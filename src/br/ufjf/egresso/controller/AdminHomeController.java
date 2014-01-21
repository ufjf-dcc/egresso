package br.ufjf.egresso.controller;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Sessions;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.business.SolicitacaoBusiness;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Solicitacao;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Friend;
import facebook4j.PostUpdate;

public class AdminHomeController {

	private List<Solicitacao> solicitacoes;
	private Facebook facebook;
	private SolicitacaoBusiness solicitacaoBusiness;

	@Init
	public void init() {
		solicitacaoBusiness = new SolicitacaoBusiness();
		solicitacoes = new SolicitacaoBusiness().getTodos();
		facebook = (Facebook) Sessions.getCurrent().getAttribute("facebook");
	}

	@NotifyChange
	@Command
	public void aceita(@BindingParam("solicitacao") Solicitacao solicitacao) {
		AlunoBusiness alunoBusiness = new AlunoBusiness();
		Aluno aluno = alunoBusiness.buscaPorMatricula(solicitacao.getMatricula());
		aluno.setFacebookId(solicitacao.getIdFacebook());
		aluno.setUrlFoto(solicitacao.getUrlFoto());
		alunoBusiness.editar(aluno);
		solicitacoes.remove(solicitacao);		
		solicitacaoBusiness.exclui(solicitacao);
	}

	@NotifyChange
	@Command
	public void recusa(@BindingParam("solicitacao") Solicitacao solicitacao) {
		solicitacoes.remove(solicitacao);
		solicitacaoBusiness.exclui(solicitacao);
	}

	@Command
	public void convida(@BindingParam("id") String id) throws FacebookException {
		facebook.postFeed(id, new PostUpdate("teste"));
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

}
