package br.ufjf.egresso.business;

import java.security.NoSuchAlgorithmException;

import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.algorithm.AbstractChecksum;
import br.ufjf.egresso.model.Administrador;
import br.ufjf.egresso.persistent.AdministradorDAO;

/**Classe para intermediar o acesso às informações da classe {@link Administrador}.
 * 
 * @author Jorge Augusto da Silva Moreira
 *
 */
public class AdministradorBusiness {
	private AdministradorDAO adminisytradorDao;
	
	public AdministradorBusiness(){
		adminisytradorDao = new AdministradorDAO();
	}
	
	/**Verifica se existe um {@link Administrador} com os dados informados.
	 * 
	 * @param identificador
	 * @param senha
	 * @return O respectivo {@link Administrador} ou {@link null} caso ele não exista.
	 */
	public Administrador autenticar(String identificador, String senha){
		return adminisytradorDao.autenticar(identificador, encripta(senha));
	}
	
	/**Encripta uma {@link String} utilizando whirlpool-1.
	 * 
	 * @param expressao A {@link String} a ser encriptada.
	 * @return Uma {@link String} encriptada a partir da expressão.
	 */
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
