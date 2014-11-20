package br.ufjf.egresso.controller;

import java.net.URLEncoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.HibernateException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.utils.ConfHandler;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.PictureSize;
import facebook4j.auth.AccessToken;
/**
 * Classe que controla a página index.zul
 * @author Eduardo Rocha Soares, Jorge Moreira da Silva
 *
 */
public class FbIndexController {
	/**
	 * Faz a autenticação do aluno que está tentando acessar 
	 * o aplicativo, se a autenticação for bem sucedida , redireciona
	 * para a página inicial do aplicativo, se não for encontrado
	 * registro no banco é direcionado para página de cadastro
	 * @throws HibernateException
	 * 	Lança uma exceção se não conseguir realizar
	 *  a consulta no banco de dados
	 * @throws Exception
	 */
	@Init
	public void autentica() throws HibernateException, Exception {

		String fbSecretKey = ConfHandler.getConf("FB.APPSECRET");
		String fbAppId = ConfHandler.getConf("FB.APPID");
		String redirectUrl = ConfHandler.getConf("FB.REDIRECTURL");
		String fbCanvasPage = ConfHandler.getConf("FB.CANVASPAGE");

		if (Executions.getCurrent().getParameter("signed_request") != null) {

			Base64 base64 = new Base64(true);

			String[] signedRequest = Executions.getCurrent()
					.getParameter("signed_request").split("\\.", 2);

			String sig = new String(base64.decode(signedRequest[0]
					.getBytes("UTF-8")));

			JSONObject data = (JSONObject) new JSONParser().parse(new String(
					base64.decode(signedRequest[1].getBytes("UTF-8"))));

			if (!data.get("algorithm").equals("HMAC-SHA256")) {
				return;
			}

			try {
				if (!hmacSHA256(signedRequest[1], fbSecretKey).equals(sig)) {
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (!data.containsKey("user_id")
					|| !data.containsKey("oauth_token")) {
				Executions
						.getCurrent()
						.sendRedirect(
								"https://www.facebook.com/dialog/oauth?client_id="
										+ fbAppId
										+ "&redirect_uri="
										+ URLEncoder.encode(redirectUrl,
												"UTF-8") + "&scope="
										+ ConfHandler.getConf("FB.PERMISSIONS"),
								"_top");
			} else {
				AccessToken accessToken = new AccessToken(
						(String) data.get("oauth_token"));
				Facebook facebook;

				try {
					facebook = new FacebookFactory().getInstance();
					facebook.setOAuthAccessToken(accessToken);
				} catch (IllegalStateException e) {
					facebook = new FacebookFactory().getInstance();
					facebook.setOAuthAppId(fbAppId, fbSecretKey);
					facebook.setOAuthAccessToken(accessToken);
				}
				Sessions.getCurrent().setAttribute("facebook", facebook);

				// Verifica se o aluno já foi autorizado ou se já solicitou
				// cadastro.
				Aluno aluno;
				try {
					aluno = new AlunoBusiness().getAluno(facebook.getId());
				} catch (HibernateException e) {
					Messagebox.show("Erro ao consultar o banco de dados. Atualize a página e tente novamente", "Erro",
							Messagebox.OK, Messagebox.ERROR);
					return;
				}

				if (aluno != null && aluno.getAtivo() == Aluno.ATIVO) {
					Sessions.getCurrent().setAttribute("aluno", aluno);
					aluno.setUrlFoto(facebook.getPictureURL(
							facebook.getMe().getId(),
							PictureSize.valueOf("large")).toExternalForm());
					new AlunoBusiness().editar(aluno);

					/*
					 * aluno.setUrlFoto(facebook.getPictureURL(
					 * facebook.getMe().getId(),
					 * PictureSize.valueOf("large")).toExternalForm()); new
					 * AlunoBusiness().editar(aluno);
					 */
					Executions.sendRedirect("/fb/turma.zul");
				} else {
					if (aluno != null)
						Sessions.getCurrent().setAttribute("aluno", aluno);

					Executions.sendRedirect("/fb/cadastro.zul");
				}
			}

		} else {
			Executions.sendRedirect(fbCanvasPage);
		}

	}

	private String hmacSHA256(String data, String key) throws Exception {
		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"),
				"HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(secretKey);
		byte[] hmacData = mac.doFinal(data.getBytes("UTF-8"));
		return new String(hmacData);
	}

}
