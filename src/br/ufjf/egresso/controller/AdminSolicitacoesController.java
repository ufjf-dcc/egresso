package br.ufjf.egresso.controller;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.business.SolicitacaoBusiness;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Solicitacao;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Friend;

public class AdminSolicitacoesController {

	private List<Solicitacao> solicitacoes;
	private Facebook facebook;
	private SolicitacaoBusiness solicitacaoBusiness;
	private Solicitacao solicitacao;
	private Aluno aluno;

	@Init
	public void init() {
		solicitacaoBusiness = new SolicitacaoBusiness();
		solicitacoes = new SolicitacaoBusiness().getTodos();
		facebook = (Facebook) Sessions.getCurrent().getAttribute("facebook");
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	@Command
	public void aceita(@BindingParam("window") Window window) {
		aluno.setFacebookId(solicitacao.getIdFacebook());
		aluno.setUrlFoto(solicitacao.getUrlFoto());
		if (new AlunoBusiness().editar(aluno))
			if (new SolicitacaoBusiness().exclui(solicitacao))
				Messagebox.show("Aluno confirmado com sucesso!", "Sucesso",
						Messagebox.OK, Messagebox.INFORMATION);
		solicitacoes.remove(solicitacao);
		BindUtils.postNotifyChange(null, null, this, "solicitacoes");
		aluno = null;
		solicitacao = null;
		window.detach();
	}

	@Command
	public void recusa(@BindingParam("window") Window window) {
		if (solicitacaoBusiness.exclui(solicitacao)){
			solicitacoes.remove(solicitacao);
			Messagebox.show("Solicitação recusada com sucesso!", "Sucesso",
					Messagebox.OK, Messagebox.INFORMATION);
			BindUtils.postNotifyChange(null, null, this, "solicitacoes");
		}
		aluno = null;
		solicitacao = null;
		window.detach();
	}

	public List<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}

	public List<Friend> getAmigos() throws FacebookException {
		return (List<Friend>) facebook.getFriends();
	}

	public Facebook getFacebook() {
		return facebook;
	}

	public void setFacebook(Facebook facebook) {
		this.facebook = facebook;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@NotifyChange({ "aluno", "solicitacao" })
	@Command
	public void comparar(@BindingParam("solicitacao") Solicitacao solicitacao,
			@BindingParam("window") final Window window) {
		aluno = new AlunoBusiness().buscaPorMatricula(solicitacao
				.getMatricula());
		this.solicitacao = solicitacao;
		if (aluno == null) {
			Messagebox
					.show("Não é possível fazer uma comparação, pois nenhum aluno foi encontrado com esta matrícula ("
							+ solicitacao.getMatricula()
							+ "). Deseja excluir esta solicitação?", "Erro",
							Messagebox.YES | Messagebox.NO,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener() {
								public void onEvent(Event e) {
									if (Messagebox.ON_YES.equals(e.getName())) {
										recusa(window);
									}
								}
							});
		} else {
			window.doModal();
		}
	}

}
