package br.ufjf.egresso.business;

import java.util.List;

import org.hibernate.HibernateException;

import br.ufjf.egresso.model.Turma;
import br.ufjf.egresso.persistent.impl.TurmaDAO;

public class TurmaBusiness extends GenericBusiness{
	public boolean turmaCadastrada(String turma) throws HibernateException, Exception{
		TurmaDAO turmaDAO = new TurmaDAO();
		if ((turmaDAO.retornaTurma(turma) != null))
			return true;
		else return false;
	}
	
	public List<Turma> getTodas(){
		return new TurmaDAO().getTurmas();
	}
}