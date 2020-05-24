package br.com.faculdadedelta.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexao {
	
	public static Connection conectarNoBancoDeDados() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");

		Connection conn = null;

		String url = "jdbc:postgresql://localhost/Web2";
		String usuario = "postgres";
		String senha = "v130783mi";

		conn = DriverManager.getConnection(url, usuario, senha);

		return conn;
	}
	
	public static void fecharConexao(Connection conn, PreparedStatement ps, ResultSet rs) 
			throws SQLException {
		if (conn != null) {
			conn.close();
		}
		if (ps != null) {
			ps.close();
		}
		if (rs != null) {
			rs.close();
		}
	}

	public static void main(String[] args) {
		try {
			conectarNoBancoDeDados();
			System.out.println("Conectou no banco de dados!");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}