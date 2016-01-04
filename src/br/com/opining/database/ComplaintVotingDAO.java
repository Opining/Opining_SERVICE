package br.com.opining.database;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;


import br.com.opining.library.model.room.polarized.ComplaintVoting;
import br.com.opining.util.HibernateUtil;

public class ComplaintVotingDAO extends GenericDAO<ComplaintVoting>{

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintVoting> getAll() {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ComplaintVoting> complaintVoting = null;

		try {
			session.beginTransaction();
			Query query = session.getNamedQuery("ComplaintVoting.getAll");
			complaintVoting = (List<ComplaintVoting>) query.list();
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();

		} finally {
			session.clear();
			session.close();
		}

		return complaintVoting;
	}

	@Override
	public ComplaintVoting getById(Integer pk) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		ComplaintVoting complaintVoting = null;

		try {
			session.beginTransaction();
			complaintVoting = (ComplaintVoting) session.get(ComplaintVoting.class, pk);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();

		} finally {
			session.close();
		}

		return complaintVoting;
	}

}
