package br.ufjf.egresso.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.business.TurmaBusiness;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.utils.ConfHandler;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.PictureSize;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

public class FbCadastroController {

	private Facebook facebook = (Facebook) Sessions.getCurrent().getAttribute(
			"facebook");
	private Aluno aluno = new Aluno();
	private TurmaBusiness turmaBusiness = new TurmaBusiness();
	private boolean matriculaExiste = false;
	private String ano;

	public Facebook getFacebook() {
		return facebook;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public String getAno() {
		return ano;
	}

	public boolean isMatriculaExiste() {
		return matriculaExiste;
	}

	public void setMatriculaExiste(boolean matriculaExiste) {
		this.matriculaExiste = matriculaExiste;
	}

	/**
	 * Valida os dados com o integra.
	 * @param cpf CPF informado.
	 * @param senha Senha informada.
	 * @return Se os dados são válidos ou não.
	 */
	private boolean verificaAlunoNoSiga(String cpf, String senha) {
		boolean alunoValido = false;
		BufferedReader reader = null;
		try {
			//Recebe o JSON do integra
			URL url = new URL(
					ConfHandler.getConf("INTEGRA") + cpf);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			try {
				JSONObject json = new JSONObject(buffer.toString());

				//Verifica se o CPF existe no integra
				String resultado = (String) json.get("codeResult");
				if (resultado.equals("OK")) {
					JSONObject dadosAluno = (JSONObject) json.get("result");

					//Verifica se a senha confere
					MessageDigest md = MessageDigest.getInstance("MD5");
					md.update(senha.getBytes());
					byte[] digest = md.digest();
					StringBuffer passmd5 = new StringBuffer();
					for (byte b : digest) {
						passmd5.append(String.format("%02x", b & 0xff));
					}

					//Se a senha está correta...
					if (((String) dadosAluno.get("passmd5")).equals(passmd5
							.toString())) {

						//...guarda as informaçẽos do aluno
						aluno = new Aluno();
						aluno.setNome(facebook.getMe().getName());
						aluno.setFacebookId(facebook.getMe().getId());
						aluno.setUrlFoto(facebook.getPictureURL(
								facebook.getMe().getId(),
								PictureSize.valueOf("large")).toExternalForm());
						aluno.setMatricula((String) dadosAluno.get("profile"));
						ano = aluno.getMatricula().substring(0, 4);
						BindUtils.postNotifyChange(null, null, this, "ano");
						alunoValido = true;
					} else {
						Messagebox.show("A senha digitada está incorreta.",
								"Erro de autenticação", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else if (resultado.equals("E_CPF_NAO_CADASTRADO")) {
					Messagebox
							.show("O CPF "
									+ cpf
									+ "não foi encontrado no SIGA. por favor, verifique se o número foi digitado corretamente.",
									"CPF não encontrado", Messagebox.OK,
									Messagebox.ERROR);
				}

			} catch (JSONException | FacebookException
					| NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return alunoValido;
	}

	/**
	 * Método para identificar o aluno.
	 * @param cpf CPF informado.
	 * @param senha Senha informada.
	 */
	@Command
	public void checaMatricula(@BindingParam("cpf") String cpf,
			@BindingParam("senha") String senha) {
		//Se o cpf e a senha foram preenchidos...
		if (cpf != null && cpf.trim().length() > 0) {
			if (senha != null && senha.trim().length() > 0) {
				if (verificaAlunoNoSiga(cpf, senha))
					//...lê as informações do SIGA
					Clients.evalJavaScript("formTurma()");
			} else
				Messagebox.show("É necessário informar a senha.", "Erro",
						Messagebox.OK, Messagebox.ERROR);

		} else
			Messagebox.show("É necessário informar seu CPF.", "Erro",
					Messagebox.OK, Messagebox.ERROR);
	}

	@Command
	public void checaTurma(@BindingParam("semestre") int semestre) {
		if (ano == null || ano.trim() == "")
			Messagebox.show(
					"Por favor, informe o ano em que você ingressou no curso.",
					"Erro", Messagebox.OK, Messagebox.ERROR);
		else {
			if (semestre == -1)
				Messagebox
						.show("Por favor, informe o semestre em que você ingressou no curso.",
								"Erro", Messagebox.OK, Messagebox.ERROR);

			else {
				aluno.setTurma(turmaBusiness.getTurma(Integer.parseInt(ano),
						semestre + 1));
				solicitaCadastro();
			}
		}
	}

	@Command
	public void solicitaCadastro() {
		if (!new AlunoBusiness().salvar(aluno))
			Messagebox
					.show("Não foi possível solicitar po cadastro. Por favor, tente novamente mais tarde.",
							"Erro", Messagebox.OK, Messagebox.ERROR);

	}

}
