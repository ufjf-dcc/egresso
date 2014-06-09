package br.ufjf.egresso.persistent;

import java.util.List;

import org.hibernate.Query;

import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Atuacao;

public class AtuacaoDAO extends GenericoDAO {
	
	@SuppressWarnings("unchecked")
	public List<Atuacao> getTodas() {
		try {
			Query query = getSession().createQuery(
					"SELECT a FROM Atuacao AS a LEFT JOIN fetch a.tipoAtuacao ORDER BY a.id");
			List<Atuacao> atuacao = query.list();

			getSession().close();

			if (atuacao != null)
				return atuacao;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Atuacao> getAtuacoes(Aluno aluno) {
		try {
			Query query = getSession().createQuery(
					"SELECT a FROM Atuacao AS a LEFT JOIN fetch a.tipoAtuacao WHERE a.aluno = :aluno ORDER BY a.id");
			query.setParameter("aluno", aluno);
			@SuppressWarnings("unchecked")
			List<Atuacao> atuacao = query.list();

			getSession().close();

			if (atuacao != null)
				return atuacao;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean excluir(Aluno aluno) {
		try {
		Query query = getSession().createQuery("DELETE Atuacao WHERE aluno = :aluno");
		query.setParameter("aluno", aluno);
		query.executeUpdate();

		getSession().close();
		return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}

	
}
