package br.ufjf.egresso.controller;

import org.hibernate.HibernateException;
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

	public boolean logado() throws HibernateException, Exception {
		aluno = (Aluno) session.getAttribute("aluno");
		alunoBusiness = new AlunoBusiness();
		if (!alunoBusiness.checaLogin(aluno)) {
			Executions.sendRedirect("/index.zul");
			aluno = new Aluno();
			return false;
		}
		return true;
	}

	public void permissao(int tipo) throws HibernateException, Exception {
		aluno = (Aluno) session.getAttribute("aluno");
		alunoBusiness = new AlunoBusiness();
		if (alunoBusiness.checaLogin(aluno)) {
			if (aluno.getTipoPermissao() != tipo) {
				Executions.sendRedirect("/home.zul");
			}
		} else {
			Executions.sendRedirect("/index.zul");
			aluno = new Aluno();
		}
	}

	public String getMenu() {
		if (aluno != null) {
			int tipoUsuario = aluno.getTipoPermissao();
			if (tipoUsuario == 0)
				return "/menuAluno.zul";
			if (tipoUsuario == 1)
				return "/menuCoordenador.zul";
			if (tipoUsuario == 2){
				return "/menuAdministrador.zul";
			}
			else return "/menuAluno.zul";
		}
		return null;	
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