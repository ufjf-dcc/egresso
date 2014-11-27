package br.ufjf.egresso.persistent;

import java.util.List;

import org.hibernate.Query;

import br.ufjf.egresso.model.TipoAtuacao;
/**
 * Classe que recupera as informações do {@link TipoAtuacao}
 * @author Eduardo Rocha Soares
 *
 */
public class TipoAtuacaoDAO extends GenericoDAO {
	/**
	 *Retorna todos os {@link TipoAtuacao}
	 * @return
	 * {@link List} de {@link TipoAtuacao}
	 */
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
