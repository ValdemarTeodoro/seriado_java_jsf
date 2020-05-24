package br.com.faculdadedelta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.faculdadedelta.modelo.Status;
import br.com.faculdadedelta.util.Conexao;

public class StatusDao {
	public void incluir(Status status) throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "INSERT INTO status(descricao) VALUES (?)";
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, status.getDescricao().trim());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}finally {
			Conexao.fecharConexao(conn, ps, null);
		}
	}
	public void alterar(Status status) throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "UPDATE status SET descricao=? WHERE id=?";
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, status.getDescricao().trim());
			ps.setLong(2, status.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}finally {
			Conexao.fecharConexao(conn, ps, null);
		}
	}
	public void excluir(Status status) throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "DELETE FROM status WHERE id=?";
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setLong(1, status.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}finally {
			Conexao.fecharConexao(conn, ps, null);
		}
	}
	public List<Status> lista() throws ClassNotFoundException, SQLException{
		List<Status> listaRetorno = new ArrayList<>();
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "SELECT id, descricao FROM status";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = null;
			
		try {
			rs = ps.executeQuery();
			while(rs.next()) {
				listaRetorno.add(popularStatus(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}finally {
			Conexao.fecharConexao(conn, ps, rs);
		}
		return listaRetorno;
	}
	public Status pesquisarPorId(Long id) throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "SELECT id, descricao FROM status WHERE id=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = null;
		Status retorno = new Status();	
		try {
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				retorno = popularStatus(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}finally {
			Conexao.fecharConexao(conn, ps, rs);
		}
		return retorno;
	}
	public Status popularStatus(ResultSet rs) throws SQLException {
		Status status = new Status();
		status.setId(rs.getLong("id"));
		status.setDescricao(rs.getString("descricao").trim());
		return status;
	}
}
