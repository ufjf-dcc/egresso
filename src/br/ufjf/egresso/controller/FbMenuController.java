package br.ufjf.egresso.controller;

import org.hibernate.HibernateException;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

import br.ufjf.egresso.model.Aluno;

public class FbMenuController {
	Aluno aluno;

	@Init
	public void init() throws HibernateException, Exception {
		aluno = (Aluno) Sessions.getCurrent().getAttribute("aluno");
	}

	@Command
	public void verTurma() {
		Executions.sendRedirect("turma.zul?id=" + aluno.getTurma().getId());
	}
	
	@Command
	public void convidar() {
	}

}
