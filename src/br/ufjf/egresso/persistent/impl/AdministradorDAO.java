package br.ufjf.egresso.persistent.impl;

import org.hibernate.Query;

import br.ufjf.egresso.model.Administrador;
import br.ufjf.egresso.persistent.GenericoDAO;
import br.ufjf.egresso.persistent.IAdministradorDAO;

public class AdministradorDAO extends GenericoDAO implements IAdministradorDAO {

	public Administrador entrar(String identificador, String senhaEncriptada) {
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
