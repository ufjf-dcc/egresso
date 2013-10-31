package br.ufjf.egresso.controller;

import org.hibernate.HibernateException;
import org.zkoss.bind.annotation.Init;

import br.ufjf.egresso.model.Aluno;

public class AlunoController extends GenericController{
	
	private Aluno aluno = new Aluno();
	
	@Init
	public void init() throws HibernateException, Exception{
		Logado();
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
}
