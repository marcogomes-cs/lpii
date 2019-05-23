package br.com.hotel.api.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.hotel.api.model.Hospede;
import br.com.hotel.api.model.Quarto;
import br.com.hotel.api.model.Reserva;

public class ReservaDAO {
	private static String status = "Reserva não instancia...";
	public static Reserva getReserva(int cod_reserva) {
		MySQLDAO mysql_db = new MySQLDAO();
		String query = "select cod_reserva, numero_quarto, efetiva, checkin, checkout from reserva where cod_reserva = " + cod_reserva;
		ResultSet rs = mysql_db.executeQuery(query);
		Reserva reserva = new Reserva();
		ArrayList<Hospede> hospedes = new ArrayList<Hospede>();
		try {
			if(rs.next()) {
				reserva.setCod_reserva(cod_reserva);
				reserva.setEfetiva(rs.getBoolean(3));
				reserva.setCheckin(rs.getString(4));
				reserva.setCheckout(rs.getString(5));
				reserva.setQuarto(QuartoDAO.getQuarto(rs.getInt(2)));
			}
		}
		catch (SQLException e) {
			status = "Reserva não encontrada";
			reserva = null;
		}
		query = "select cod_hospede from reserva_hospede where cod_reserva = " + cod_reserva;
		rs = mysql_db.executeQuery(query);
		try {
			while(rs.next()) {
				Hospede hospede = HospedeDAO.getHospede(rs.getInt(1));
				hospedes.add(hospede);
			}
			reserva.setHospedes(hospedes);
		}
		catch (SQLException e) {
			status = "Erro ao associar hospede com reserva";
			reserva = null;
		}
		finally {
			mysql_db.closeConnection();
		}
		return reserva;
	}
	public static ArrayList<Reserva> listReserva() {
		MySQLDAO mysql_db = new MySQLDAO();
		String query = "select cod_reserva, numero_quarto, efetiva, checkin, checkout from reserva";
		ResultSet rs = mysql_db.executeQuery(query);
		Reserva reserva = new Reserva();
		ArrayList<Reserva> reservas = new ArrayList<Reserva>(); 
		ArrayList<Reserva> reservas_response = new ArrayList<Reserva>(); 
		try {
			while(rs.next()) {
				reserva.setCod_reserva(rs.getInt(1));
				reserva.setEfetiva(rs.getBoolean(3));
				reserva.setCheckin(rs.getString(4));
				reserva.setCheckout(rs.getString(5));
				reserva.setQuarto(QuartoDAO.getQuarto(rs.getInt(2)));
				reservas.add(reserva);
			}
		}
		catch (SQLException e) {
			status = "Reserva não encontrada";
			return null;
		}
		for(Reserva reserva_salva : reservas) {
			ArrayList<Hospede> hospedes = new ArrayList<Hospede>();
			query = "select cod_hospede from reserva_hospede where cod_reserva = " + reserva_salva.getCod_reserva();
			rs = mysql_db.executeQuery(query);
			try {
				while(rs.next()) {
					Hospede hospede = HospedeDAO.getHospede(rs.getInt(1));
					hospedes.add(hospede);
				}
				reserva_salva.setHospedes(hospedes);
				reservas_response.add(reserva_salva);
			}
			catch (SQLException e) {
				status = "Erro ao associar hospede com reserva";
				return null;
			}
			finally {
				mysql_db.closeConnection();
			}
		}
		return reservas_response;
	}
	public static int createReserva(Date checkin, Date checkout, int capacidade, Hospede hospede) {
		MySQLDAO mysql_db = new MySQLDAO();
		String query = "select numero from quarto where ocupado = false and reservado = false and capacidade = '" + capacidade + "' LIMIT 1";
		ResultSet rs = mysql_db.executeQuery(query);
		int numero_quarto = 0;
		try {
			if(rs.next()) {
				numero_quarto = rs.getInt(1);
			}
		}
		catch(SQLException e) {
			status = "Não há quarto disponível";
		}
		query = "INSERT into reserva (numero_quarto, efetiva, cancelada, checkin, checkout) values ('" + numero_quarto + "','false','false','" + checkin + "','" + checkout + "')";
		int cod_reserva = mysql_db.executeInsert(query);
		if(cod_reserva != 0) {
			query = "INSERT into reserva_hospede (cod_reserva, cod_hospede) values (" + cod_reserva + "," + hospede.getCod_hospede() + ")";
			int cod_rtrn = mysql_db.executeInsert(query);
			query = "update quarto set reservado = true were numero = '" + numero_quarto + "'";
			cod_rtrn = mysql_db.executeInsert(query);
		}
		return cod_reserva;
	}
	public static Boolean addHospedeToReserva(int cod_reserva, Hospede hospede) {
		MySQLDAO mysql_db = new MySQLDAO();
		String query = "INSERT into reserva_hospede (cod_reserva, cod_hospede) values (" + cod_reserva + "," + hospede.getCod_hospede() + ")";
		int resposta = mysql_db.executeInsert(query);
		return (resposta != 0);
	}
	public static Boolean efetivaReserva(int cod_reserva) {
		MySQLDAO mysql_db = new MySQLDAO();
		String query = "select numero_quarto from reserva where cod_reserva = " + cod_reserva;
		ResultSet rs = mysql_db.executeQuery(query);
		int numero_quarto = 0;
		int resposta = 0;
		try {
			if(rs.next()) {
				numero_quarto = rs.getInt(1);
			}
		}
		catch(SQLException e) {
			status = "Numero do quarto não encontrado";
			return false;
		}
		query = "UPDATE quarto set ocupado = true where numero = '" + numero_quarto + "'";
		resposta = mysql_db.executeInsert(query);
		if(resposta != 0) {
			query = "UPDATE reserva_hospede set efetiva = true where cod_reserva = " + cod_reserva;
			resposta = mysql_db.executeInsert(query);
			return true;
		}
		else {
			status = "Problemas ao efetivar a reserva";
			return false;
		}
	}
}
