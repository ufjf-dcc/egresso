package br.ufjf.egresso.persistent;

import java.util.List;

import org.hibernate.Query;

import br.ufjf.egresso.model.Curso;

public class CursoDAO extends GenericoDAO {
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
