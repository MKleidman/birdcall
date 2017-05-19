package com.birdcall.user;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.birdcall.user.User;


//import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public class UserDao{
	private static SessionFactory sessionFactory;
	
	public Session initSession() {
		try {
			sessionFactory = new Configuration()
				.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
				.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
				.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/birdcall")
				.setProperty("hibernate.connection.username", "socialcode")
				.setProperty("hibernate.connection.password", "edoclaicos")
				.addResource("hibernate/User.hbm.xml")
				.buildSessionFactory();
	    } catch (Throwable ex) { 
	    	System.err.println("Failed to create sessionFactory object." + ex);
	    	throw new ExceptionInInitializerError(ex); 
	    }
		return sessionFactory.openSession();
	}
	public void save(User user){
		Session session = initSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		session.save(user);
		tx.commit();
	}

	public void update(User user){
		return;
	}

	public void delete(User user){
		return;
	}

	public User findByUserEmail(String userEmail){
		Session session = initSession();
		Transaction tx = session.beginTransaction();
		String queryString = "from User where email = '" + userEmail + "'";
		List<User> result = (List<User>) session.createQuery(queryString).list();
		User user = result.get(0);
		tx.commit();
		return user;
	}
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public static void setSessionFactory(SessionFactory sessionFactory) {
		UserDao.sessionFactory = sessionFactory;
	}

}