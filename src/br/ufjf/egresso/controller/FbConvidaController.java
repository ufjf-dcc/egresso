package br.ufjf.egresso.controller;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.model.Aluno;

public class FbConvidaController {

	private Aluno aluno;

	@Init
	public void init() {
		Integer id = Integer.parseInt((String) Executions.getCurrent().getParameter("id"));
		aluno = new AlunoBusiness().getAluno(id);
	}

	public Aluno getAluno() {
		return aluno;
	}

}