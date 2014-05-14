package br.ufjf.egresso.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.business.TurmaBusiness;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Turma;
import br.ufjf.egresso.utils.ConfHandler;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.PictureSize;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

/**
 * Classe para controlar a página /fb/cadasro.zul
 * 
 * @author Jorge Augusto da Silva Moreira
 * **/
public class FbCadastroController {

	private Facebook facebook;
	private Aluno aluno;
	private Set<Integer> anos;
	private int intervalo;
	/** Variável para indicar a existência dos semestres para determiado ano **/
	private int semestres = 3;

	@Init
	public void init() {
		intervalo = 4;
		aluno = (Aluno) Sessions.getCurrent().getAttribute("aluno");
		if (aluno == null) {
			aluno = new Aluno();
			aluno.setAtivo(true);
		}
		facebook = (Facebook) Sessions.getCurrent().getAttribute("facebook");
		anos = new HashSet<Integer>();
		for (Turma turma : new TurmaBusiness().getTodas()) {
			anos.add(turma.getAno());
		}
	}

	public Facebook getFacebook() {
		return facebook;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public Set<Integer> getAnos() {
		return anos;
	}

	public int getIntervalo() {
		return intervalo;
	}

	public int getSemestres() {
		return semestres;
	}

	@Command
	public void atualizaSemestres(@BindingParam("ano") String ano) {
		List<Turma> turmas = new TurmaBusiness()
				.getTurmas(Integer.valueOf(ano));
		semestres = turmas.size() == 2 ? 3 : turmas.get(0).getSemestre();
		BindUtils.postNotifyChange(null, null, this, "semestres");
	}

	/**
	 * Valida os dados com o integra.
	 * 
	 * @param cpf
	 *            CPF informado.
	 * @param senha
	 *            Senha informada.
	 * @return Se os dados são válidos ou não.
	 */
	private boolean verificarAlunoNoIntegra(String cpf, String senha) {
		boolean alunoValido = false;
		BufferedReader reader = null;
		try {
			// Recebe o JSON do integra
			URL url = new URL(ConfHandler.getConf("INTEGRA.URL") + cpf);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			try {
				JSONObject json = new JSONObject(buffer.toString());

				// Verifica se o CPF existe no integra
				switch ((String) json.get("codeResult")) {
				case "OK":
					JSONObject dadosAluno = (JSONObject) json.get("result");

					// Verifica se a senha confere
					MessageDigest md = MessageDigest.getInstance("MD5");
					md.update(senha.getBytes());
					byte[] digest = md.digest();
					StringBuffer passmd5 = new StringBuffer();
					for (byte b : digest) {
						passmd5.append(String.format("%02x", b & 0xff));
					}

					// Se a senha está correta...
					if (((String) dadosAluno.get("passmd5")).equals(passmd5
							.toString())) {

						// ...guarda as informaçẽos do aluno
						aluno = new Aluno();
						aluno.setNome(facebook.getMe().getName());
						aluno.setFacebookId(facebook.getMe().getId());
						aluno.setUrlFoto(facebook.getPictureURL(
								facebook.getMe().getId(),
								PictureSize.valueOf("large")).toExternalForm());
						aluno.setMatricula((String) dadosAluno.get("profile"));
						BindUtils.postNotifyChange(null, null, this, "ano");
						alunoValido = true;
					} else {
						Messagebox.show("A senha digitada está incorreta.",
								"Erro de autenticação", Messagebox.OK,
								Messagebox.ERROR);
					}
					break;
				case "E_CPF_NAO_CADASTRADO":
					Messagebox
							.show("O CPF "
									+ cpf
									+ "não foi encontrado no SIGA. Por favor, verifique se o número foi digitado corretamente.",
									"CPF não encontrado", Messagebox.OK,
									Messagebox.ERROR);
					break;
				case "E_CPF_TAM_INV":
					Messagebox.show(
							"O formato do CPF digitado está incorreto.",
							"CPF inválido", Messagebox.OK, Messagebox.ERROR);
					break;
				default:
					Messagebox
							.show("Não foi possível autenticar. Por favor, verifique se o número foi digitado corretamente.",
									"Erro", Messagebox.OK, Messagebox.ERROR);
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
	 * 
	 * @param cpf
	 *            CPF informado.
	 * @param senha
	 *            Senha informada.
	 */
	@Command
	public void checarMatricula(@BindingParam("cpf") String cpf,
			@BindingParam("senha") String senha) {
		// Se o cpf e a senha foram preenchidos...
		if (cpf != null && cpf.trim().length() > 0) {
			if (senha != null && senha.trim().length() > 0) {
				cpf = cpf.replace(".", "");
				cpf = cpf.replace("-", "");
				if (verificarAlunoNoIntegra(cpf, senha))
					// ...lê as informações do SIGA
					Clients.evalJavaScript("formTurma()");
			} else
				Messagebox.show("É necessário informar a senha.", "Erro",
						Messagebox.OK, Messagebox.ERROR);

		} else
			Messagebox.show("É necessário informar seu CPF.", "Erro",
					Messagebox.OK, Messagebox.ERROR);
	}

	@Command
	public void checarTurma(@BindingParam("ano") String ano,
			@BindingParam("semestre") int semestre) {
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
				aluno.setTurma(new TurmaBusiness().getTurma(
						Integer.parseInt(ano), semestre + 1));
			}
		}
	}

	@Command
	public void cadastrar() {
		if (!new AlunoBusiness().salvar(aluno))
			Messagebox
					.show("Não foi realizar o cadastro. Por favor, tente novamente mais tarde.",
							"Erro", Messagebox.OK, Messagebox.ERROR);
		else {
			while (intervalo > 0) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				intervalo--;
				BindUtils.postNotifyChange(null, null, this, "intervalo");
			}

			Sessions.getCurrent().setAttribute("aluno", aluno);
			Executions.sendRedirect("perfil.zul");
		}

	}

}
