package br.ufjf.egresso.controller;

import org.hibernate.HibernateException;
import org.zkoss.bind.annotation.Init;
import br.ufjf.egresso.model.Aluno;

public class HomeController extends CommonsController {

	@Init
	public void init() throws HibernateException, Exception{
		testaLogado();
	}
	
	public Aluno getAluno(){
		return super.getAlunoCommon();
	}
}