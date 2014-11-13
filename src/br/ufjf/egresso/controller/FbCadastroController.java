package br.ufjf.egresso.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
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
import br.ufjf.ice.integra3.ws.login.interfaces.IWsLogin;
import br.ufjf.ice.integra3.ws.login.interfaces.Profile;
import br.ufjf.ice.integra3.ws.login.interfaces.WsException_Exception;
import br.ufjf.ice.integra3.ws.login.interfaces.WsLoginResponse;
import br.ufjf.ice.integra3.ws.login.interfaces.WsUserInfoResponse;
import br.ufjf.ice.integra3.ws.login.service.WSLogin;
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
			aluno.setAtivo(Aluno.ATIVO);
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
	    senha = md5(senha);
	    try {
	    	
			String token = ConfHandler.getConf("FILE.TOKEN");
			System.out.println("Logando...");
			IWsLogin integra = new WSLogin().getWsLoginServicePort();
			WsLoginResponse user = integra.login(cpf, senha, token);
			WsUserInfoResponse infos = integra.getUserInformation(user.getToken());
			List<Profile> profiles = (infos.getProfileList()).getProfile();
			
			aluno = new AlunoBusiness()
						.buscaPorMatricula((profiles.get(0).getMatricula()));
				if (aluno != null) {
					// ...guarda as informaçẽos do aluno
					aluno.setNome(facebook.getMe().getName());
					aluno.setFacebookId(facebook.getMe().getId());
					aluno.setUrlFoto(facebook.getPictureURL(
							facebook.getMe().getId(),
							PictureSize.valueOf("large"))
							.toExternalForm());
					aluno.setMatricula((profiles.get(0).getMatricula()));
					aluno.setAtivo(Aluno.ATIVO);
					BindUtils.postNotifyChange(null, null, this, "ano");
					alunoValido = true;
				} else {
					aluno = new Aluno();
					aluno.setAtivo(Aluno.ATIVO);
					// ...guarda as informaçẽos do aluno
					aluno.setNome(facebook.getMe().getName());
					aluno.setFacebookId(facebook.getMe().getId());
					aluno.setUrlFoto(facebook.getPictureURL(
							facebook.getMe().getId(),
							PictureSize.valueOf("large"))
							.toExternalForm());
					aluno.setMatricula(((profiles.get(0).getMatricula())));
					aluno.setAtivo(Aluno.ATIVO);
					BindUtils.postNotifyChange(null, null, this, "ano");
					alunoValido = true;
				}
			


	    }catch(WsException_Exception e){
	    	switch(e.getFaultInfo().getErrorUserMessage()){
	    	case "Usuário não encontrado":
	    		Messagebox
				.show("O CPF "
						+ cpf
						+ "não foi encontrado no SIGA. Por favor, verifique se o número foi digitado corretamente.",
						"CPF não encontrado", Messagebox.OK,
						Messagebox.ERROR);
	    		break;
	    	default:
	    		Messagebox.show("Não foi possível autenticar. Por favor, verifique se o número foi digitado corretamente.",
									"Erro", Messagebox.OK, Messagebox.ERROR);
			}
	    
	    } catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		if (!new AlunoBusiness().salvaOuEdita(aluno))
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

	public static String md5(String input) {

		String md5 = null;

		if (null == input)
			return null;

		try {
			// Create MessageDigest object for MD5
			MessageDigest digest = MessageDigest.getInstance("MD5");

			// Update input string in message digest
			digest.update(input.getBytes(), 0, input.length());

			// Converts message digest value in base 16 (hex)
			md5 = new BigInteger(1, digest.digest()).toString(16);

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		return md5;
	}

}
