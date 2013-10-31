package br.ufjf.egresso.controller;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.Initiator;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.model.Aluno;


public class AuthController implements Initiator {
	
	@Override
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		Session session = Sessions.getCurrent();
		Aluno aluno = (Aluno) session.getAttribute("aluno");
		AlunoBusiness alunoBusiness = new AlunoBusiness();
		if (!alunoBusiness.checaLogin(aluno)) {
			Executions.sendRedirect("/index.zul");
			return;
		}
	}
}
