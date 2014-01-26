package br.ufjf.egresso.persistent.impl;

import java.util.List;

import org.hibernate.Query;

import br.ufjf.egresso.model.Atuacao;
import br.ufjf.egresso.model.Turma;
import br.ufjf.egresso.persistent.GenericoDAO;
import br.ufjf.egresso.persistent.IAtuacaoDAO;

public class AtuacaoDAO extends GenericoDAO implements IAtuacaoDAO {
	@Override
	@SuppressWarnings("unchecked")
	public List<Atuacao> getTodas() {
		try {
			Query query = getSession().createQuery(
					"SELECT a FROM Atuacao AS a ORDER BY a.id");
			List<Atuacao> atuacao = query.list();

			getSession().close();

			if (atuacao != null)
				return atuacao;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
