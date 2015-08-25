package authentication.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import authentication.model.User;

public class AuthenticationDAO extends ConnectionFactory {

	public static List findAllHibernate() {
		Session session = initHibernate();
		Transaction tx = null;
		List<User> users = null;
		try {
			tx = session.beginTransaction();
			users = session.createQuery("FROM User").list();
			for (Iterator iterator = users.iterator(); iterator.hasNext();) {
				User user = (User) iterator.next();
//				System.out.println(" id: " + user.getId());
//				System.out.println(" Name: " + user.getUsername());
//				System.out.println("Count: " + user.getPassword());

			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return users;
		// session.close();
	}

	public void saveHibernate(User user) {
		Session session = initHibernate();
		Transaction t = session.beginTransaction();
		session.persist(user);
		System.out.println("ID: " + user.getId());
		t.commit();// transaction is committed
		session.close();

	}

	public void deleteUsers() {
		Session session = initHibernate();
		Transaction t = session.beginTransaction();
		String stringQuery = "DELETE FROM User";
		Query query = session.createQuery(stringQuery);
		query.executeUpdate();
		t.commit();
		session.close();

	}

	public List<User> authenticate(User user) {
		Session session = initHibernate();

		String hql = "FROM User E WHERE E.username = :username and E.password= :password";
		Query query = session.createQuery(hql);
		query.setParameter("username", user.getUsername());
		query.setParameter("password", user.getPassword());
		List<User> results = (List<User>) query.list();
		return (results);

	}

	public int changePassword(String username, String oldPassword, String newPassword) {
		Session session = initHibernate();
		Transaction t = session.beginTransaction();
		String hql = "FROM User E WHERE E.username = :username and E.password= :password";
		Query query = session.createQuery(hql);
		query.setParameter("username", username);
		query.setParameter("password", oldPassword);
		List<User> results = (List<User>) query.list();
		if(results.size() == 0)
			return(1);
		results.get(0).setPassword(newPassword);
		t.commit();
		return 0;
	}

}
