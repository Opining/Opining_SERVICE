package br.com.opining.database;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.opining.library.model.AcessKey;
import br.com.opining.util.HibernateUtil;

public class AcessKeyDAO extends GenericDAO<AcessKey>{

	@Override
	@SuppressWarnings("unchecked")
	public List<AcessKey> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<AcessKey> acessKeys = null;

		try {
			session.beginTransaction();
			Query query = session.getNamedQuery("AcessKey.getAll");
			acessKeys = (List<AcessKey>) query.list();
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();

		} finally {
			session.clear();
			session.close();
		}

		return acessKeys;
	}

	@Override
	public AcessKey getById(Integer pk) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		AcessKey acessKey = null;

		try {
			session.beginTransaction();
			acessKey = (AcessKey) session.get(AcessKey.class, pk);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();

		} finally {
			session.close();
		}

		return acessKey;
	}

}
