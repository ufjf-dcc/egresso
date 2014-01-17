package br.ufjf.egresso.business;

import org.hibernate.HibernateException;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import br.ufjf.egresso.model.Pedido;
import br.ufjf.egresso.persistent.impl.PedidoDAO;

public class PedidoBusiness extends GenericBusiness {
	private PedidoDAO pedidoDAO;

	public PedidoBusiness() {
		pedidoDAO = new PedidoDAO();
	}

	public boolean naLista(String idFacebook) throws HibernateException,
			Exception {
		Pedido listaEspera = pedidoDAO.getPedido(idFacebook);

		if (listaEspera != null) {
			Session session = Sessions.getCurrent();
			session.setAttribute("listaEspera", listaEspera);
			return true;
		}
		return false;
	}

	public boolean checaLista(Pedido listaEspera)
			throws HibernateException, Exception {
		if (listaEspera != null) {
			listaEspera = pedidoDAO.getPedido(listaEspera
					.getIdFacebook());
			if (listaEspera != null) {
				return true;
			}
		}
		return false;
	}

	public boolean salvar(Pedido pedido) {
		return pedidoDAO.salvar(pedido);
	}

	public Object getPedido(String facebookId) {
		return pedidoDAO.getPedido(facebookId);
	}
}
