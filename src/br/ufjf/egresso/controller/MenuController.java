package br.ufjf.egresso.controller;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

import br.ufjf.egresso.model.Aluno;

public class MenuController {
	private String saudacao;

	@Init
	public void init() {
		Aluno aluno = (Aluno) Sessions.getCurrent().getAttribute("aluno");
		if (aluno != null)
			saudacao = aluno.getNome()
					+ ", seja bem vindo(a) ao Controle de Egressos da UFJF!";
	}

	public String getSaudacao() {
		return saudacao;
	}

	public void setSaudacao(String saudacao) {
		this.saudacao = saudacao;
	}

	@Command
	public void sair() {
		Sessions.getCurrent().invalidate();
		Executions.sendRedirect("/index.zul");
	}
	
	@Command
	public void api(){
		Window window = (Window)Executions.createComponents(
                "/fb/api.zul", null, null);
        window.doModal();
	}

}
