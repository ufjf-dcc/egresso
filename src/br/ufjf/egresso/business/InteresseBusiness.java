package br.ufjf.egresso.business;

import java.util.List;

import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Interesse;
import br.ufjf.egresso.persistent.InteresseDAO;

public class InteresseBusiness {
	private InteresseDAO interesseDao;

	public InteresseBusiness() {
		
		interesseDao = new InteresseDAO();
	}
	
	public List<Interesse> getInteresses(Aluno aluno){
		return interesseDao.getInteresses(aluno);
	}

	public  boolean salvar(Interesse novoInteresse) {
		return interesseDao.salvar(novoInteresse);
		
	}

}
