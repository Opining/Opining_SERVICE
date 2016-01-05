package br.com.opining.database;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;



import br.com.opining.library.model.room.polarized.PolarizedRoom;
import br.com.opining.util.HibernateUtil;

public class PolarizedRoomDAO extends GenericDAO<PolarizedRoom> {

	@SuppressWarnings("unchecked")
	@Override
	public List<PolarizedRoom> getAll() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		List<PolarizedRoom> polarizedRoom = null;

		try {
			session.beginTransaction();
			Query query = session.getNamedQuery("PolarizedRoom.getAll");
			polarizedRoom = (List<PolarizedRoom>) query.list();
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();

		} finally {
			session.clear();
			session.close();
		}

		return polarizedRoom;
	}

	@Override
	public PolarizedRoom getById(Integer pk) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		PolarizedRoom polarizedRoom = null;

		try {
			session.beginTransaction();
			polarizedRoom = (PolarizedRoom) session.get(PolarizedRoom.class, pk);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();

		} finally {
			session.close();
		}

		return polarizedRoom;
	}

}
