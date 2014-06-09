package br.ufjf.egresso.controller;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Initiator;

public class FbAuthController implements Initiator {

	@Override
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		if (Sessions.getCurrent()
				.getAttribute("aluno") == null)
			Executions.sendRedirect("https://apps.facebook.com/ufjf-dcc-egresso/");
	}
}
