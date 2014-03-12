package br.ufjf.egresso.controller;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import br.ufjf.egresso.business.TurmaBusiness;
import br.ufjf.egresso.model.Turma;

public class FbTurmasController {

	private List<Turma> turmas, filtraTurmas;
	private String pesquisa, emptyMessage = "Nenhuma turma encontrada";

	@Init
	public void init() {
		turmas = new TurmaBusiness().getTodas();
		filtraTurmas = turmas;
	}

	public List<Turma> getFiltraTurmas() {
		return filtraTurmas;
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
	@NotifyChange({ "filtraTurmas", "emptyMessage" })
	public void pesquisar() {
	}

	@Command("limparPesquisa")
	public void limparPesquisa() {
		filtraTurmas = turmas;
		BindUtils.postNotifyChange(null, null, this, "filtraTurmas");
	}

	@Command
	public void verTurma(@BindingParam("turma") Turma turma) {
		Executions.sendRedirect("turma.zul?id=" + turma.getId());
	}

}