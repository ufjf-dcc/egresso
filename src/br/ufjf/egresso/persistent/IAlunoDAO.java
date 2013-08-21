package br.ufjf.egresso.persistent;

import org.hibernate.HibernateException;
import br.ufjf.egresso.model.Aluno;

public interface IAlunoDAO {

	public Aluno retornaAluno(String tokenFacebook) throws HibernateException,Exception;

}
