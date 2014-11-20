package br.ufjf.egresso.controller;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;

import br.ufjf.egresso.business.AdministradorBusiness;
import br.ufjf.egresso.model.Administrador;
/**
 * Classe para controlar a página de login
 * @author Eduardo Rocha Soares
 *
 */
public class AdminLoginController {
	/**
	 * Recebe identificador e senha e faz uma busca no banco de dados
	 * para verificar se os dados estão corretos, caso estejam
	 * é redirecionado para página gerencia-alunos.zul
	 , caso contrário é exibida uma mensagem de erro
	 * @param identificador
	 * 	Identificador do {@link Administrador} para fazer login
	 * @param senha
	 *    Senha do {@link Administrador} para fazer login
	 */
	@Command
	public void entrar(@BindingParam("identificador") String identificador,
			@BindingParam("senha") String senha) {
		Administrador admin = new AdministradorBusiness().autenticar(identificador,
				senha);
		if (admin != null) {
			Sessions.getCurrent().setAttribute("admin", admin);
			Executions.sendRedirect("/admin/gerencia-alunos.zul");
		} else {
			Messagebox.show("Identificador ou senha incorretos.", "Erro",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

}
