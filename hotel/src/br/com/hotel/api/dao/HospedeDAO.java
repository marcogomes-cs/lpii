package br.com.hotel.api.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import br.com.hotel.api.model.Hospede;

public class HospedeDAO {
	private static String status = "Não chamou classe hospede...";
	public static Hospede getHospede(int cod_hospede) {
		MySQLDAO mysql_db = new MySQLDAO();
		String query = "select cod_hospede, nome, endereco, rg, cpf, telefone, email from (select * from hospede where cod_hospede = " + Integer.toString(cod_hospede) + ") h INNER JOIN pessoa p on h.id_pessoa = p.id";
		ResultSet rs = mysql_db.executeQuery(query);
		Hospede hospede = new Hospede();
		try {
			if(rs.next()) {
				hospede.setNome(rs.getString(2));
				hospede.setEndereco(rs.getString(3));
				hospede.setRg(rs.getLong(4));
				hospede.setCpf(rs.getLong(5));
				hospede.setTelefone(rs.getLong(6));
				hospede.setEmail(rs.getString(7));
				hospede.setCod_hospede(cod_hospede);
			}
		}
		catch (SQLException e) {
			status = "Hospede não encontrado";
			hospede = null;
		}
		finally {
			mysql_db.closeConnection();
		}
		return hospede;
	}
	public static ArrayList<Hospede> listHospedes() {
		MySQLDAO mysql_db = new MySQLDAO();
		String query = "select cod_hospede, nome, endereco, rg, cpf, telefone, email from hospede h INNER JOIN pessoa p on h.id_pessoa = p.id";
		ResultSet rs = mysql_db.executeQuery(query);
		ArrayList<Hospede> hospedes = new ArrayList<Hospede>();
		try {
			while(rs.next()) {
				Hospede hospede = new Hospede();
				hospede.setNome(rs.getString(2));
				hospede.setEndereco(rs.getString(3));
				hospede.setRg(rs.getLong(4));
				hospede.setCpf(rs.getLong(5));
				hospede.setTelefone(rs.getLong(6));
				hospede.setEmail(rs.getString(7));
				hospede.setCod_hospede(rs.getInt(1));
				hospedes.add(hospede);
			}
			rs.close();
		}
		catch (SQLException e) {
			status = "";
			for(StackTraceElement element : e.getStackTrace()) {
				status += element.toString();
				status += "\n";
			}
			
			hospedes = null;
		}
		finally {
			mysql_db.closeConnection();
		}
		if (hospedes != null) {
			status = "Lista carregada com sucesso";
		}
		return hospedes;
	}
	public static String statusConnection() {
		return status;
	}
	public static int saveHospede(Hospede hospede) {
		MySQLDAO mysql_db = new MySQLDAO();
		String query = "INSERT into pessoa (nome, endereco, rg, cpf, telefone, email) values ('" + hospede.getNome() + "','" + hospede.getEndereco() + "','" + hospede.getRg() + "','" + hospede.getCpf() + "','" + hospede.getTelefone() + "','" + hospede.getEmail() + "')";
		int cod_pessoa = mysql_db.executeInsert(query);
		query = "INSERT into hospede (id_pessoa) values (" + cod_pessoa + ")";
		int cod_hospede = mysql_db.executeInsert(query);
		return cod_hospede;
	}
	public static Boolean updateHospede(Hospede hospede) {
		MySQLDAO mysql_db = new MySQLDAO();
		String query = "Select id_pessoa from hospede where cod_hospede = '" + hospede.getCod_hospede() + "'";
		ResultSet rs = mysql_db.executeQuery(query);
		int id_pessoa = 0;
		int response = 999;
		try {
			if(rs.next()) {
				id_pessoa = rs.getInt(1);
			}
		}
		catch(SQLException e) {
			status = "Hospede não encontrado";
			return false;
		}
		if(id_pessoa != 0) {
			query = "Update pessoa set nome = '" + hospede.getNome() + "', endereco = '" + hospede.getEndereco() + "', rg = '" + hospede.getRg() + "', cpf = '" + hospede.getCpf() + "', telefone = '" + hospede.getTelefone() + "', email = '" + hospede.getEmail() + "' where id = " + id_pessoa;
			response = mysql_db.executeInsert(query);			
		}
		return (response == 0);
	}
}