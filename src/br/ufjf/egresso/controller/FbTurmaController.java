package br.ufjf.egresso.controller;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.ClientInfoEvent;

import br.ufjf.egresso.business.AlunoBusiness;
import br.ufjf.egresso.business.TurmaBusiness;
import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Turma;

public class FbTurmaController {

	private List<Aluno> alunos, filtraAlunos;
	private List<List<Aluno>> linhas;
	private List<String> semestres;
	private List<Turma> turmas;
	private TurmaBusiness turmaBusiness = new TurmaBusiness();
	private String pesquisa,
			emptyMessage = "Nenhum aluno desta turma se cadastrou no aplicativo ainda",
			descricao;

	@Init
	public void init() {	
		
		Turma turma = turmaBusiness.getTurma(((Aluno) Sessions.getCurrent()
				.getAttribute("aluno")).getId());
		turmas = turmaBusiness.getTodas();

		semestres = new ArrayList<String>();
		for (Turma t : turmas) {
			semestres.add(t.getAno() + " " + t.getSemestre() + "º semestre");
		}

		descricao = "Turma do " + turma.getSemestre() + "º semestre de "
				+ turma.getAno();
		alunos = new AlunoBusiness().getAlunos(turma);
		filtraAlunos = alunos;
	}
	
	@Command
	public void montaTabela(@BindingParam("event") ClientInfoEvent evt){
		int largura = evt.getDesktopWidth() - 300;
		System.out.println(largura);
		int inseridos = 0;
		linhas = new ArrayList<List<Aluno>>();
		
		List<Aluno> linha = new ArrayList<Aluno>();		
		
		for(Aluno a : filtraAlunos){
			if(largura / 220 < (inseridos + 1)){
				linhas.add(linha);
				inseridos = 0;
				linha = new ArrayList<Aluno>();
			}
			linha.add(a);
			inseridos++;
		}
		
		if(linha.size() > 0)
			linhas.add(linha);
		
		BindUtils.postNotifyChange(null, null, this, "linhas");
	}

	public List<List<Aluno>> getLinhas() {
		return linhas;
	}

	public String getDescricao() {
		return descricao;
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

	public List<String> getSemestres() {
		return semestres;
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

	@Command
	public void trocaTurma(@BindingParam("turma") String turmaDesc) {
		alunos = new AlunoBusiness().getAlunos(turmaBusiness.getTurma(Integer
				.parseInt(turmaDesc.substring(0, turmaDesc.indexOf(" "))),
				Integer.parseInt(turmaDesc.substring(
						turmaDesc.indexOf("º") - 1, turmaDesc.indexOf("º")))));
		filtraAlunos = alunos;
		BindUtils.postNotifyChange(null, null, this, "filtraAlunos");
	}

}