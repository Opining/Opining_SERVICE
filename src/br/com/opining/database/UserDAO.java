package br.com.opining.database;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.opining.library.model.User;
import br.com.opining.util.HibernateUtil;

public class UserDAO extends GenericDAO<User>{

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<User> users = null;

		try {
			session.beginTransaction();
			Query query = session.getNamedQuery("User.getAll");
			users = (List<User>) query.list();
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();

		} finally {
			session.clear();
			session.close();
		}

		return users;
	}

	@Override
	public User getById(Integer pk) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		User user = null;

		try {
			session.beginTransaction();
			user = (User) session.get(User.class, pk);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();

		} finally {
			session.close();
		}

		return user;
	}
	
	public User getByLogin(String login){
		 Session session = HibernateUtil.getSessionFactory().openSession();
		 session.beginTransaction();
		
		 Query query = session.createQuery("from User where login = :login");
		 query.setParameter("login", login);
		 query.setMaxResults(1);
		 
		 User user = (User) query.uniqueResult();
		
		 session.getTransaction().commit();
		 session.close();
		
		 return user;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getValidUsers(){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<User> validUsers = null;
		
		try{
			session.beginTransaction();
			Query query = session.createQuery("from User where login is not null");
			
			validUsers = (List<User>) query.list();
			session.getTransaction().commit();
			
		} catch (HibernateException hexp) {
			session.getTransaction().rollback();			
		} finally{
			session.clear();
			session.close();
		}
		
		return validUsers;
	}
	
	public User getValidUser(Integer id){
		
		//TODO
		User user = null;
		
		return user;
	}
}
