package br.ufjf.egresso.controller;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Initiator;

import br.ufjf.egresso.utils.ConfHandler;

public class FbAuthController implements Initiator {
	/**
	 * Redireciona para página inicial 
	 * do aplicativo no Facebook
	 */
	@Override
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		if (Sessions.getCurrent()
				.getAttribute("aluno") == null)
			Executions.sendRedirect(ConfHandler.getConf("FB.CANVASPAGE"));
	}
}
