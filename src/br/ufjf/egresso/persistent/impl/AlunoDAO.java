package br.ufjf.egresso.persistent.impl;

import java.util.List;

import org.hibernate.Query;

import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Turma;
import br.ufjf.egresso.persistent.GenericoDAO;
import br.ufjf.egresso.persistent.IAlunoDAO;

public class AlunoDAO extends GenericoDAO implements IAlunoDAO {

	@Override
	public Aluno getAluno(String facebookId) {
		try {
			Query query = getSession()
					.createQuery(
							"SELECT a FROM Aluno AS a WHERE a.facebookId = :idFacebook");
			query.setParameter("idFacebook", facebookId);

			Aluno aluno = (Aluno) query.uniqueResult();

			getSession().close();

			return aluno;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Aluno> getAlunos() {
		try {
			Query query = getSession().createQuery(
					"SELECT a FROM Aluno as a ORDER BY a.nome");

			List<Aluno> aluno = query.list();
			getSession().close();

			if (aluno != null)
				return aluno;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Aluno> getAlunosTurma(Turma turma) {
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

	@Override
	public Aluno retornaAlunoM(String matricula) {
		try {
			Query query = getSession().createQuery(
					"SELECT a FROM Aluno as a WHERE a.matricula = :matricula");
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

	@Override
	public Aluno retornaAlunoN(String nome) {
		try {
			Query query = getSession().createQuery(
					"SELECT a FROM Aluno as a WHERE a.nome = :nome");
			query.setParameter("nome", nome);

			Aluno aluno = (Aluno) query.uniqueResult();
			getSession().close();

			if (aluno != null)
				return aluno;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
