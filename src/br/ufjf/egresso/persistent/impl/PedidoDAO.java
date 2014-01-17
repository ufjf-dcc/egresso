package br.ufjf.egresso.persistent.impl;

import org.hibernate.Query;

import br.ufjf.egresso.model.Pedido;
import br.ufjf.egresso.persistent.GenericoDAO;
import br.ufjf.egresso.persistent.IPedidoDAO;

public class PedidoDAO extends GenericoDAO implements IPedidoDAO{
	
	public Pedido getPedido(String facebookId) {
		try {
			Query query = getSession().createQuery("SELECT p FROM Pedido AS p WHERE p.idFacebook = :idFacebook");
			query.setParameter("idFacebook", facebookId);

			Pedido pedido = (Pedido) query.uniqueResult();

			getSession().close();

			if(pedido!=null)
				return pedido;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
