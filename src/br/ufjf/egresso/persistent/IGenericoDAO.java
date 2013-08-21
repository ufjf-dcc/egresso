package br.ufjf.egresso.persistent;

import java.util.List;
import org.hibernate.HibernateException;

public interface IGenericoDAO {

	boolean salvar(Object objeto) throws HibernateException;

	boolean editar(Object objeto) throws HibernateException;

	boolean salvaOuEdita(Object objeto) throws HibernateException;

	Object procuraId(int id, Class classe) throws HibernateException;

	List<?> procuraTodos(Class classe, int inicio, int fim) throws HibernateException;

	boolean exclui(Object objeto) throws HibernateException;

	boolean excluiLista(List<?> objetos) throws HibernateException;

	boolean salvarLista(List<?> objetos) throws HibernateException;

}