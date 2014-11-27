package br.ufjf.egresso.persistent;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import br.ufjf.egresso.model.Aluno;
import br.ufjf.egresso.model.Curso;
import br.ufjf.egresso.model.Turma;

 /**
  * 
  * @author Eduardo Rocha Soares
  * Classe responsável por acessar as informações do {@link Aluno}
  * no banco de dados.
  */
public class AlunoDAO extends GenericoDAO {
	/**
	 * Faz uma busca de {@link Aluno} no banco através do facebookId.
	 * @param facebookId
	 * 	parâmetro pelo qual a busca será filtrada.
	 * @return
	 * 		{@link Aluno} retornado da busca.s
	 * @throws HibernateException
	 * Exceção lançada caso a busca não seja efetuada com sucesso.s 
	 */
	public Aluno getAluno(String facebookId) throws HibernateException {
		try {
			Query query = getSession()
					.createQuery(
							"SELECT a FROM Aluno AS a LEFT JOIN FETCH a.turma WHERE a.facebookId = :facebookId");
			query.setParameter("facebookId", facebookId);

			Aluno aluno = (Aluno) query.uniqueResult();

			getSession().close();

			return aluno;
		} catch (Exception e) {
			throw new HibernateException("erro");
		}

	}
	/**
	 * Busca um {@link Aluno } pelo seu id
	 * @param id
	 * id do aluno o qual está se buscando
	 * @return
	 * 	Retorna o aluno encontrado, caso contrário retorna
	 * um objeto nulo.
	 */
	public Aluno getAluno(int id) {
		try {
			Query query = getSession()
					.createQuery(
							"SELECT a FROM Aluno AS a LEFT JOIN FETCH a.turma WHERE a.id = :id");
			query.setParameter("id", id);

			Aluno aluno = (Aluno) query.uniqueResult();

			getSession().close();

			return aluno;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Recupera todos os {@link Aluno}s cadastrados  do banco de dados
	 * @return
	 * 	Retorna uma {@link List}  com os {@link Aluno}s.
	 */
	@SuppressWarnings("unchecked")
	public List<Aluno> getTodos() {
		try {
			Query query = getSession()
					.createQuery(
							"SELECT a FROM Aluno AS a LEFT JOIN FETCH a.turma ORDER BY a.nome");

			List<Aluno> aluno = query.list();
			getSession().close();

			if (aluno != null)
				return aluno;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Recupera todos os {@link Aluno}s de um determinado {@link Curso}
	 * @param curso
	 * 	Curso que será parâmetro de busca
	 * @return
	 * 	{@link List} de {@link Aluno}s retornados da busca. 
	 */
	@SuppressWarnings("unchecked")
	public List<Aluno> getTodosCurso(Curso curso) {
		try {
			Query query = getSession()
					.createQuery(
							"SELECT a FROM Aluno AS a LEFT JOIN FETCH a.turma WHERE a.curso = :curso");
			query.setParameter("curso", curso);

			List<Aluno> aluno = query.list();
			getSession().close();

			if (aluno != null)
				return aluno;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Busca um {@link Aluno} do banco pela sua matrícula
	 * @param matricula
	 * 	Matrícula que será parâmetro para localizar o aluno no banco.
	 * @return
	 * 	Retorna um {@link Aluno} caso seja localizado um aluno
	 * ou {@link null} caso contrário.
	 */
	public Aluno getAlunoPorMatricula(String matricula) {
		try {
			Query query = getSession()
					.createQuery(
							"SELECT a FROM Aluno as a LEFT JOIN FETCH a.turma WHERE a.matricula = :matricula");
			query.setParameter("matricula", matricula);

			Aluno aluno = (Aluno) query.uniqueResult();
			getSession().close();

			if (aluno != null)
				return aluno;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Verifica se um {@link Aluno} já existe no banco mas com outra matrícula 
	 * @param matricula
	 * 	Matrícula atual
	 * @param matriculaAntiga
	 * 	Matrícula antiga no banco
	 * @return
	 * 	{@link true} caso seja confirmado, {@link false} caso contrário.
	 */
	public boolean jaExiste(String matricula, String matriculaAntiga) {
		try {
			Query query;
			if (matriculaAntiga != null) {
				query = getSession()
						.createQuery(
								"SELECT a FROM Aluno a WHERE a.matricula = :matricula AND a.matricula != :matriculaAntiga");
				query.setParameter("matriculaAntiga", matriculaAntiga);
			} else
				query = getSession().createQuery(
						"SELECT a FROM Aluno a WHERE a.matricula = :matricula");

			query.setParameter("matricula", matricula);

			boolean resultado = query.list().size() > 0 ? true : false;

			getSession().close();

			return resultado;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	/**
	 * Retorna no banco os {@link Aluno}s de uma determinada {@link Turma}
	 * @param turma
	 * {@link Turma} para a qual serão retornados os {@link Aluno}s
	 * 	
	 * @return
	 * 	{@link List} de {@link Aluno}s pertencentes à {@link Turma}
	 */
	public List<Aluno> getAlunos(Turma turma) {
		try {
			Query query = getSession()
					.createQuery(
							"SELECT a FROM Aluno AS a LEFT JOIN FETCH a.turma WHERE a.turma = :turma AND a.ativo = '1'");
			query.setParameter("turma", turma);

			@SuppressWarnings("unchecked")
			List<Aluno> aluno = query.list();

			getSession().close();

			return aluno;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}

	public List<Aluno> getAlunos() {
		try {
			Query query = getSession().createQuery("SELECT a FROM Aluno AS a");

			@SuppressWarnings("unchecked")
			List<Aluno> aluno = query.list();

			getSession().close();

			return aluno;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Retorna {@link Aluno}s do banco que pertençam à uma {@link Turma} 
	 * e {@link Curso}
	 * @param turma
	 * 	{@link Turma} para qual os {@link Aluno}s pertencentes a ela serão retornados
	 * @param curso
	 * {@link Curso} para qual os {@link Aluno}s pertencentes a ele serão retornados
	 * @return
	 * 	{@link List} de alunos resultantes da busca.
	 */
	public List<Aluno> getAlunosCurso(Turma turma, Curso curso) {
		try {
			Query query = getSession()
					.createQuery(
							"SELECT a FROM Aluno AS a  WHERE a.turma = :turma AND  a.curso = :curso");
			query.setParameter("turma", turma);
			query.setParameter("curso", curso);

			@SuppressWarnings("unchecked")
			List<Aluno> aluno = query.list();

			getSession().close();

			return aluno;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}

}
