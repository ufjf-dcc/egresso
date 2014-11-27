package br.ufjf.egresso.persistent;

import java.util.List;

import org.hibernate.Query;

import br.ufjf.egresso.model.Curso;
/**
 * Classe responśvel por acessar os dados de {@link Curso}
 * @author Eduardo Rocha Soares
 *
 */

public class CursoDAO extends GenericoDAO {
	/**
	 * Retorna todos os códigos de todos os {@link Curso}s existentes 
	 * no banco de dados
	 * @return
	 * 	{@link List} de {@link Integer} ou {@link null}
	 * se não encontrar nenhum registro.
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getAllCodes() {
		try {
			Query query = getSession()
					.createQuery("SELECT codCurso FROM Curso");
			List<Integer> codCurso = query.list();
			getSession().close();
			return codCurso;

		} catch (Exception e) {
			System.err.println("erro na query");
		}
		return null;

	}
	/**
	 * Retorna os todos os {@link Curso}s existentes
	 *  no banco de dados.
	 * @return
	 * {@link List} de {@link Curso}s
	 */
	public List<Curso> getTodos() {
		try {
			Query query = getSession().createQuery("SELECT c FROM Curso as c ");
			@SuppressWarnings("unchecked")
			List<Curso> cursos = query.list();
			getSession().close();
			return cursos;

		} catch (Exception e) {
			System.err.println("erro na query");
		}
		return null;
	}
	/**
	 * Retorna um {@link Curso} pesquisado por seu código
	 * @param cod
	 * código do {@link Curso}
	 * @return
	 * 	{@link Curso} se a busca encontrar um registro ou
	 * {@link null} caso não encontre.
	 */
	public Curso getPorCod(int cod) {
		try {
			Query query = getSession().createQuery("SELECT c FROM Curso as c WHERE c.codCurso = :cod ");
			query.setParameter("cod", cod);
			Curso curso = (Curso) query.uniqueResult();
			getSession().close();
			return curso;

		} catch (Exception e) {
			System.err.println("erro na query");
		}
		return null;

	}
}
