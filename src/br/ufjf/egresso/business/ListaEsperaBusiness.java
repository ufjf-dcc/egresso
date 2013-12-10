package br.ufjf.egresso.business;

import org.hibernate.HibernateException;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import br.ufjf.egresso.model.ListaEspera;
import br.ufjf.egresso.persistent.impl.ListaEsperaDAO;

public class ListaEsperaBusiness extends GenericBusiness {

	public boolean naLista (String idFacebook) throws HibernateException, Exception{
		ListaEsperaDAO listaEsperaDAO = new ListaEsperaDAO ();
		ListaEspera listaEspera = listaEsperaDAO.retornaListaEspera(idFacebook);
		
		if(listaEspera !=null){
			Session session = Sessions.getCurrent();
			session.setAttribute("listaEspera", listaEspera);
			return true;
		}
		return false;
	}
	
	public boolean checaLista(ListaEspera listaEspera) throws HibernateException, Exception {
		if (listaEspera != null) {
			ListaEsperaDAO listaEsperaDAO = new ListaEsperaDAO ();
			listaEspera = listaEsperaDAO.retornaListaEspera(listaEspera.getIdfacebook());
			if (listaEspera != null) {
				return true;
			}
		}
		return false;
	}
}
