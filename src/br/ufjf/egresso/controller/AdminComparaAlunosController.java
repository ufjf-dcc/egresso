package br.ufjf.egresso.controller;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.business.SolicitacaoBusiness;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Solicitacao;

public class AdminComparaAlunosController {
	private Solicitacao solicitacao;
	private Aluno aluno;
	
	@Init
	public void init(){
		System.out.println(Executions.getCurrent().getParameter("id"));
		solicitacao = new SolicitacaoBusiness().getSolicitacao(Executions.getCurrent().getParameter("id"));
		aluno = new AlunoBusiness().buscaPorMatricula(solicitacao.getMatricula());
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
	public void aceita() {
		AlunoBusiness alunoBusiness = new AlunoBusiness();
		Aluno aluno = alunoBusiness.buscaPorMatricula(solicitacao
				.getMatricula());
		if (aluno == null) {
			Messagebox.show("Nenhum aluno foi encontrado com esta matrícula ("
					+ solicitacao.getMatricula() + ").", "Erro", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		aluno.setFacebookId(solicitacao.getIdFacebook());
		aluno.setUrlFoto(solicitacao.getUrlFoto());
		alunoBusiness.editar(aluno);
		new SolicitacaoBusiness().exclui(solicitacao);
	}

	@Command
	public void recusa() {
		new SolicitacaoBusiness().exclui(solicitacao);
	}

}
