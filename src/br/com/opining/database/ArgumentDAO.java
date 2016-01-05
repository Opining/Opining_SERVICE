package br.com.opining.database;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.opining.library.model.room.polarized.Argument;
import br.com.opining.util.HibernateUtil;

public class ArgumentDAO extends GenericDAO<Argument> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Argument> getAll() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Argument> argument = null;

		try {
			session.beginTransaction();
			Query query = session.getNamedQuery("Argument.getAll");
			argument = (List<Argument>) query.list();
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();

		} finally {
			session.clear();
			session.close();
		}

		return argument;
	}

	@Override
	public Argument getById(Integer pk) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Argument argument = null;

		try {
			session.beginTransaction();
			argument = (Argument) session.get(Argument.class, pk);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();

		} finally {
			session.close();
		}

		return argument;
	}

}
