package br.ufjf.egresso.persistent;

import java.util.List;

import org.hibernate.Query;

import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Turma;

public class AlunoDAO extends GenericoDAO {

	
	public Aluno getAluno(String facebookId) {
		try {
			Query query = getSession()
					.createQuery(
							"SELECT a FROM Aluno AS a LEFT JOIN FETCH a.turma WHERE a.facebookId = :facebookId");
			query.setParameter("facebookId", facebookId);

			Aluno aluno = (Aluno) query.uniqueResult();

			getSession().close();

			return aluno;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}
	
	
	public Aluno getAluno(int id) {
		try {
			Query query = getSession()
					.createQuery(
							"SELECT a FROM Aluno AS a LEFT JOIN FETCH a.turma WHERE a.id = :id");
			query.setParameter("id", id);

			Aluno aluno = (Aluno) query.uniqueResult();

			getSession().close();

			return aluno;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}

	
	@SuppressWarnings("unchecked")
	public List<Aluno> getTodos() {
		try {
			Query query = getSession().createQuery(
					"SELECT a FROM Aluno AS a LEFT JOIN FETCH a.turma ORDER BY a.nome");

			List<Aluno> aluno = query.list();
			getSession().close();

			if (aluno != null)
				return aluno;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	@SuppressWarnings("unchecked")
	public List<Aluno> getAlunos(Turma turma) {
		try {
			Query query = getSession()
					.createQuery(
							"SELECT a FROM Aluno as a WHERE a.turma = :turma ORDER BY a.nome");
			query.setParameter("turma", turma);

			List<Aluno> administradores = query.list();
			getSession().close();

			if (administradores != null)
				return administradores;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public Aluno buscaPorMatricula(String matricula) {
		try {
			Query query = getSession().createQuery(
					"SELECT a FROM Aluno as a LEFT JOIN FETCH a.turma WHERE a.matricula = :matricula");
			query.setParameter("matricula", matricula);

			Aluno aluno = (Aluno) query.uniqueResult();
			getSession().close();

			if (aluno != null)
				return aluno;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean jaExiste(String matricula, String matriculaAntiga) {
		try {
			Query query;
			if (matriculaAntiga != null) {
				query = getSession()
						.createQuery(
								"SELECT a FROM Aluno a WHERE a.matricula = :matricula AND a.matricula != :matriculaAntiga");
				query.setParameter("matriculaAntiga", matriculaAntiga);
			} else
				query = getSession()
						.createQuery(
								"SELECT a FROM Aluno a WHERE a.matricula = :matricula");

			query.setParameter("matricula", matricula);

			boolean resultado = query.list().size() > 0 ? true : false;

			getSession().close();

			return resultado;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public List<Aluno> getAlunosCadastrados(Turma turma) {
		try {
			Query query = getSession()
					.createQuery(
							"SELECT a FROM Aluno AS a LEFT JOIN FETCH a.turma WHERE a.turma = :turma AND a.facebookId IS NOT NULL");
			query.setParameter("turma", turma);

			@SuppressWarnings("unchecked")
			List<Aluno> aluno = query.list();

			getSession().close();

			return aluno;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}

}
