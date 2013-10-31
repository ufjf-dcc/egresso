package br.ufjf.egresso.business;

import org.hibernate.HibernateException;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.persistent.impl.AlunoDAO;


public class AlunoBusiness extends GenericBusiness {

	
	public boolean login(String codigoFB) throws HibernateException, Exception {
		AlunoDAO alunoDAO = new AlunoDAO();
		Aluno aluno = alunoDAO.retornaAluno(codigoFB);

		if (aluno != null) {
			Session session = Sessions.getCurrent();
			session.setAttribute("aluno", aluno);
			return true;
		}
		return false;
	}

	public boolean checaLogin(Aluno aluno) throws HibernateException, Exception {
		if (aluno != null) {
			AlunoDAO alunoDAO = new AlunoDAO();
			aluno = alunoDAO.retornaAluno(aluno.getTokenFacebook());
			if (aluno != null) {
				return true;
			}
		}
		return false;
	}
	
	public boolean alunoCadastrado(String codigoFB) throws HibernateException, Exception {
		AlunoDAO alunoDAO = new AlunoDAO();
		if (alunoDAO.retornaAluno(codigoFB)!=null){
			return true;
		}
		else return false;
	}
		
	
	public boolean cadastroAluno(){
		return true;
	}
	
}