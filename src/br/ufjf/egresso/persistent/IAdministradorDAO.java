package br.ufjf.egresso.persistent;

import br.ufjf.egresso.model.Administrador;

public interface IAdministradorDAO {
	
	public Administrador entrar(String id, String senhaEncriptada);

}
