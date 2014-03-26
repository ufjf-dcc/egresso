package br.ufjf.egresso.utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Sessions;

import facebook4j.Facebook;
import facebook4j.FacebookException;

public class ApiTest {
	private Facebook facebook;

	@Init
	public void init() {
		facebook = (Facebook) Sessions.getCurrent().getAttribute("facebook");
	}

	@Command
	public void atualizarStatus() throws FacebookException,
			MalformedURLException {
		facebook.postStatusMessage("Hello World from Facebook4J.");
	}

	@Command
	public void postLink() throws MalformedURLException, FacebookException {
		facebook.postLink(new URL("http://google.com"));
	}

	// está sem permissão
	@Command
	public void postLinkT() throws MalformedURLException, FacebookException {
		facebook.postLink("Mensagem", new URL("http://google.com"));
	}

	@Command
	public void postLinkT2() throws MalformedURLException, FacebookException {
		facebook.postLink(new URL("http://google.com"), "Mensagem");
	}

	// está sem permissão
	@Command
	public void postLinkTD() throws MalformedURLException, FacebookException {
		facebook.postLink("Mensagem", new URL("http://google.com"), "Mensagem2");
	}

}
