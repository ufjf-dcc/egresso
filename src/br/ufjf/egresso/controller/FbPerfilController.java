package br.ufjf.egresso.controller;

import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import br.ufjf.egresso.business.AtuacaoBusiness;
import br.ufjf.egresso.business.TurmaBusiness;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Atuacao;
import br.ufjf.egresso.model.Turma;

public class FbPerfilController {

	private Aluno aluno;
	private AtuacaoBusiness atuacaoBusiness = new AtuacaoBusiness();
	private List<Atuacao> todasAtuacoes =  atuacaoBusiness.getTodas();
	private Atuacao novaAtuacao = new Atuacao();
	

	@Init
	public void init() {
		Session session = Sessions.getCurrent();
		aluno = (Aluno) session.getAttribute("aluno");
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
	public Atuacao getNovaAtuacao() {
		return novaAtuacao;
	}

	public void setNovaAtuacao(Atuacao novaAtuacao) {
		this.novaAtuacao = novaAtuacao;
	}

}