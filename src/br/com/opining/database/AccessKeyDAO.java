package br.com.opining.database;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.opining.library.model.AccessKey;
import br.com.opining.util.HibernateUtil;

public class AccessKeyDAO extends GenericDAO<AccessKey>{

	@Override
	@SuppressWarnings("unchecked")
	public List<AccessKey> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<AccessKey> accessKeys = null;

		try {
			session.beginTransaction();
			Query query = session.getNamedQuery("AcessKey.getAll");
			accessKeys = (List<AccessKey>) query.list();
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();

		} finally {
			session.clear();
			session.close();
		}

		return accessKeys;
	}

	@Override
	public AccessKey getById(Integer pk) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		AccessKey accessKey = null;

		try {
			session.beginTransaction();
			accessKey = (AccessKey) session.get(AccessKey.class, pk);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();

		} finally {
			session.close();
		}

		return accessKey;
	}
	
	public AccessKey getByLoginUser(String login){
		 Session session = HibernateUtil.getSessionFactory().openSession();
		 session.beginTransaction();
		
		 Query query = session.createQuery("from AcessKey where user.login = :login");
		 query.setParameter("login", login);
		 query.setMaxResults(1);
		 
		 AccessKey accessKey = (AccessKey) query.uniqueResult();
		
		 session.getTransaction().commit();
		 session.close();
		
		 return accessKey;
	}
	
}
