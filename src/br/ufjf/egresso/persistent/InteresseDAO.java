package br.ufjf.egresso.persistent;


import java.util.List;

import org.hibernate.Query;

import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Interesse;

public class InteresseDAO  extends GenericoDAO{
	
	public List<Interesse> getInteresses(Aluno aluno){
		Query query;
		try {
			query = getSession().createQuery(
					"SELECT i FROM interesses AS i WHERE i.aluno = :aluno");
			query.setParameter("aluno", aluno);
	
			@SuppressWarnings("unchecked")
			List<Interesse> interesses = query.list();
			getSession().close();
			return interesses;
		} catch (Exception e) {
			return null;
		}
	
		
	}

	
	
	

}
