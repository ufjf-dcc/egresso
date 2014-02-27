package br.ufjf.egresso.controller;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.business.TurmaBusiness;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Turma;

public class FbTurmaController {

	private List<Aluno> alunos, filtraAlunos;
	private Turma turma;
	private String pesquisa,
			emptyMessage = "Nenhum aluno desta turma se cadastrou no aplicativo ainda", descricao;

	@Init
	public void init() {
		turma = new TurmaBusiness().getTurma(Integer.parseInt(Executions.getCurrent().getParameter("id")));
		descricao = "Turma do "+turma.getSemestre()+"º semestre de "+turma.getAno();
		alunos = new AlunoBusiness().getAlunos(turma);
		filtraAlunos = alunos;
	}

	public String getDescricao() {
		return descricao;
	}

	public List<Aluno> getFiltraAlunos() {
		return filtraAlunos;
	}

	public Turma getTurma() {
		return turma;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}

	public String getEmptyMessage() {
		return emptyMessage;
	}

	@Command
	@NotifyChange({ "filtraAlunos", "emptyMessage" })
	public void pesquisar() {
		List<Aluno> resultados = new ArrayList<Aluno>();
		for (Aluno aluno : alunos)
			if (aluno.getNome().trim().toLowerCase()
					.contains(pesquisa.trim().toLowerCase()))
				resultados.add(aluno);
		filtraAlunos = resultados;
		emptyMessage = "Nenhum resultado encontrado para \"" + pesquisa + "\"";
		BindUtils.postNotifyChange(null, null, this, "filtraAlunos");
	}

	@Command("limparPesquisa")
	public void limparPesquisa() {
		filtraAlunos = alunos;
		BindUtils.postNotifyChange(null, null, this, "filtraAlunos");
	}

	@Command
	public void verPerfil(@BindingParam("aluno") Aluno aluno) {
		Executions.sendRedirect("perfil.zul?id=" + aluno.getFacebookId());
	}

}