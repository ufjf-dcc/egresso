package br.ufjf.egresso.persistent;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.ObjectDeletedException;
import org.hibernate.Session;

/**
 * Classe genérica responsável por manipular dados no banco de dados
 * @author esoares
 *
 */
public class GenericoDAO {

	private Session session;

	/**
	 * Salva um {@link Object} no banco de dados.
	 * @param objeto
	 * 	{@link Object} a ser salvo no banco de dados
	 * @return
	 * {@link true} caso seja salvo com sucesso
	 * {@link false} caso contrário
	 * @throws HibernateException
	 * Exceção lançacada caso não seja possível acessar 
	 * o banco de dados
	 */
	public boolean salvar(Object objeto) throws HibernateException {
		boolean retorno = false;
		if (objeto != null) {
			retorno = HibernateUtil.save(objeto);
		} else {
			System.out.println("Objeto vazio.");
		}
		return retorno;
	}

	/**
	 * Salva uma {@link List} de {@link Object}s no banco de dados
	 * @param objetos
	 * 	{@link List} de {@link Object} de serão salvos 
	 * no banco de dados
	 * @return
	 * 	{@link true} caso seja feita com sucesso,
	 * {@link false} caso não haja sucesso
	 * @throws HibernateException
	 * 	Caso não seja possível acessar o banco é lançada uma exceção.
	 */
	public boolean salvarLista(List<?> objetos) throws HibernateException {
		boolean retorno = false;
		if (objetos != null && !objetos.isEmpty()) {
			retorno = HibernateUtil.saveList(objetos);
		} else {
			System.out.println("Lista vazia.");
		}
		return retorno;
	}

	/**
	 * Edita um {@link Objeto} do banco de dados
	 * @param objeto
	 * {@link Objeto} a ser editados
	 * @return
	 * 	{@link true} caso seja feito com sucesso
	 * {@link false} caso contrário
	 * @throws HibernateException
	 * Lança exceção caso o banco não possa ser acessado.
	 */
	public boolean editar(Object objeto) throws HibernateException {
		boolean retorno = false;
		if (objeto != null) {
			retorno = HibernateUtil.update(objeto);
		} else {
			System.out.println("Objeto vazio.");
		}
		return retorno;
	}

	/**
	 * Salva ou edita um {@link Object} no banco de dados,
	 * se ele já existir no banco : edita ? salva.
	 * @param objeto
	 * {@link Object} que será salvo ou editado.
	 * @return
	 * 	{@link true} caso seja feito com sucesso, {@link false} caso contrário
	 * @throws HibernateException
	 * Execeção lançada pelo Hibernate caso algum erro ocorra durante o processo.
	 */
	public boolean salvaOuEdita(Object objeto) throws HibernateException {
		boolean retorno = false;
		if (objeto != null) {
			retorno = HibernateUtil.saveOrUpdate(objeto);
		} else {
			System.out.println("Objeto vazio.");
		}
		return retorno;
	}

	@SuppressWarnings("rawtypes")
	
	public Object procuraId(int id, Class classe) throws HibernateException {
		Object objeto = null;
		if (id >= 0 && classe != null) {
			objeto = HibernateUtil.find(classe, id);
		} else {
			System.out.println("ID ou Classe nulo(a).");
		}
		return objeto;
	}
	/**
	 * Faz uma busca de todos os {@link Objeto} de uma classe em um determinado 
	 * intervalo de id no BD.
	 * @param classe
	 * Classe em que a busca será filtrada
	 * @param inicio
	 * 	Limite inferior de ids procurados.
	 * @param fim
	 * 	Limite superior.
	 * @return
	 * 	{@link List} de {@link Object}s de uma classe.
	 * @throws HibernateException
	 * 	Execeção lançada pelo Hibernate caso algum erro ocorra durante o processo.

	 */
	@SuppressWarnings("rawtypes")
	
	public List<?> procuraTodos(Class classe, int inicio, int fim)
			throws HibernateException {
		List<?> objetos = null;
		if (classe != null) {
			objetos = HibernateUtil.findAll(classe, inicio, fim);
		} else {
			System.out.println("ID ou Classe nulo(a).");
		}
		return objetos;
	}

	/**
	 * Exclui o registro de um {@link Object} do banco de dados.
	 * @param objeto
	 * {@link Object} que será excluído
	 * @return
	 * {@link true} caso tenha sucesso na exclusão,
	 * {@link false} caso contrário.
	 * @throws HibernateException
	 * Execeção lançada pelo Hibernate caso algum erro ocorra durante o processo.
	 */
	public boolean exclui(Object objeto) throws HibernateException {
		boolean retorno = false;
		if (objeto != null) {
			retorno = HibernateUtil.delete(objeto);
		} else {
			System.out.println("Objeto vazio.");
		}
		return retorno;
	}

	/**
	 * Exclui uma {@link List} de {@link Object}s do banco de dados
	 * @param objetos
	 * {@link List} de {@link Object}s a serem excluídos.
	 * @return
	 * {@link true} caso tenha sucesso na exclusão,
	 * {@link false} caso contrário.
	 * @throws HibernateException
	 * Execeção lançada pelo Hibernate caso algum erro ocorra durante o processo.
	 */
	public boolean excluiLista(List<?> objetos) throws HibernateException {
		boolean retorno = false;
		if (objetos != null && !objetos.isEmpty()) {
			retorno = HibernateUtil.deleteList(objetos);
		} else {
			System.out.println("Lista vazia.");
		}
		return retorno;
	}
	/**
	 * Retorna a sessão do banco, caso não esteja aberta 
	 * ou seja nula, cria uma nova .
	 * @return
	 * {@link Session}  aberta ou uma nova criada.
	 * @throws Exception
	 * Exceção lançada pelo Hibernate caso haja algum erro.
	 */
	public Session getSession() throws Exception {
		if(session == null){
			session = HibernateUtil.getInstance();
		}
		else{
			if (!session.isOpen()){
				session = HibernateUtil.getInstance();
			}
		}
		return session;
	}

}