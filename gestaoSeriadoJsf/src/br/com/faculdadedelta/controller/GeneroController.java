package br.com.faculdadedelta.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.faculdadedelta.dao.GeneroDao;
import br.com.faculdadedelta.modelo.Genero;
@ManagedBean
@SessionScoped
public class GeneroController {
	private String CADASTRO_GENERO = "cadastroGenero.xhtml";
	
	private Genero genero = new Genero();
	private GeneroDao dao = new GeneroDao();
	
	
	public Genero getGenero() {
		return genero;
	}
	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	private void exibirMensagem(String mensagem) {
		FacesMessage msg = new FacesMessage(mensagem);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	public void limparCampos() {
		genero = new Genero();
	}
	public String salvar() {
		try {
			if(genero.getId()== null) {
				dao.incluir(genero);
				exibirMensagem("Inclusão realizada com sucesso");
				limparCampos();
			}else {
				dao.alterar(genero);
				exibirMensagem("Alteração realizada com sucesso");
				limparCampos();
			}
		} catch (Exception e) {
			e.printStackTrace();
			exibirMensagem("Erro ao realizar a operação! " + e.getMessage());
		}
		return CADASTRO_GENERO;
	}
	public String editar() {
		return CADASTRO_GENERO;
	}
	public String excluir() {
		try {
			dao.excluir(genero);
			exibirMensagem("Exclusão realizada com sucesso");
			limparCampos();
		} catch (Exception e) {
			e.printStackTrace();
			exibirMensagem("Erro ao realizar a operação! " + e.getMessage());
		}
		return CADASTRO_GENERO;
	}
	public List<Genero> getLista(){
		List<Genero> listaRetorno = new ArrayList<>();
		try {
			listaRetorno = dao.lista();
		} catch (Exception e) {
			e.printStackTrace();
			exibirMensagem("Erro ao realizar a operação! " + e.getMessage());
		}
		return listaRetorno;
	}
}
