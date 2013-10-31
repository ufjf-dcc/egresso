package br.ufjf.egresso.controller;

import org.hibernate.HibernateException;
import org.zkoss.bind.annotation.Init;

public class HomeController extends GenericController {

	@Init
	public void init() throws HibernateException, Exception{
		Logado();
	}
	
}