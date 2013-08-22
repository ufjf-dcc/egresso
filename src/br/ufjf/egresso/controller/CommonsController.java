package br.ufjf.egresso.controller;

import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.zkoss.zk.ui.Executions;

import br.ufjf.egresso.business.LoginBusiness;
import br.ufjf.egresso.model.Aluno;

public class CommonsController {
	private Aluno alunoCommon;
	private HttpSession session = (HttpSession) (Executions.getCurrent())
			.getDesktop().getSession().getNativeSession();
	private LoginBusiness loginBusiness;

	public void testaLogado() throws HibernateException, Exception {
		alunoCommon = (Aluno) session.getAttribute("aluno");
		if (alunoCommon != null) {
			loginBusiness = new LoginBusiness();
			alunoCommon = loginBusiness.login(alunoCommon.getTokenfacebook());
			if (alunoCommon != null) {
				return;
			}
		}
		alunoErro();
	}

	private void alunoErro() throws InterruptedException {
		Executions.sendRedirect("/index.zul");
		alunoCommon = new Aluno();
	}

	public Aluno getAlunoCommon() {
		return alunoCommon;
	}

	public HttpSession getSession() {
		return session;
	}
}