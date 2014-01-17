package br.ufjf.egresso.persistent;

import br.ufjf.egresso.model.Pedido;

public interface IPedidoDAO {
	
	public Pedido getPedido(String facebookId);
}
