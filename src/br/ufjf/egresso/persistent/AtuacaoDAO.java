package br.ufjf.egresso.persistent;

import java.util.List;

import org.hibernate.Query;

import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Atuacao;
/**
 * Classe que obtém as informações de {@link Atuacao} do c
 * banco de dados
 * @author Eduardo Rocha Soares
 *
 */
public class AtuacaoDAO extends GenericoDAO {
	/**
	 * Retorna todas as atuação do banco de dados.
	 * @return
	 * {@link List} de {@link Atuacao} 
	 */
	@SuppressWarnings("unchecked")
	public List<Atuacao> getTodas() {
		try {
			Query query = getSession().createQuery(
					"SELECT a FROM Atuacao AS a LEFT JOIN fetch a.tipoAtuacao ORDER BY a.id");
			List<Atuacao> atuacao = query.list();

			getSession().close();

			if (atuacao != null)
				return atuacao;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Retorna todas as {@link Atuacao} de um determinado {@link Aluno}
	 * @param aluno
	 * {@link Aluno} para o qual carregaremos as {@link Atuacao}
	 * @return
	 * 	{@link List} de {@link Atuacao}
	 */
	public List<Atuacao> getAtuacoes(Aluno aluno) {
		try {
			Query query = getSession().createQuery(
					"SELECT a FROM Atuacao AS a LEFT JOIN fetch a.tipoAtuacao WHERE a.aluno = :aluno ORDER BY a.id");
			query.setParameter("aluno", aluno);
			@SuppressWarnings("unchecked")
			List<Atuacao> atuacao = query.list();

			getSession().close();

			if (atuacao != null)
				return atuacao;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Exclui as {@link Atuacao} de um {@link Aluno]
	 * @param aluno
	 * 	{@link Aluno} do qual serão excluídas as {@link Atuacao}
	 * @return
	 * 	{@link true} se for bem sucedido {@link false} caso contŕario
	 */
	public boolean excluir(Aluno aluno) {
		try {
		Query query = getSession().createQuery("DELETE Atuacao WHERE aluno = :aluno");
		query.setParameter("aluno", aluno);
		query.executeUpdate();

		getSession().close();
		return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}

	
}
