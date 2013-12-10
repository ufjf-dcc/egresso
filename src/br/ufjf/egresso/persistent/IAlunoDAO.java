package br.ufjf.egresso.persistent;

import java.util.List;

import org.hibernate.HibernateException;

import br.ufjf.egresso.model.Aluno;

public interface IAlunoDAO {

	public Aluno retornaAluno(String tokenFacebook) throws HibernateException,Exception;

	public List<Aluno> getAlunos();
	
	public List<Aluno> getSomenteAlunos();
	
	public Aluno retornaAlunoM(String matricula) throws HibernateException,Exception;
	
	public Aluno retornaAlunoN(String nome) throws HibernateException,Exception;
	
	public List<Aluno> getCoordenadores();
	
	public List<Aluno> getAdministradores();
	
	public List<Aluno> getAlunoTurma(String turma);
	
	public Aluno retornaAluno(String nome, String tokenFacebook);
}
