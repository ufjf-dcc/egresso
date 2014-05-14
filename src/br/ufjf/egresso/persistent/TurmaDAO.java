package br.ufjf.egresso.persistent;

import java.util.List;

import org.hibernate.Query;

import br.ufjf.egresso.model.Turma;

public class TurmaDAO extends GenericoDAO {

	
	@SuppressWarnings("unchecked")
	public List<Turma> getTodas() {
		try {
			Query query = getSession().createQuery(
					"SELECT t FROM Turma AS t ORDER BY t.ano DESC");
			List<Turma> turmas = query.list();

			getSession().close();

			return turmas;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Turma getTurma(int id) {
		try {
			Query query = getSession()
					.createQuery(
							"SELECT t FROM Turma AS t WHERE t.id = :id");
			query.setParameter("id", id);
			Turma turma = (Turma) query.uniqueResult();

			getSession().close();

			return turma;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Turma getTurma(int ano, int semestre) {
		try {
			Query query = getSession()
					.createQuery(
							"SELECT t FROM Turma AS t WHERE t.ano = :ano AND t.semestre = :semestre");
			query.setParameter("ano", ano);
			query.setParameter("semestre", semestre);
			Turma turma = (Turma) query.uniqueResult();

			getSession().close();

			return turma;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Turma> getTurmas(int ano){
		try {
			Query query = getSession()
					.createQuery(
							"SELECT t FROM Turma AS t WHERE t.ano = :ano");
			query.setParameter("ano", ano);
			@SuppressWarnings("unchecked")
			List<Turma> turmas = query.list();

			getSession().close();

			return turmas;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
