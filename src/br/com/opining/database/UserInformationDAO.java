package br.com.opining.database;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.opining.library.model.UserInformations;
import br.com.opining.util.HibernateUtil;

public class UserInformationDAO extends GenericDAO<UserInformations>{

	@SuppressWarnings("unchecked")
	@Override
	public List<UserInformations> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<UserInformations> userInfos = null;

		try {
			session.beginTransaction();
			Query query = session.getNamedQuery("UserInformations.getAll");
			userInfos = (List<UserInformations>) query.list();
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();

		} finally {
			session.clear();
			session.close();
		}

		return userInfos;
	}

	@Override
	public UserInformations getById(Integer pk) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		UserInformations userInfo = null;

		try {
			session.beginTransaction();
			userInfo = (UserInformations) session.get(UserInformations.class, pk);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();

		} finally {
			session.close();
		}

		return userInfo;
	}
}
