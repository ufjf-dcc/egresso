package br.ufjf.egresso.controller;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Initiator;

import br.ufjf.egresso.business.ListaEsperaBusiness;
import br.ufjf.egresso.model.ListaEspera;

public class ListaController implements Initiator {
	
	@Override
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		Session session = Sessions.getCurrent();
		ListaEspera listaEspera = (ListaEspera) session.getAttribute("listaEspera");
		ListaEsperaBusiness listaEsperaBusiness = new ListaEsperaBusiness();
		if (!listaEsperaBusiness.checaLista(listaEspera)){ 
			Executions.sendRedirect("/home.zul");
			return;
		}
	}
	
}
