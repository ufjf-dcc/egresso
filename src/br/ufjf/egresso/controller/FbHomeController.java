package br.ufjf.egresso.controller;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Sessions;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.business.TurmaBusiness;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Turma;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Friend;
import facebook4j.PostUpdate;

public class FbHomeController {

	private List<Turma> turmas = new TurmaBusiness().getTodas();
	private int[] linhas = new int[] { 0, 1, 2, 3, 4, 5, 6 };
	private Facebook facebook;
	private Aluno aluno;
	private List<Friend> amigos = new ArrayList<Friend>();
	
	@Init
	public void init(){
		facebook = (Facebook) Sessions.getCurrent().getAttribute(
				"facebook");
		try {
			Aluno aluno = new AlunoBusiness().getAluno(facebook.getId());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Command
	public void convida(@BindingParam("id") String id) throws FacebookException {
		facebook.postFeed(id, new PostUpdate("teste"));
	}

	public String getMenuAmigos() {
		return "/amigos.zul";
	}

	public List<Turma> getTurmas() {
		return turmas;
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