package br.ufjf.egresso.persistent.impl;

import java.util.List;

import org.hibernate.Query;

import br.ufjf.egresso.model.Solicitacao;
import br.ufjf.egresso.persistent.GenericoDAO;
import br.ufjf.egresso.persistent.ISolicitacaoDAO;

public class SolicitacaoDAO extends GenericoDAO implements ISolicitacaoDAO {

	public Solicitacao getPedido(String facebookId) {
		try {
			Query query = getSession()
					.createQuery(
							"SELECT s FROM Solicitacao AS s WHERE s.idFacebook = :idFacebook");
			query.setParameter("idFacebook", facebookId);

			Solicitacao solicitacao = (Solicitacao) query.uniqueResult();

			getSession().close();

			return solicitacao;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Solicitacao> getTodos() {
		try {
			Query query = getSession().createQuery(
					"SELECT s FROM Solicitacao AS s LEFT JOIN FETCH s.turma");

			@SuppressWarnings("unchecked")
			List<Solicitacao> solicitacoes = query.list();

			getSession().close();

			return solicitacoes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
