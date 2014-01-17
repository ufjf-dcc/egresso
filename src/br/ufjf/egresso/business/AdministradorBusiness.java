package br.ufjf.egresso.business;

import br.ufjf.egresso.model.Administrador;
import br.ufjf.egresso.persistent.impl.AdministradorDAO;

public class AdministradorBusiness extends GenericBusiness {
	private AdministradorDAO adminisytradorDao;
	
	public AdministradorBusiness(){
		adminisytradorDao = new AdministradorDAO();
	}
	
	public Administrador entrar(String id, String senha){
		return adminisytradorDao.entrar(id, encripta(senha));
	}

}
