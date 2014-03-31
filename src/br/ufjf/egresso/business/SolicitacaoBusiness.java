package br.ufjf.egresso.business;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import br.ufjf.egresso.model.Solicitacao;
import br.ufjf.egresso.model.Turma;
import br.ufjf.egresso.persistent.SolicitacaoDAO;

public class SolicitacaoBusiness {
	private SolicitacaoDAO solicitacaoDAO;
	private List<String> errors;

	public SolicitacaoBusiness() {
		this.errors = new ArrayList<String>();
		solicitacaoDAO = new SolicitacaoDAO();
	}
	
	public List<String> getErrors() {
		return errors;
	}
	
	public boolean validar(Solicitacao solicitacao) {
		errors.clear();

		validaMatricula(solicitacao.getMatricula());
		validaTurma(solicitacao.getTurma());

		return errors.size() == 0;
	}

	private void validaTurma(Turma turma) {
		if (turma == null)
			errors.add("É necessário informar a turma do aluno;\n");
	}

	private void validaMatricula(String matricula) {
		if (matricula == null || matricula.trim().length() == 0)
			errors.add("É necessário informar o código do aluno;\n");
	}

	public boolean naLista(String idFacebook) throws HibernateException,
			Exception {
		Solicitacao listaEspera = solicitacaoDAO.getSolicitacao(idFacebook);

		if (listaEspera != null) {
			Session session = Sessions.getCurrent();
			session.setAttribute("listaEspera", listaEspera);
			return true;
		}
		return false;
	}

	public boolean checaLista(Solicitacao solicitacao)
			throws HibernateException, Exception {
		if (solicitacao != null) {
			solicitacao = solicitacaoDAO.getSolicitacao(solicitacao
					.getIdFacebook());
			if (solicitacao != null) {
				return true;
			}
		}
		return false;
	}

	public boolean salvar(Solicitacao solicitacao) {
		return solicitacaoDAO.salvar(solicitacao);
	}

	public Solicitacao getSolicitacao(String facebookId) {
		return solicitacaoDAO.getSolicitacao(facebookId);
	}
	
	public List<Solicitacao> getTodos(){
		return solicitacaoDAO.getTodos();
	}

	public boolean exclui(Solicitacao solicitacao) {
		return solicitacaoDAO.exclui(solicitacao);
	}
}
