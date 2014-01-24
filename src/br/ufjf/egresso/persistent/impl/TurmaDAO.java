package br.ufjf.egresso.persistent.impl;

import java.util.List;

import org.hibernate.Query;

import br.ufjf.egresso.model.Turma;
import br.ufjf.egresso.persistent.GenericoDAO;
import br.ufjf.egresso.persistent.ITurmaDAO;

public class TurmaDAO extends GenericoDAO implements ITurmaDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<Turma> getTodas() {
		try {
			Query query = getSession().createQuery(
					"SELECT t FROM Turma AS t ORDER BY t.semestre");
			List<Turma> turmas = query.list();

			getSession().close();

			if (turmas != null)
				return turmas;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
