package br.ufjf.egresso.persistent;

import java.util.List;

import org.hibernate.HibernateException;

import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Turma;

public interface IAlunoDAO {

	public Aluno getAluno(String facebookId) throws HibernateException,Exception;

	public List<Aluno> getAlunos();
	
	public List<Aluno> getAlunosTurma(Turma turma);

	Aluno retornaAlunoM(String matricula);

	Aluno retornaAlunoN(String nome);
}
