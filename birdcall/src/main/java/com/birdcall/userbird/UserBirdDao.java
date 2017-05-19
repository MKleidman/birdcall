package com.birdcall.userbird;

import java.util.List;

import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.birdcall.user.User;
import com.birdcall.userbird.UserBird;


//import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public class UserBirdDao{
	private static SessionFactory sessionFactory;
	
	public Session initSession() {
		try {
			sessionFactory = new Configuration()
				.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
				.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
				.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/birdcall")
				.setProperty("hibernate.connection.username", "socialcode")
				.setProperty("hibernate.connection.password", "edoclaicos")
				.addResource("hibernate/UserBird.hbm.xml")
				.buildSessionFactory();
	    } catch (Throwable ex) { 
	    	System.err.println("Failed to create sessionFactory object." + ex);
	    	throw new ExceptionInInitializerError(ex); 
	    }
		return sessionFactory.openSession();
	}
	public void save(UserBird userBird){
		Session session = initSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		session.save(userBird);
		tx.commit();
	}

	public void update(UserBird userBird){
		return;
	}

	public void delete(UserBird userBird){
		return;
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public static void setSessionFactory(SessionFactory sessionFactory) {
		UserBirdDao.sessionFactory = sessionFactory;
	}
	
	public List<UserBird> getBirdsForUser(Long userId) {
		Session session = initSession();
		Transaction tx = session.beginTransaction();
		String queryString = "from UserBird where user_id = " + userId;
		List<UserBird> result = (List<UserBird>) session.createQuery(queryString).list();
		tx.commit();
		return result;
	}

}