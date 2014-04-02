package br.ufjf.egresso.controller;

import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.hibernate.HibernateException;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Label;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.model.Aluno;

/** Classe para controlar a página de integração com o LinkedIn **/
public class LkIndexController {

	/**
	 * Método para solicitar autenticação do usuário do LinkedIn, obter um
	 * Access Token e armazená-lo no {@link Aluno} correspondente
	 **/
	@Command
	public void autentica(@BindingParam("lbl") Label lbl)
			throws HibernateException, Exception {
		String alunoId = (String) Executions.getCurrent().getParameter("id");

		String lkSecretKey = "zRBXDl6cNLEp8VbH";
		String lkApiKey = "77gl5puzk1zfno";
		String myState = "DUIGYTeffuhEWIU78fmnIu";
		String redirectUri = "http://localhost:8080/egresso/lk/index.zul?id="
				+ alunoId;
		// String redirectUri =
		// "http://monografias.nrc.ice.ufjf.br/egresso/fb/";
		String lkAuthenticationPage = "https://www.linkedin.com/uas/oauth2/authorization?response_type=code"
				+ "&client_id="
				+ lkApiKey
				+ "&scope=r_fullprofile"
				+ "&state="
				+ myState + "&redirect_uri=" + redirectUri;

		String code = Executions.getCurrent().getParameter("code");
		String stateReceived = Executions.getCurrent().getParameter("state");
		String error = Executions.getCurrent().getParameter("error");
		String errorDescription = Executions.getCurrent().getParameter(
				"error_description");

		if (stateReceived != null) {

			// Se o state recebido é diferente do original, pode ser uma
			// tentativa maliciosa
			if (!stateReceived.equals(myState)) {
				lbl.setValue("State diferente = " + stateReceived);
				return;
			}

			if (code != null) {
				// Usuário autenticou
				lbl.setValue("Authentication code = " + code);

				// Redireciona para outra página e obtêm o Access Token do
				// usuário para esta API key
				String lkRequestTokenPage = "https://www.linkedin.com/uas/oauth2/accessToken?grant_type=authorization_code"
						+ "&code="
						+ code
						+ "&redirect_uri="
						+ redirectUri
						+ "&client_id="
						+ lkApiKey
						+ "&client_secret="
						+ lkSecretKey;

				String receivedJSON = IOUtils.toString(new URL(
						lkRequestTokenPage));
				System.out.println(receivedJSON);
				String lkAccessToken = receivedJSON.substring(
						receivedJSON.indexOf("_token\":\"") + 9,
						receivedJSON.indexOf("\"}"));

				AlunoBusiness alunoBusiness = new AlunoBusiness();
				Aluno aluno = alunoBusiness.getAluno(Integer.parseInt(alunoId));
				aluno.setLkAccessToken(lkAccessToken);
				alunoBusiness.editar(aluno);
				
				//fecha a aba atual
				Clients.evalJavaScript("close()");

			} else if (error != null) {
				// Usuário não autenticou
				lbl.setValue("Error description = " + errorDescription);
			}

			return;

		} else {
			// Redireciona para a página de autenticação
			Executions.sendRedirect(lkAuthenticationPage);
		}
	}
}
