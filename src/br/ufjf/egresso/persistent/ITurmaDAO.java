package br.ufjf.egresso.persistent;

import java.util.List;

import br.ufjf.egresso.model.Turma;

public interface ITurmaDAO {

	public Turma getTurma(String turma);

	public List<Turma> getTodas();
	
}
