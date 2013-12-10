package br.ufjf.egresso.controller;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import br.ufjf.egresso.model.ListaEspera;
import br.ufjf.egresso.model.Turma;
import br.ufjf.egresso.persistent.impl.ListaEsperaDAO;
import br.ufjf.egresso.persistent.impl.TurmaDAO;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.PictureSize;

public class CadastroController {

	private Facebook facebook = (Facebook) Sessions.getCurrent().getAttribute("facebook");
	private String urlpic;
	private ListaEspera listaEspera = new ListaEspera();
	private TurmaDAO turmaDAO = new TurmaDAO();
	private List<Turma> turmas = turmaDAO.getTurmas();

	@Init
	public void init() throws FacebookException {
		urlpic = facebook.getPictureURL(facebook.getMe().getId(),PictureSize.valueOf("large")).toExternalForm();
		listaEspera.setNome(facebook.getMe().getName());
		listaEspera.setIdfacebook(facebook.getMe().getId());
	}

	@Command
	public void cadastra() {
		listaEspera.setUrlFoto(urlpic);
		ListaEsperaDAO listaEsperaDAO = new ListaEsperaDAO();
		listaEsperaDAO.salvar(listaEspera);
		Session session = Sessions.getCurrent();
		session.setAttribute("listaEspera", listaEspera);
		Executions.sendRedirect("/lista.zul");
	}

	public Facebook getFacebook() {
		return facebook;
	}

	public String getUrlpic() {
		return urlpic;
	}

	public ListaEspera getListaEspera() {
		return listaEspera;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

}
