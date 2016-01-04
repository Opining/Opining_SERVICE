package br.com.opining.database;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.opining.library.model.room.polarized.Vote;
import br.com.opining.util.HibernateUtil;

public class VoteDAO extends GenericDAO<Vote> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Vote> getAll() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Vote> vote = null;

		try {
			session.beginTransaction();
			Query query = session.getNamedQuery("Vote.getAll");
			vote = (List<Vote>) query.list();
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();

		} finally {
			session.clear();
			session.close();
		}

		return vote;
	}

	@Override
	public Vote getById(Integer pk) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Vote vote = null;

		try {
			session.beginTransaction();
			vote = (Vote) session.get(Vote.class, pk);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();

		} finally {
			session.close();
		}

		return vote;
	}

}
