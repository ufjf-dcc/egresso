package br.ufjf.egresso.controller;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.model.Aluno;

public class GenericController {

	protected Session session = Sessions.getCurrent();
	protected Aluno aluno = (Aluno) session.getAttribute("aluno");
	protected AlunoBusiness alunoBusiness;

	public String getMenu() {
		return "/templates/menu-aluno.zul";
	}

	@Command
	public void exit() {
		session.invalidate();
		Executions.sendRedirect("/index.zul");
	}
	
	public Aluno getUsuario() {
		return aluno;
	}

	public void setUsuario(Aluno aluno) {
		this.aluno = aluno;
	}

}