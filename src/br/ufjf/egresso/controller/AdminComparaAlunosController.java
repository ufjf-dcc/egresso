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
	public void init() {
		solicitacao = new SolicitacaoBusiness().getSolicitacao(Executions
				.getCurrent().getParameter("id"));
		aluno = new AlunoBusiness().buscaPorMatricula(solicitacao
				.getMatricula());
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
		aluno.setFacebookId(solicitacao.getIdFacebook());
		aluno.setUrlFoto(solicitacao.getUrlFoto());
		if (new AlunoBusiness().editar(aluno))
			if (new SolicitacaoBusiness().exclui(solicitacao))
				Messagebox.show("Aluno confirmado com sucesso!", "Sucesso",
						Messagebox.OK, Messagebox.INFORMATION);
		Executions.sendRedirect("gerencia-alunos.zul");
	}

	@Command
	public void recusa() {
		if(new SolicitacaoBusiness().exclui(solicitacao))
			Executions.sendRedirect("gerencia-alunos.zul");
	}

}
