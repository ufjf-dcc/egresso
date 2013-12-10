package br.ufjf.egresso.controller;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.ListaEspera;
import br.ufjf.egresso.model.Turma;
import br.ufjf.egresso.persistent.impl.AlunoDAO;
import br.ufjf.egresso.persistent.impl.ListaEsperaDAO;
import br.ufjf.egresso.persistent.impl.TurmaDAO;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Friend;
import facebook4j.PostUpdate;

public class HomeController extends GenericController{

	private TurmaDAO turmaDAO = new TurmaDAO();
	private ListaEsperaDAO listaEsperaDAO = new ListaEsperaDAO();
	private List<Turma> turmas = turmaDAO.getTurmas();
	private List<ListaEspera> solicitacoes =  listaEsperaDAO.getListasEspera();
	int[] linhas = new int[] {0,1,2,3,4,5,6};
	Facebook facebook = (Facebook) Sessions.getCurrent().getAttribute("facebook");
	List<Friend> amigos = new ArrayList<Friend>();
	
	public String getMenuSolicitacoes(){
		if (aluno != null) {
			int tipoUsuario = aluno.getTipoPermissao();
			if (tipoUsuario == 2){
				return "/solicitacoesAdmin.zul";
			}
			else return "";
		}
		return null;	
	}

	@Command
	public void aceita(@BindingParam("solicitacao")ListaEspera listaEspera ){
		ListaEsperaDAO listaEsperaDAO = new ListaEsperaDAO();
		AlunoDAO alunoDAO = new AlunoDAO();
		Aluno aluno = alunoDAO.retornaAlunoM(listaEspera.getMatricula());
		aluno.setIdfacebook(listaEspera.getIdfacebook());
		aluno.setUrlFoto(listaEspera.getUrlFoto());
		alunoDAO.editar(aluno);
		listaEsperaDAO.exclui(listaEspera);
		Executions.sendRedirect("/home.zul");
	}
	
	@Command
	public void recusa(@BindingParam("solicitacao")ListaEspera listaEspera ){
		ListaEsperaDAO listaEsperaDAO = new ListaEsperaDAO();
		listaEsperaDAO.exclui(listaEspera);
		Executions.sendRedirect("/home.zul");
	}
	
	
	@Command
	public void convida(@BindingParam("id")String id) throws FacebookException{
		facebook.postFeed(id, new PostUpdate("teste"));
	}
	
	
	public String getMenuAmigos(){
		if (aluno != null) {
			int tipoUsuario = aluno.getTipoPermissao();
			if (tipoUsuario == 0){
				return "/amigos.zul";
			}
			else return "";
		}
		return null;	
	}
	
	
	
	public List<Turma> getTurmas() {
		return turmas;
	}

	public List<ListaEspera> getSolicitacoes() {
		return solicitacoes;
	}

	public int[] getLinhas() {
		return linhas;
	}

	public List<Friend> getAmigos() throws FacebookException {
		return (List<Friend>) facebook.getFriends();
	}

	public Facebook getFacebook() {
		return facebook;
	}

	public void setFacebook(Facebook facebook) {
		this.facebook = facebook;
	}

	
	
	
}