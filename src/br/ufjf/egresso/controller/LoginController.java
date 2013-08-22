package br.ufjf.egresso.controller;

import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

import br.ufjf.egresso.business.LoginBusiness;
import br.ufjf.egresso.model.Aluno;

public class LoginController {

	private Aluno aluno = new Aluno();
	private LoginBusiness loginBusiness;
	private HttpSession session = (HttpSession) (Executions.getCurrent())
			.getDesktop().getSession().getNativeSession();

	@Init
	public void verificaLogado() throws HibernateException, Exception {
		aluno = (Aluno) session.getAttribute("aluno");
		if (aluno != null) {
			loginBusiness = new LoginBusiness();
			aluno = loginBusiness.login(aluno.getTokenfacebook());
			if (aluno != null) {
				Executions.sendRedirect("/home.zul");
				return;
			}
		}
		aluno = new Aluno();
	}

	@Command
	public void submit() throws HibernateException, Exception {
		if (aluno != null && aluno.getTokenfacebook() != null) {
			loginBusiness = new LoginBusiness();
			aluno = loginBusiness.login(aluno.getTokenfacebook());
			if (aluno != null) {
				Executions.sendRedirect("/home.zul");
			}
		}
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

}
