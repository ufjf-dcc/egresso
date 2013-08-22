package br.ufjf.egresso.business;

import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.persistent.impl.AlunoDAO;

public class LoginBusiness {
	AlunoDAO alunoDAO;

	public Aluno login(String tokenFacebook) throws HibernateException, Exception {
		Aluno aluno;
		alunoDAO = new AlunoDAO();
		aluno = alunoDAO.retornaAluno(tokenFacebook);

		if (aluno != null) {
			HttpSession session = (HttpSession) (Executions.getCurrent())
					.getDesktop().getSession().getNativeSession();
			session.setAttribute("aluno", aluno);
		} else {
			aluno = new Aluno();
			Messagebox.show("Aceite o aplicativo antes!", "Error",
					Messagebox.OK, Messagebox.ERROR);
		}
		return aluno;
	}
}