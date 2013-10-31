package br.ufjf.egresso.business;

import org.hibernate.HibernateException;

import br.ufjf.egresso.persistent.impl.TurmaDAO;

public class TurmaBusiness extends GenericBusiness{
	public boolean turmaCadastrada(String turma) throws HibernateException, Exception{
		TurmaDAO turmaDAO = new TurmaDAO();
		if ((turmaDAO.retornaTurma(turma) != null))
			return true;
		else return false;
	}
}