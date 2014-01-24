package br.ufjf.egresso.controller;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import br.ufjf.egresso.model.Aluno;

public class FbPerfilController {

	private Aluno aluno;

	@Init
	public void init() {
		Session session = Sessions.getCurrent();
		aluno = (Aluno) session.getAttribute("aluno");
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

}