package br.ufjf.egresso.controller;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import br.ufjf.egresso.model.Aluno;

public class MenuController extends CommonsController {

	@Command
	public void sair() {
		super.getSession().setAttribute("aluno", null);
		Executions.sendRedirect("/index.zul");
	}

	public Aluno getUsuario() {
		return super.getUsuarioCommon();
	}

}