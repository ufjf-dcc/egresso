package br.ufjf.egresso.business;

import java.util.ArrayList;
import java.util.List;



import br.ufjf.egresso.model.Turma;
import br.ufjf.egresso.persistent.impl.TurmaDAO;

public class TurmaBusiness {
	
	private List<String> errors;
	private TurmaDAO turmaDao;
	
	public TurmaBusiness() {
		this.errors = new ArrayList<String>();
		turmaDao = new TurmaDAO();
	}
	
	public List<String> getErrors() {
		return errors;
	}
	
	public List<Turma> getTodas(){
		return new TurmaDAO().getTodas();
	}
	public boolean validar(Turma turma) {
		errors.clear();

		validaSemestre(turma.getSemestre());
		return errors.size() == 0;
	}
	public boolean editar(Turma turma) {
		return turmaDao.editar(turma);
	}
	public boolean exclui(Turma turma) {
		return turmaDao.exclui(turma);
	}
	public boolean salvar(Turma novaTurma) {
		return turmaDao.salvar(novaTurma);
	}


	private void validaSemestre(String semestre) {
		if (semestre == null || semestre.trim().length() == 0)
			errors.add("É necessário informar o semestre da turma;\n");
	}

}
