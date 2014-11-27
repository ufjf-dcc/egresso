package br.ufjf.egresso.persistent;

import org.hibernate.Query;

import br.ufjf.egresso.model.Administrador;
/**
 * 
 * @author Eduardo Rocha Soares, Jorge Augusto Moreira da Silva
 *	Classe responsável por acessar as informações do {@link Administrator} 
 *	no banco de dados.
 */
public class AdministradorDAO extends GenericoDAO {
	/**
	 * Faz a autenticação das credenciais do {@link Administrator} no sistema
	 * @param identificador
	 * 	Login do {@link Administrator}
	 * @param senhaEncriptada
	 * 	Senha do {@link Administrator} codificada em md5
	 * @return
	 */
	public Administrador autenticar(String identificador, String senhaEncriptada) {
		try {
			Query query = getSession()
					.createQuery(
							"SELECT a FROM Administrador AS a WHERE a.identificador = :identificador AND a.senha = :senha");
			query.setParameter("identificador", identificador);
			query.setParameter("senha", senhaEncriptada);

			Administrador admin = (Administrador) query.uniqueResult();

			getSession().close();

			return admin;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}

}
