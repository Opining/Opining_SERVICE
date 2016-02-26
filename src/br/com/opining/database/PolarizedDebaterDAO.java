package br.com.opining.database;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.opining.library.model.room.participant.PolarizedDebater;
import br.com.opining.util.HibernateUtil;

public class PolarizedDebaterDAO extends GenericDAO<PolarizedDebater> {

	@SuppressWarnings("unchecked")
	@Override
	public List<PolarizedDebater> getAll() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		List<PolarizedDebater> polarizedDebater = null;

		try {
			session.beginTransaction();
			Query query = session.getNamedQuery("PolarizedDebater.getAll");
			polarizedDebater = (List<PolarizedDebater>) query.list();
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();

		} finally {
			session.clear();
			session.close();
		}

		return polarizedDebater;
	}

	@Override
	public PolarizedDebater getById(Integer pk) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		PolarizedDebater polarizedDebater = null;

		try {
			session.beginTransaction();
			polarizedDebater = (PolarizedDebater) session.get(PolarizedDebater.class, pk);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();

		} finally {
			session.close();
		}

		return polarizedDebater;
	}

}
