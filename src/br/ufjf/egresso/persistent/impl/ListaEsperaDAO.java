package br.ufjf.egresso.persistent.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import br.ufjf.egresso.model.ListaEspera;
import br.ufjf.egresso.persistent.GenericoDAO;
import br.ufjf.egresso.persistent.IListaEsperaDAO;

public class ListaEsperaDAO extends GenericoDAO implements IListaEsperaDAO{
	
	public ListaEspera retornaListaEspera(String idFacebook) throws HibernateException,Exception{
		try {
			Query query = getSession().createQuery("SELECT l FROM ListaEspera AS l WHERE l.idfacebook = :idFacebook");
			query.setParameter("idFacebook", idFacebook);

			ListaEspera listaEspera = (ListaEspera) query.uniqueResult();

			getSession().close();

			if(listaEspera!=null)
				return listaEspera;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<ListaEspera> getListasEspera() {
		try {
			Query query = getSession().createQuery("SELECT l FROM ListaEspera as l LEFT JOIN FETCH l.turma");
			
			List<ListaEspera> listaEspera = query.list();
			getSession().close();
			
			if(listaEspera!=null)
				return listaEspera;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
