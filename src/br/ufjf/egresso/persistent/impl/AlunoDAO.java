package br.ufjf.egresso.persistent.impl;

import java.util.List;

import org.hibernate.Query;

import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.persistent.GenericoDAO;
import br.ufjf.egresso.persistent.IAlunoDAO;

public class AlunoDAO extends GenericoDAO implements IAlunoDAO {
	
	@Override
	public Aluno retornaAluno(String tokenFacebook) {
		try {
			Query query = getSession().createQuery("select a from Aluno as a where a.tokenFacebook = :tokenFacebook");
			query.setParameter("tokenFacebook", tokenFacebook);
			
			Aluno aluno = (Aluno) query.uniqueResult();
	
			System.out.println(aluno.getNome());
			getSession().close();
			
			if(aluno!=null)
				return aluno;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Aluno> getAlunos() {
		try {
			Query query = getSession().createQuery("SELECT a FROM Aluno as a ORDER BY a.nome");
			
			List<Aluno> aluno = query.list();
			getSession().close();
			
			if(aluno!=null)
				return aluno;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Aluno> getSomenteAlunos() {
		try {
			Query query = getSession().createQuery("SELECT a FROM Aluno as aWHERE a.tipoPermissao = :tipoPermissao ORDER BY a.nome");
			query.setParameter("tipoPermissao", 0);
			
			List<Aluno> aluno = query.list();
			getSession().close();
			
			if(aluno!=null)
				return aluno;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Aluno retornaAlunoM(String matricula) {
		try {
			Query query = getSession().createQuery("SELECT a FROM Aluno as a WHERE a.matricula = :matricula");
			query.setParameter("matricula", matricula);
			
			Aluno aluno = (Aluno) query.uniqueResult();
			getSession().close();
			
			if (aluno!=null)
				return aluno;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Aluno retornaAlunoN(String nome) {
		try {
			Query query = getSession().createQuery("SELECT a FROM Aluno as a WHERE a.nome = :nome");
			query.setParameter("nome", nome);
			
			Aluno aluno = (Aluno) query.uniqueResult();
			getSession().close();
			
			if (aluno!=null)
				return aluno;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Aluno> getCoordenadores() {
		try {
			Query query = getSession().createQuery("SELECT a FROM Aluno as a WHERE a.tipoPermissao = :tipoPermissao ORDER BY a.nome");
			query.setParameter("tipoPermissao", 1);
			
			List<Aluno> coordenadores = query.list();
			getSession().close();
			
			if (coordenadores!=null)
				return coordenadores;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Aluno> getAdministradores() {
		try {
			Query query = getSession().createQuery("SELECT a FROM Aluno as a WHERE a.tipoPermissao = :tipoPermissao ORDER BY a.nome");
			query.setParameter("tipoPermissao", 2);
			
			List<Aluno> administradores = query.list();
			getSession().close();
			
			if (administradores!=null)
				return administradores;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Aluno> getAlunoTurma(String turma) {
		try {
			Query query = getSession().createQuery("SELECT a FROM Aluno as a WHERE a.turma = :turma ORDER BY a.nome");
			query.setParameter("turma", turma);
			
			List<Aluno> administradores = query.list();
			getSession().close();
			
			if (administradores!=null)
				return administradores;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
