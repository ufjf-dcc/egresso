package br.ufjf.egresso.controller;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.business.SolicitacaoBusiness;
import br.ufjf.egresso.business.TurmaBusiness;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Solicitacao;
import br.ufjf.egresso.model.Turma;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.PictureSize;

public class FbCadastroController {

	private Facebook facebook = (Facebook) Sessions.getCurrent().getAttribute(
			"facebook");
	private String urlpic;
	private Solicitacao solicitacao = new Solicitacao();
	private TurmaBusiness turmaBusiness = new TurmaBusiness();
	private List<Turma> turmas = turmaBusiness.getTodas();
	private boolean matriculaExiste = false;

	@Init
	public void init() throws FacebookException {
		urlpic = facebook.getPictureURL(facebook.getMe().getId(),
				PictureSize.valueOf("large")).toExternalForm();
		solicitacao.setNome(facebook.getMe().getName());
		solicitacao.setIdFacebook(facebook.getMe().getId());
	}

	public Facebook getFacebook() {
		return facebook;
	}

	public String getUrlpic() {
		return urlpic;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public boolean isMatriculaExiste() {
		return matriculaExiste;
	}

	public void setMatriculaExiste(boolean matriculaExiste) {
		this.matriculaExiste = matriculaExiste;
	}

	@Command
	public void checaMatricula(@BindingParam("txt") Textbox txt) {
		String matricula = txt.getValue();
		if (matricula != null) {
			Aluno aluno = new AlunoBusiness().buscaPorMatricula(matricula);
			if (aluno == null)
				Messagebox
						.show("Não temos registro de um aluno com matrícula "
								+ matricula
								+ ". Certifique-se que digitou a matrícula corretamente.",
								"Erro", Messagebox.OK, Messagebox.ERROR);
			else if (aluno.getFacebookId() != null)
				Messagebox.show("O aluno de matrícula " + aluno.getMatricula()
						+ " já está cadastrado no aplicativo.", "Erro",
						Messagebox.OK, Messagebox.ERROR);
			else {
				solicitacao.setMatricula(txt.getValue());
				Clients.evalJavaScript("formTurma()");
			}
		} else
			Messagebox.show("É necessário informar a matrícula e a turma.",
					"Erro", Messagebox.OK, Messagebox.ERROR);
	}

	@Command
	public void checaTurma(@BindingParam("ano") String ano,
			@BindingParam("semestres") Radiogroup semestres) {
		if (ano == null || ano.trim() == "")
			Messagebox.show(
					"Por favor, informe o ano em que você ingressou no curso.",
					"Erro", Messagebox.OK, Messagebox.ERROR);
		else {
			int semestre = semestres.getSelectedIndex() + 1;
			if (semestre == 0)
				Messagebox
						.show("Por favor, informe o semestre em que você ingressou no curso.",
								"Erro", Messagebox.OK, Messagebox.ERROR);

			else {
				solicitacao.setTurma(turmaBusiness.getTurma(
						Integer.parseInt(ano), semestre));
				solicitaCadastro();
			}
		}
	}

	@Command
	public void solicitaCadastro() {
		SolicitacaoBusiness solicitacaoBusiness = new SolicitacaoBusiness();
		if (solicitacaoBusiness.validar(solicitacao)) {

			solicitacao.setUrlFoto(urlpic);
			if (!new SolicitacaoBusiness().salvar(solicitacao))
				Messagebox
						.show("Não foi possível solicitar po cadastro. Por favor, tente novamente mais tarde.",
								"Erro", Messagebox.OK, Messagebox.ERROR);
		}

	}

}
