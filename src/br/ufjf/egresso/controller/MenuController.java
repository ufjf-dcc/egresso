package br.ufjf.egresso.controller;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

public class MenuController {
	
	@Command
	public void sair(){
		Sessions.getCurrent().invalidate();
		Executions.sendRedirect("/admin/entrar.zul");
	}
}
