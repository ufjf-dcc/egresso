package br.ufjf.egresso.controller;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Initiator;

public class AdminAuthController implements Initiator {
/**
 * Redireciona para a página index.zul
 */
	@Override
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		if (Sessions.getCurrent()
				.getAttribute("admin") == null)
			Executions.sendRedirect("/index.zul");
	}
}
