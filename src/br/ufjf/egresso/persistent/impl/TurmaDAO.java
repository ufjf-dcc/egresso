package br.ufjf.egresso.persistent.impl;

import java.util.List;

import org.hibernate.Query;

import br.ufjf.egresso.model.Turma;
import br.ufjf.egresso.persistent.GenericoDAO;
import br.ufjf.egresso.persistent.ITurmaDAO;

public class TurmaDAO extends GenericoDAO implements ITurmaDAO {

	@Override
	public Turma retornaTurma(String stringTurma) {
		try {
			Query query = getSession()
					.createQuery(
							"SELECT turma FROM turma AS turma LEFT JOIN FETCH turma.aluno JOIN FETCH turma.listaespera WHERE turma.turma = :stringTurma");
			query.setParameter("stringTurma", stringTurma);

			Turma turma = (Turma) query.uniqueResult();

			getSession().close();

			if (turma != null)
				return turma;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Turma> getTurmas() {
		try {
			Query query = getSession()
					.createQuery(
							"SELECT turma FROM turma AS t LEFT JOIN FETCH t.aluno JOIN FETCH t.listaespera");
			List<Turma> turma = query.list();

			getSession().close();

			if (turma != null)
				return turma;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
