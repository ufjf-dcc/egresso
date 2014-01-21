package br.ufjf.egresso.controller;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;

import br.ufjf.egresso.business.SolicitacaoBusiness;
import br.ufjf.egresso.model.Solicitacao;
import br.ufjf.egresso.model.Turma;
import br.ufjf.egresso.persistent.impl.TurmaDAO;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.PictureSize;

public class FbCadastroController {

	private Facebook facebook = (Facebook) Sessions.getCurrent().getAttribute(
			"facebook");
	private String urlpic;
	private Solicitacao solicitacao = new Solicitacao();
	private List<Turma> turmas = new TurmaDAO().getTodas();

	@Init
	public void init() throws FacebookException {
		urlpic = facebook.getPictureURL(facebook.getMe().getId(),
				PictureSize.valueOf("large")).toExternalForm();
		solicitacao.setNome(facebook.getMe().getName());
		solicitacao.setIdFacebook(facebook.getMe().getId());
	}

	@Command
	public void solicitaCadastro() {
		solicitacao.setUrlFoto(urlpic);
		if (new SolicitacaoBusiness().salvar(solicitacao))
			Executions.sendRedirect("/fb/solicitacao-em-espera.zul");
		else
			Messagebox
					.show("Não foi possível solicitar po cadastro. Por favor, tente novamente mais tarde.",
							"Erro", Messagebox.OK, Messagebox.ERROR);
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

}
