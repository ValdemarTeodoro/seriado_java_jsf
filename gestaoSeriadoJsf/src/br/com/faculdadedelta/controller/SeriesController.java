package br.com.faculdadedelta.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.faculdadedelta.dao.SeriesDao;
import br.com.faculdadedelta.modelo.Genero;
import br.com.faculdadedelta.modelo.Series;
import br.com.faculdadedelta.modelo.Status;
@ManagedBean
@SessionScoped
public class SeriesController {
	private String CADASTRO_SERIE = "cadastroSerie.xhtml";
	
	private Series series = new Series();
	private SeriesDao dao = new SeriesDao();
	private Genero generoSelecionado = new Genero();
	private Status statusSelecionado = new Status();
	private String nomePesquisado;
	private String comentarioPesquisado;
	private List<Series> lista = new ArrayList<>();
	
	public String getNomePesquisado() {
		return nomePesquisado;
	}

	public void setNomePesquisado(String nomePesquisado) {
		this.nomePesquisado = nomePesquisado;
	}

	public String getComentarioPesquisado() {
		return comentarioPesquisado;
	}

	public void setComentarioPesquisado(String comentarioPesquisado) {
		this.comentarioPesquisado = comentarioPesquisado;
	}

	public Series getSeries() {
		return series;
	}

	public void setSeries(Series series) {
		this.series = series;
	}

	public Genero getGeneroSelecionado() {
		return generoSelecionado;
	}

	public void setGeneroSelecionado(Genero generoSelecionado) {
		this.generoSelecionado = generoSelecionado;
	}

	public Status getStatusSelecionado() {
		return statusSelecionado;
	}

	public void setStatusSelecionado(Status statusSelecionado) {
		this.statusSelecionado = statusSelecionado;
	}

	public void limparCampos() {
		series = new Series();
		generoSelecionado = new Genero();
		statusSelecionado = new Status();
	}
	
	private void exibirMensagem(String mensagem) {
		FacesMessage msg = new FacesMessage(mensagem);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public String salvar() {
		try {
			if(series.getId()==null) {
				if(dao.pesquisarSeriePornome(getSeries().getNome())==null) {
					series.setGenero(generoSelecionado);
					series.setStatus(statusSelecionado);
					dao.incluir(series);
					exibirMensagem("Inclusão realizada com sucesso!");
					limparCampos();
				}else {
					exibirMensagem("O seriado já está cadastrado!");
				}
			}else {
				series.setGenero(generoSelecionado);
				series.setStatus(statusSelecionado);
				dao.alterar(series);
				exibirMensagem("Alteração realizada com sucesso!");
				limparCampos();
			}
		} catch (Exception e) {
			e.printStackTrace();
			exibirMensagem("Erro ao realizar a operação, tente novamente mais tarde!" + e.getMessage());
		}
		return CADASTRO_SERIE;
	}
	public String editar() {
		generoSelecionado = series.getGenero();
		statusSelecionado = series.getStatus();
		return CADASTRO_SERIE;
	}
	public String excluir() {
		try {
			dao.excluir(series);
			exibirMensagem("Eclusão realizada com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			exibirMensagem("Erro ao realizar a operação, tente novamente mais tarde!" + e.getMessage());
		}
		return CADASTRO_SERIE;
	}
	public List<Series> getLista(){
		try {
			if(nomePesquisado != null) {
				lista = dao.pesquisarPorNome(nomePesquisado, comentarioPesquisado);
			}else {
				lista = dao.lista();
			}
		} catch (Exception e) {
			e.printStackTrace();
			exibirMensagem("Erro ao realizar a operação, tente novamente mais tarde!" + e.getMessage());
		}
		return lista;
	}
	public String pesquisarSeriePorNome() {
		try {
			lista = dao.pesquisarPorNome(nomePesquisado, comentarioPesquisado);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CADASTRO_SERIE;
	}
}
