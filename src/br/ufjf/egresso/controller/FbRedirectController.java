package br.ufjf.egresso.controller;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

public class FbRedirectController {

	@Init
	public void redirect() {
		Executions.sendRedirect("https://apps.facebook.com/ufjf-dcc-egresso");
	}

}
