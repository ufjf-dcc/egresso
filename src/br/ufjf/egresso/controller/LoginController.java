package br.ufjf.egresso.controller;

import org.hibernate.HibernateException;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.model.Aluno;

public class LoginController {

	private Aluno aluno = new Aluno();
	private AlunoBusiness alunoBusiness = new AlunoBusiness();
	private Session session = Sessions.getCurrent();
	
	@Init
	public void logado() throws HibernateException, Exception {
		alunoBusiness = new AlunoBusiness();
		aluno = (Aluno) session.getAttribute("aluno");
		if (alunoBusiness.checaLogin(aluno) || alunoBusiness.checaLoginAdmin(aluno)) {
				Executions.sendRedirect("/home.zul");
		}
		else {
			aluno = new Aluno();
		}
	}

	@Command
	public void facebookLogin(){
		Executions.sendRedirect("/signin");
	}
	
	@Command
	public void adminLogin() throws HibernateException, Exception{
		if (aluno != null && alunoBusiness.stringPreenchida(aluno.getNome()) && alunoBusiness.stringPreenchida(aluno.getTokenFacebook())){
			if (alunoBusiness.login(aluno.getNome(), aluno.getTokenFacebook()))
				Executions.sendRedirect("home.zul");
			else 
				Messagebox.show("Login ou senha inválidos!", "Erro!", Messagebox.OK, Messagebox.ERROR);
		}
		else
				Messagebox.show("Preencha os campos!", "Erro!", Messagebox.OK, Messagebox.ERROR);
	}
	
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

}
