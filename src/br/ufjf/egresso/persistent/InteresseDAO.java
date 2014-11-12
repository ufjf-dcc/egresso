package br.ufjf.egresso.persistent;


import java.util.List;

import org.hibernate.Query;

import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Interesse;

public class InteresseDAO  extends GenericoDAO{
	@SuppressWarnings("unchecked")
	public List<Interesse> getInteresses(Aluno aluno){
		try {
			Query query = getSession().createQuery(
					"SELECT i FROM Interesse AS i WHERE i.aluno = :aluno");
			query.setParameter("aluno", aluno);
			List<Interesse> interesses = query.list();
			getSession().close();
			return interesses;
			
		} catch (Exception e) {
			System.err.println("erro na query");
		}
		return null;
		
	}

	
	
	

}
