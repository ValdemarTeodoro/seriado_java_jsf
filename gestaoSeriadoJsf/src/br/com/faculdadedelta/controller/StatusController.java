package br.com.faculdadedelta.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.faculdadedelta.dao.StatusDao;
import br.com.faculdadedelta.modelo.Status;

@ManagedBean
@SessionScoped
public class StatusController {
	
	private String CADASTRO_STATUS = "cadastroStatus.xhtml";
	
	private Status status = new Status();
	private StatusDao dao = new StatusDao();
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	private void exibirMensagem(String mensagem) {
		FacesMessage msg = new FacesMessage(mensagem);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	public void limparCampos() {
		status = new Status();
	}
	public String salvar() {
		try {
			if(status.getId()== null) {
				dao.incluir(status);
				exibirMensagem("Inclusão realizada com sucesso");
				limparCampos();
			}else {
				dao.alterar(status);
				exibirMensagem("Alteração realizada com sucesso");
				limparCampos();
			}
		} catch (Exception e) {
			e.printStackTrace();
			exibirMensagem("Erro ao realizar a operação! " + e.getMessage());
		}
		return CADASTRO_STATUS;
	}
	public String editar() {
		return CADASTRO_STATUS;
	}
	public String excluir() {
		try {
			dao.excluir(status);
			exibirMensagem("Exclusão realizada com sucesso");
			limparCampos();
		} catch (Exception e) {
			e.printStackTrace();
			exibirMensagem("Erro ao realizar a operação! " + e.getMessage());
		}
		return CADASTRO_STATUS;
	}
	public List<Status> getLista(){
		List<Status> listaRetorno = new ArrayList<>();
		try {
			listaRetorno = dao.lista();
		} catch (Exception e) {
			e.printStackTrace();
			exibirMensagem("Erro ao realizar a operação! " + e.getMessage());
		}
		return listaRetorno;
	}

}
