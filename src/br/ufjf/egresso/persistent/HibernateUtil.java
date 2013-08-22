package br.ufjf.egresso.persistent;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
	private static Transaction transaction;
	private static Session session;

	private static void start() {
		try {
			Configuration configuration = new Configuration();
			configuration.configure();
			serviceRegistry = new ServiceRegistryBuilder()
					.applySettings(configuration.getProperties())
					.buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Throwable e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static Session getInstance() throws Exception {
		if (sessionFactory == null) {
			start();
		}
		return sessionFactory.openSession();
	}

	@SuppressWarnings({ "finally", "rawtypes" })
	public static List<?> findAll(Class objClass, int begin, int end) {
		List<?> lista = null;
		Query query = null;
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			query = session.createQuery("From " + objClass.getName());
			if (begin >= 0 && end >= 0) {
				query.setFirstResult(begin);
				query.setMaxResults(end - begin);
			}
			lista = (List<?>) query.list();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
		} finally {
			session.close();
			return lista;
		}
	}

	@SuppressWarnings({ "finally", "rawtypes" })
	public static Object find(Class objClass, int id) {
		Object objGet = null;
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			objGet = session.get(objClass, id);
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
		} finally {
			session.close();
			return objGet;
		}
	}

	@SuppressWarnings("finally")
	public static boolean saveOrUpdate(Object obj) {
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			session.saveOrUpdate(obj);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
			return false;
		} finally {
			session.close();
			return true;
		}
	}

	@SuppressWarnings("finally")
	public static boolean save(Object obj) {
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			session.save(obj);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
			return false;
		} finally {
			session.close();
			return true;
		}
	}

	@SuppressWarnings("finally")
	public static boolean saveList(List<?> objects) {
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			for (Object obj : objects) {
				session.save(obj);
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
			return false;
		} finally {
			session.close();
			return true;
		}
	}

	@SuppressWarnings("finally")
	public static boolean update(Object obj) {
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			session.update(obj);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
			return false;
		} finally {
			session.close();
			return true;
		}
	}

	@SuppressWarnings("finally")
	public static boolean delete(Object obj) {
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			session.delete(obj);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
			return false;
		} finally {
			session.close();
			return true;
		}
	}

	@SuppressWarnings("finally")
	public static boolean deleteList(List<?> objects) {
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			for (Object obj : objects) {
				session.delete(obj);
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
			return false;
		} finally {
			session.close();
			return true;
		}
	}
}