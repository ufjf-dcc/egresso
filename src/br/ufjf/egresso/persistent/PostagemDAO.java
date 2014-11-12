package br.ufjf.egresso.persistent;

import java.util.List;

import org.hibernate.Query;

import br.ufjf.egresso.model.Postagem;
import br.ufjf.egresso.model.Turma;

public class PostagemDAO extends GenericoDAO {

	public Postagem getPostagem(int id) {
		try {
			Query query = getSession().createQuery(
					"SELECT p FROM Postagem AS p WHERE p.id = :id");
			query.setParameter("id", id);

			Postagem postagem = (Postagem) query.uniqueResult();

			getSession().close();

			return postagem;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Postagem> getPostagens(Turma turma) {
		try {
			Query query = getSession().createQuery(
					"SELECT p FROM Postagem AS p WHERE p.turma = :turma ORDER BY data_hora DESC ");
			query.setParameter("turma", turma);

			List<Postagem> postagens = query.list();
			getSession().close();

			return postagens;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
