package br.ufjf.egresso.controller;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;

import br.ufjf.egresso.business.AdministradorBusiness;
import br.ufjf.egresso.model.Administrador;

public class LoginAdminController extends GenericController {
	
	@Command
	public void entrar(@BindingParam("id") String id, @BindingParam("senha") String senha){
		Administrador admin = new AdministradorBusiness().entrar(id, senha);
		if (admin != null){
			Sessions.getCurrent().setAttribute("admin", admin);
			Executions.sendRedirect("home.zul");
		} else {
			Messagebox
			.show("Identificador ou senha incorretos.",
					"Erro", Messagebox.OK, Messagebox.ERROR);
		}
	}

}
