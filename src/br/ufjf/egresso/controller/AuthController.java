package br.ufjf.egresso.controller;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Initiator;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.model.Aluno;


public class AuthController implements Initiator {
	
	@Override
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		/*Aluno aluno = (Aluno) Sessions.getCurrent().getAttribute("aluno");
		if (!new AlunoBusiness().checaLogin(aluno)) 
			Executions.sendRedirect("/index.zul");*/
	}
}
