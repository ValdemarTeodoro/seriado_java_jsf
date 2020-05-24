package br.com.faculdadedelta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.faculdadedelta.modelo.Genero;
import br.com.faculdadedelta.util.Conexao;

public class GeneroDao {
	public void incluir(Genero genero) throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "INSERT INTO genero(descricao) VALUES (?)";
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, genero.getDescricao().trim());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}finally {
			Conexao.fecharConexao(conn, ps, null);
		}
	}
	public void alterar(Genero genero) throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "UPDATE genero SET descricao=? WHERE id=?";
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, genero.getDescricao().trim());
			ps.setLong(2, genero.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}finally {
			Conexao.fecharConexao(conn, ps, null);
		}
	}
	public void excluir(Genero genero) throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "DELETE FROM genero WHERE id=?";
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setLong(1, genero.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}finally {
			Conexao.fecharConexao(conn, ps, null);
		}
	}
	public List<Genero> lista() throws ClassNotFoundException, SQLException{
		List<Genero> listaRetorno = new ArrayList<>();
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "SELECT id, descricao FROM genero";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = null;
			
		try {
			rs = ps.executeQuery();
			while(rs.next()) {
				listaRetorno.add(popularGenero(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}finally {
			Conexao.fecharConexao(conn, ps, rs);
		}
		return listaRetorno;
	}
	public Genero pesquisarPorId(Long id) throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "SELECT id, descricao FROM genero WHERE id =?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = null;
		Genero retorno = new Genero();	
		try {
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				retorno = popularGenero(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}finally {
			Conexao.fecharConexao(conn, ps, rs);
		}
		return retorno;
	}
	public Genero popularGenero(ResultSet rs) throws SQLException {
		Genero genero = new Genero();
		genero.setId(rs.getLong("id"));
		genero.setDescricao(rs.getString("descricao").trim());
		return genero;
	}
}
