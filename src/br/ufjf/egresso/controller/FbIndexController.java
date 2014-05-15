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

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.utils.ConfHandler;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;

public class FbIndexController {

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
				Facebook facebook = new FacebookFactory().getInstance();
				
				facebook.setOAuthAccessToken(accessToken);
				Sessions.getCurrent().setAttribute("facebook", facebook);

				// Verifica se o aluno já foi autorizado ou se já solicitou
				// cadastro.
				Aluno aluno = new AlunoBusiness().getAluno(facebook.getId());
				if (aluno != null && aluno.isAtivo()) {
					Sessions.getCurrent().setAttribute("aluno", aluno);
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
