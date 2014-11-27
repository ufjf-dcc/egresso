package br.ufjf.egresso.persistent;


import java.util.List;

import org.hibernate.Query;

import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Interesse;
/**
 * Classe responsável por acessar os dados do {@link Interesse}
 * @author Eduardo Rocha Soares
 *
 */
public class InteresseDAO  extends GenericoDAO{
	/**
	 * Retorna uma {@link List} com os interesses de um {@link Aluno}
	 * @param aluno
	 * {@link Aluno} para o qual queremos saber os {@link Interesse}s
	 * @return
	 * {@link List} de {@link Interesse} caso haja {@link Interesse}s
	 *, caso contrário retorna {@link null}
	 */
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
