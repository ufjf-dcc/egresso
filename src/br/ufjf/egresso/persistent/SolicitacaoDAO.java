package br.ufjf.egresso.persistent;

import java.util.List;

import org.hibernate.Query;

import br.ufjf.egresso.model.Solicitacao;

public class SolicitacaoDAO extends GenericoDAO {

	public Solicitacao getSolicitacao(String facebookId) {
		try {
			Query query = getSession()
					.createQuery(
							"SELECT s FROM Solicitacao AS s LEFT JOIN FETCH s.turma WHERE s.idFacebook = :idFacebook");
			query.setParameter("idFacebook", facebookId);

			Solicitacao solicitacao = (Solicitacao) query.uniqueResult();

			getSession().close();

			return solicitacao;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Solicitacao> getTodas() {
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
