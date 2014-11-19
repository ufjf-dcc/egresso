package br.ufjf.egresso.business;

import java.util.List;

import br.ufjf.egresso.model.Curso;
import br.ufjf.egresso.persistent.CursoDAO;

public class CursoBusiness {
	private CursoDAO cursoDao;

	public CursoBusiness() {
		cursoDao = new CursoDAO();
	}

	public List<Integer> getTodosCod() {
		return cursoDao.getAllCodes();
	}
	public List<Curso> getTodos(){
		return cursoDao.getTodos();
	}

	public Curso getPorCod(int cod) {
		return cursoDao.getPorCod(cod);
	}
}
