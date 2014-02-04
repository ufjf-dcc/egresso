package br.ufjf.egresso.controller;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.business.TurmaBusiness;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Turma;
import facebook4j.Friend;

public class FbTurmaController {

	private List<Turma> turmas = new TurmaBusiness().getTodas();
	private List<Aluno> alunos;
	private Aluno aluno;
	private Turma turma;
	private List<Friend> amigos = new ArrayList<Friend>();
	private String pesquisa,
			emptyMessage = "Nenhum aluno deste semestre se cadastrou no aplicativo";

	@Init
	public void init() {
		Session session = Sessions.getCurrent();
		aluno = (Aluno) session.getAttribute("aluno");
		turma = aluno.getTurma();
		selecionaTurma();
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Friend> getAmigos() {
		return amigos;
	}

	public void setAmigos(List<Friend> amigos) {
		this.amigos = amigos;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
		selecionaTurma();
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
	@NotifyChange({ "alunos", "emptyMessage", "turma" })
	public void pesquisar() {
		List<Aluno> resultados = new ArrayList<Aluno>();
		for (Aluno aluno : new AlunoBusiness().getTodos())
			if (aluno.getNome().trim().toLowerCase()
					.contains(pesquisa.trim().toLowerCase()))
				resultados.add(aluno);
		alunos = resultados;
		emptyMessage = "Nenhum resultado encontrado para \"" + pesquisa + "\"";
		turma = null;
	}

	@Command("limparPesquisa")
	public void limparPesquisa() {
		turma = aluno.getTurma();
		BindUtils.postNotifyChange(null, null, this, "turma");
		selecionaTurma();
	}

	public void selecionaTurma() {
		alunos = new AlunoBusiness().buscaPorTurma(turma);
		emptyMessage = "Nenhum aluno deste semestre se cadastrou no aplicativo";
		pesquisa = null;
		BindUtils.postNotifyChange(null, null, this, "emptyMessage");
		BindUtils.postNotifyChange(null, null, this, "alunos");
		BindUtils.postNotifyChange(null, null, this, "pesquisa");
	}

	@Command
	public void verPerfil(@BindingParam("aluno") Aluno aluno) {
		Executions.sendRedirect("perfil.zul?id=" + aluno.getFacebookId());
	}

}