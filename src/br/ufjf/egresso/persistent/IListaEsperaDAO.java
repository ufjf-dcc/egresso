package br.ufjf.egresso.persistent;

import org.hibernate.HibernateException;

import br.ufjf.egresso.model.ListaEspera;

public interface IListaEsperaDAO {
	
	public ListaEspera retornaListaEspera(String idFacebook) throws HibernateException, Exception;
}
