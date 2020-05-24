package br.com.faculdadedelta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.faculdadedelta.modelo.Genero;
import br.com.faculdadedelta.modelo.Series;
import br.com.faculdadedelta.modelo.Status;
import br.com.faculdadedelta.util.Conexao;

public class SeriesDao {
	public void incluir(Series series) throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "INSERT INTO series(id_genero, id_status, nome, comentario, nota_avaliacao) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setLong(1, series.getStatus().getId());
			ps.setLong(2, series.getGenero().getId());
			ps.setString(3, series.getNome().trim());
			ps.setString(4, series.getComentario().trim());
			ps.setInt(5, series.getNotaAvaliacao());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}finally {
			Conexao.fecharConexao(conn, ps, null);
		}
	}
	public void alterar(Series series) throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "UPDATE series SET id_genero=?, id_status=?, nome=?, comentario=?, nota_avaliacao=? WHERE id=?";
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setLong(1, series.getStatus().getId());
			ps.setLong(2, series.getGenero().getId());
			ps.setString(3, series.getNome().trim());
			ps.setString(4, series.getComentario().trim());
			ps.setInt(5, series.getNotaAvaliacao());
			ps.setLong(6, series.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}finally {
			Conexao.fecharConexao(conn, ps, null);
		}
	}
	public void excluir(Series series) throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "DELETE FROM series WHERE id=?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setLong(1, series.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}finally {
			Conexao.fecharConexao(conn, ps, null);
		}
	}
	public List<Series> lista() throws ClassNotFoundException, SQLException{
		List<Series> listaRetorno = new ArrayList<>();
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "SELECT s.id As idSerie, s.id_status AS idStatus, s.id_genero AS idGenero, s.nome As nomeSerie, s.comentario As comentarioSerie, s.nota_avaliacao AS notaSerie, g.descricao AS descricaoGenero, st.descricao As descricaoStatus FROM series s inner join status st on s.id_status = st.id inner join genero g on s.id_genero = g.id";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Series series = new Series();
				series.setId(rs.getLong("idSerie"));
				series.setNome(rs.getString("nomeSerie").trim());
				series.setComentario(rs.getString("comentarioSerie").trim());
				series.setNotaAvaliacao(rs.getInt("notaSerie"));
				
				Status status = new Status();
				status.setId(rs.getLong("idStatus"));
				status.setDescricao(rs.getString("descricaoStatus").trim());
				series.setStatus(status);
				
				Genero genero = new Genero();
				genero.setId(rs.getLong("idGenero"));
				genero.setDescricao(rs.getString("descricaoGenero").trim());
				series.setGenero(genero);
				
				listaRetorno.add(series);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}finally {
			Conexao.fecharConexao(conn, ps, rs);
		}
		
		return listaRetorno;
	}
	public Series pesquisarSeriePornome(String nome) throws Exception{
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql ="SELECT nome FROM series where nome =?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = null;
		Series series = null;
		try {
			ps.setString(1, nome);
			rs = ps.executeQuery();
			if(rs.next()) {
				series = new Series();
				series.setNome(nome);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException();
		}finally {
			Conexao.fecharConexao(conn, ps, rs);
		}
		
		return series;
	}
	public List<Series> pesquisarPorNome(String nome,String comentario) throws ClassNotFoundException, SQLException{
		List<Series> listaRetorno = new ArrayList<>();
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "SELECT s.id As idSerie, s.id_status AS idStatus, s.id_genero AS idGenero, s.nome As nomeSerie, s.comentario As comentarioSerie, s.nota_avaliacao AS notaSerie, g.descricao AS descricaoGenero, st.descricao As descricaoStatus FROM series s inner join status st on s.id_status = st.id inner join genero g on s.id_genero = g.id where upper(s.nome) like ? ";
		if(!comentario.isEmpty()) {
			sql += " and upper(s.comentario) like ? "; 
		}
		sql += " order by s.nome";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = null;
		try {
			ps.setString(1, nome.toUpperCase() + "%");
			if(!comentario.isEmpty()) {
				ps.setString(2, comentario.toUpperCase() + "%");
			}
			rs = ps.executeQuery();
			while(rs.next()) {
				Series series = new Series();
				series.setId(rs.getLong("idSerie"));
				series.setNome(rs.getString("nomeSerie").trim());
				series.setComentario(rs.getString("comentarioSerie").trim());
				series.setNotaAvaliacao(rs.getInt("notaSerie"));
				
				Status status = new Status();
				status.setId(rs.getLong("idStatus"));
				status.setDescricao(rs.getString("descricaoStatus").trim());
				series.setStatus(status);
				
				Genero genero = new Genero();
				genero.setId(rs.getLong("idGenero"));
				genero.setDescricao(rs.getString("descricaoGenero").trim());
				series.setGenero(genero);
				
				listaRetorno.add(series);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e);
		}finally {
			Conexao.fecharConexao(conn, ps, rs);
		}
		
		return listaRetorno;
	}
}