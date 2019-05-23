package br.com.hotel.api.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MySQLDAO {
	private static String status = "Não conectou...";
	private Connection connection;
	public void getConnection() {
		this.connection = null;
		Scanner sc = null;
		try {
			String driverName = "com.mysql.jdbc.Driver";
			sc = new Scanner(new File("/tmp/sql_info.txt"));
			Class.forName(driverName);
			String serverName = sc.nextLine();
			String mydatabase =sc.nextLine();
			
			String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
			String username = sc.nextLine();
			String password = sc.nextLine();
			connection = DriverManager.getConnection(url, username, password);
			if (connection != null) {
				status = "STATUS--->Conectado com sucesso!";
			}
			else {
				status = "STATUS--->Não foi possivel realizar conexão";
			}
		}
		catch (ClassNotFoundException e) {
			status = "O driver expecificado nao foi encontrado.";
		}
		catch (SQLException e) {
			status = "Nao foi possivel conectar ao Banco de Dados.";
		} catch (FileNotFoundException e) {
			status = "Arquivo de configuração do banco não encontrado.";
		}
		finally {
			sc.close();
		}
	}
	public static String statusConnection() {
		return status;
	}
	public boolean closeConnection() {
		try {
			this.connection.close();
			return true;
		}
		catch (SQLException e) {
			return false;
		}
		catch (NullPointerException e) {
			return false;
		}
	}
	public void resetConnection() {
		closeConnection();
		getConnection();
	}
	public ResultSet executeQuery(String query) {
		getConnection();
		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = this.connection.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			status = "Não foi possível executar a query";
		}
		return rs;
	}
	public int executeInsert(String query) {
		getConnection();
		Statement stmt;
		int response = 0;
		int id = 0;
		ResultSet rs = null;
		try {
			stmt = this.connection.createStatement();
			response = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
	        if (rs.next()){
	            id=rs.getInt(1);
	        }
		} 
		catch (SQLException e) {
			status = "Não foi possível executar a query";
			id = 0;
		}
		finally {
			closeConnection();
		}
		return id;
	}
}