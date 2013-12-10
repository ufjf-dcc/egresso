package br.ufjf.egresso.controller;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Initiator;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.business.ListaEsperaBusiness;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;

public class RedirectController implements Initiator {
	
	@Override
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		
		Facebook facebook = (Facebook) Sessions.getCurrent().getAttribute("facebook");
		AccessToken access = null;
		if(facebook != null){
			access = facebook.getOAuthAccessToken();
			Sessions.getCurrent().setAttribute("token", access);
			Sessions.getCurrent().setAttribute("facebook", null);
		} else
			access = (AccessToken) Sessions.getCurrent().getAttribute("token");
		facebook = new FacebookFactory().getInstance(access);
		Sessions.getCurrent().setAttribute("facebook", facebook);
		
		AlunoBusiness alunoBusiness = new AlunoBusiness();
		ListaEsperaBusiness listaEsperaBusiness = new ListaEsperaBusiness();
		if (alunoBusiness.login(facebook.getId())){  //Verifica se o usuário já está no sistema
			Executions.sendRedirect("home.zul");
		} else if (listaEsperaBusiness.naLista(facebook.getId())){ //Verifica se o usuário está na lista de espera
			Executions.sendRedirect("lista.zul");
		}else Executions.sendRedirect("cadastro.zul");
		
	}

}
