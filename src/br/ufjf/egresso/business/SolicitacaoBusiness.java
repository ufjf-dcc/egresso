package br.ufjf.egresso.business;

import java.util.List;

import org.hibernate.HibernateException;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import br.ufjf.egresso.model.Solicitacao;
import br.ufjf.egresso.persistent.impl.SolicitacaoDAO;

public class SolicitacaoBusiness {
	private SolicitacaoDAO solicitacaoDAO;

	public SolicitacaoBusiness() {
		solicitacaoDAO = new SolicitacaoDAO();
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
