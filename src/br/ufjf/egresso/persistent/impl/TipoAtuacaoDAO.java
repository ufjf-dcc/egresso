package br.ufjf.egresso.persistent.impl;

import java.util.List;

import org.hibernate.Query;

import br.ufjf.egresso.model.TipoAtuacao;

import br.ufjf.egresso.persistent.GenericoDAO;
import br.ufjf.egresso.persistent.ITipoAtuacaoDAO;

public class TipoAtuacaoDAO extends GenericoDAO implements ITipoAtuacaoDAO {
	
	@SuppressWarnings("unchecked")
	public List<TipoAtuacao> getTodas() {
		try {
			Query query = getSession().createQuery(
					"SELECT t FROM TipoAtuacao AS t ORDER BY t.nome");
			List<TipoAtuacao> tipoAtuacao = query.list();

			getSession().close();

			if (tipoAtuacao != null)
				return tipoAtuacao;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
