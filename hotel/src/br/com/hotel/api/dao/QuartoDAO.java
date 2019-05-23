package br.com.hotel.api.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.hotel.api.model.Quarto;

public class QuartoDAO {
	public static Quarto getQuarto(int numero) {
		MySQLDAO mysql_db = new MySQLDAO();
		String query = "select numero, capacidade from quarto where numero = " + numero;
		ResultSet rs = mysql_db.executeQuery(query);
		Quarto quarto = new Quarto();
		try {
			if(rs.next()) {
				quarto.setNumero(rs.getInt(1));
				quarto.setCapacidade(rs.getInt(2));
			}
		}
		catch (SQLException e) {
			System.err.println("Quarto não encontrado");
			quarto = null;
		}
		return quarto;
	}
	public static ArrayList<Quarto> listQuarto() {
		MySQLDAO mysql_db = new MySQLDAO();
		String query = "select numero, capacidade from quarto";
		ResultSet rs = mysql_db.executeQuery(query);
		ArrayList<Quarto> quartos = new ArrayList<Quarto>();
		try {
			while(rs.next()) {
				Quarto quarto = new Quarto();
				try {
					quarto.setNumero(rs.getInt(1));
					quarto.setCapacidade(rs.getInt(2));
				}
				catch (SQLException e) {
					System.err.println("Quarto não encontrado");
					quarto = null;
				}
				quartos.add(quarto);
			}
		} catch (SQLException e) {
			System.err.println("Quarto não encontrado");
			quartos = null;
		}
		return quartos;
	}
	public static Boolean createQuarto(int quantidade, int capacidade) {
		MySQLDAO mysql_db = new MySQLDAO();
		String query = "INSERT into quarto (capacidade,ocupado) values ('" + capacidade + "',false)";
		Boolean retorno = true;
		for(int i = 0; i < quantidade; i++) {
			int cod_quarto = mysql_db.executeInsert(query);
			if(cod_quarto == 0) {
				retorno = false;
			}
		}
		return retorno;
	}
}