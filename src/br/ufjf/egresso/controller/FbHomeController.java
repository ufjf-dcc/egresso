package br.ufjf.egresso.controller;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import br.ufjf.egresso.business.TurmaBusiness;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Turma;
import facebook4j.Facebook;
import facebook4j.Friend;

public class FbHomeController {

	private List<Turma> turmas = new TurmaBusiness().getTodas();
	private int[] linhas = new int[] { 0, 1, 2, 3, 4, 5, 6 };
	private Facebook facebook;
	private Aluno aluno;
	private List<Friend> amigos = new ArrayList<Friend>();
	
	@Init
	public void init(){
		Session session = Sessions.getCurrent();
		facebook = (Facebook) session.getAttribute(
				"facebook");
			aluno = (Aluno) session.getAttribute("aluno");
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public int[] getLinhas() {
		return linhas;
	}

	public void setLinhas(int[] linhas) {
		this.linhas = linhas;
	}

	public Facebook getFacebook() {
		return facebook;
	}

	public void setFacebook(Facebook facebook) {
		this.facebook = facebook;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Friend> getAmigos() {
		return amigos;
	}

	public void setAmigos(List<Friend> amigos) {
		this.amigos = amigos;
	}

}