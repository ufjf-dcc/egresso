package br.ufjf.egresso.persistent;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;


public class GenericoDAO {

	private Session session;

	
	public boolean salvar(Object objeto) throws HibernateException {
		boolean retorno = false;
		if (objeto != null) {
			retorno = HibernateUtil.save(objeto);
		} else {
			System.out.println("Objeto vazio.");
		}
		return retorno;
	}

	
	public boolean salvarLista(List<?> objetos) throws HibernateException {
		boolean retorno = false;
		if (objetos != null && !objetos.isEmpty()) {
			retorno = HibernateUtil.saveList(objetos);
		} else {
			System.out.println("Lista vazia.");
		}
		return retorno;
	}

	
	public boolean editar(Object objeto) throws HibernateException {
		boolean retorno = false;
		if (objeto != null) {
			retorno = HibernateUtil.update(objeto);
		} else {
			System.out.println("Objeto vazio.");
		}
		return retorno;
	}

	
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

	
	public boolean exclui(Object objeto) throws HibernateException {
		boolean retorno = false;
		if (objeto != null) {
			retorno = HibernateUtil.delete(objeto);
		} else {
			System.out.println("Objeto vazio.");
		}
		return retorno;
	}

	
	public boolean excluiLista(List<?> objetos) throws HibernateException {
		boolean retorno = false;
		if (objetos != null && !objetos.isEmpty()) {
			retorno = HibernateUtil.deleteList(objetos);
		} else {
			System.out.println("Lista vazia.");
		}
		return retorno;
	}

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