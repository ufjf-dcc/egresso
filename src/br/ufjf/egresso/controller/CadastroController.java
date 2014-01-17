package br.ufjf.egresso.controller;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;

import br.ufjf.egresso.business.PedidoBusiness;
import br.ufjf.egresso.model.Pedido;
import br.ufjf.egresso.model.Turma;
import br.ufjf.egresso.persistent.impl.TurmaDAO;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.PictureSize;

public class CadastroController {

	private Facebook facebook = (Facebook) Sessions.getCurrent().getAttribute(
			"facebook");
	private String urlpic;
	private Pedido pedido = new Pedido();
	private List<Turma> turmas = new TurmaDAO().getTurmas();

	@Init
	public void init() throws FacebookException {
		urlpic = facebook.getPictureURL(facebook.getMe().getId(),
				PictureSize.valueOf("large")).toExternalForm();
		pedido.setNome(facebook.getMe().getName());
		pedido.setIdFacebook(facebook.getMe().getId());
	}

	@Command
	public void solicitaCadastro() {
		pedido.setUrlFoto(urlpic);
		if (new PedidoBusiness().salvar(pedido))
			Executions.sendRedirect("/pedido-em-espera.zul");
		else
			Messagebox
					.show("Não foi possível solicitar po cadastro. Por favor, tente novamente mais tarde.",
							"Erro", Messagebox.OK, Messagebox.ERROR);
	}

	public Facebook getFacebook() {
		return facebook;
	}

	public String getUrlpic() {
		return urlpic;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

}
