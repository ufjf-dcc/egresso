package br.ufjf.egresso.business;

import java.security.NoSuchAlgorithmException;

import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.algorithm.AbstractChecksum;
import br.ufjf.egresso.model.Administrador;
import br.ufjf.egresso.persistent.AdministradorDAO;

public class AdministradorBusiness {
	private AdministradorDAO adminisytradorDao;
	
	public AdministradorBusiness(){
		adminisytradorDao = new AdministradorDAO();
	}
	
	public Administrador entrar(String identificador, String senha){
		return adminisytradorDao.entrar(identificador, encripta(senha));
	}
	
	public String encripta(String expressao) {
		try {
			AbstractChecksum checksum = null;
			checksum = JacksumAPI.getChecksumInstance("whirlpool-1");
			checksum.update(expressao.getBytes());
			return checksum.getFormattedValue();
		} catch (NoSuchAlgorithmException ns) {
			ns.printStackTrace();
			return null;
		}
	}

}
