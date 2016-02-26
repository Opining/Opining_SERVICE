package br.com.opining.database;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.opining.util.HibernateUtil;

public abstract class GenericDAO<T> {
	
	public int insert(T entity) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();
			session.save(entity);
			session.getTransaction().commit();
		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return 0;
	}
	
	public void update(T entity) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();
			session.update(entity);
			session.getTransaction().commit();
		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
	}
	
	public void delete(T entity) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();
			session.delete(entity);
			session.getTransaction().commit();
		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
	}
	
	public abstract List<T> getAll();

	public abstract T getById(Integer pk);
	
}
