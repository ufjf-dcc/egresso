package br.ufjf.egresso.controller;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;

import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.ListaEspera;
import br.ufjf.egresso.model.Turma;
import br.ufjf.egresso.persistent.impl.AlunoDAO;
import br.ufjf.egresso.persistent.impl.ListaEsperaDAO;
import br.ufjf.egresso.persistent.impl.TurmaDAO;

public class HomeController extends GenericController{

	private TurmaDAO turmaDAO = new TurmaDAO();
	private ListaEsperaDAO listaEsperaDAO = new ListaEsperaDAO();
	private List<Turma> turmas = turmaDAO.getTurmas();
	private List<ListaEspera> solicitacoes =  listaEsperaDAO.getListasEspera();
	int[] linhas = new int[] {0,1,2,3,4,5,6};
	
	public String getMenuSolicitacoes(){
		if (aluno != null) {
			int tipoUsuario = aluno.getTipoPermissao();
			if (tipoUsuario == 2){
				return "/solicitacoesAdmin.zul";
			}
			else return "";
		}
		return null;	
	}

	@Command
	public void aceita(@BindingParam("solicitacao")ListaEspera listaEspera ){
		ListaEsperaDAO listaEsperaDAO = new ListaEsperaDAO();
		AlunoDAO alunoDAO = new AlunoDAO();
		Aluno aluno = alunoDAO.retornaAlunoM(listaEspera.getMatricula());
		aluno.setIdfacebook(listaEspera.getIdfacebook());
		alunoDAO.editar(aluno);
		listaEsperaDAO.exclui(listaEspera);
		Executions.sendRedirect("/home.zul");
	}
	
	
	public List<Turma> getTurmas() {
		return turmas;
	}

	public List<ListaEspera> getSolicitacoes() {
		return solicitacoes;
	}

	public int[] getLinhas() {
		return linhas;
	}

	
	
	
}