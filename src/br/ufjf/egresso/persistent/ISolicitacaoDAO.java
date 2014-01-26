package br.ufjf.egresso.persistent;

import br.ufjf.egresso.model.Solicitacao;

public interface ISolicitacaoDAO {
	
	public Solicitacao getSolicitacao(String facebookId);
}
