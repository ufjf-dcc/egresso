package br.ufjf.egresso.persistent.impl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.persistent.GenericoDAO;
import br.ufjf.egresso.persistent.IAlunoDAO;

public class AlunoDAO extends GenericoDAO implements IAlunoDAO {

	@SuppressWarnings("finally")
	@Override
	public Aluno retornaAluno(String tokenFacebook) throws HibernateException, Exception {
		Aluno aluno = null;
		try {
			Criteria criteria = getSession().createCriteria(Aluno.class,"aluno")
					.add(Restrictions.eq("aluno.tokenFacebook",tokenFacebook))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			aluno = (Aluno) criteria.uniqueResult();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return aluno;
		}
	}
}