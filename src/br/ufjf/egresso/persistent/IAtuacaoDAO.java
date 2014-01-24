package br.ufjf.egresso.persistent;

import java.util.List;

import br.ufjf.egresso.model.Atuacao;
import br.ufjf.egresso.model.Turma;

public interface IAtuacaoDAO {
	

	List<Atuacao> getTodas();

}
