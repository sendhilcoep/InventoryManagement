package com.inventorymanagement.dao;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.inventorymanagement.model.Product;

public class InventoryDAO extends ConnectionFactory {

	public static List<Product> findAll() {
		Session session = initHibernate();
		Transaction tx = null;
		List<Product> products = null;
		try {
			tx = session.beginTransaction();
			products = session.createQuery("FROM Product").list();
			for (Iterator iterator = products.iterator(); iterator.hasNext();) {
				Product product = (Product) iterator.next();

			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return products;
		// session.close();
	}
	public static Product findByIndex(int id) {
		return findAll().get(id);
//		Session session = initHibernate();
//		Transaction tx = null;
//		List<Product> products = null;
//		try {
//			tx = session.beginTransaction();
//			products = session.createQuery("FROM Product").list();
//			for (Iterator iterator = products.iterator(); iterator.hasNext();) {
//				Product product = (Product) iterator.next();
////				System.out.println(" id: " + product.getId());
////				System.out.println(" Name: " + product.getName());
////				System.out.println("Count: " + product.getQuantity());
//
//			}
//			tx.commit();
//		} catch (HibernateException e) {
//			if (tx != null)
//				tx.rollback();
//			e.printStackTrace();
//		} finally {
//			session.close();
//		}
//		return products.get(id);
		// session.close();
	}

	public void saveHibernate(Product product) throws ClassNotFoundException, SQLException {
		Session session = initHibernate();
		Transaction t = session.beginTransaction();
		session.persist(product);
//		System.out.println("ID: " + product.getId());
		t.commit();// transaction is commited
		session.close();
//		TransactionDAO dao = new TransactionDAO();
//		dao.alterTableAddproduct(product);

	}

	public void deleteProducts() {
		Session session= initHibernate();
		Transaction t = session.beginTransaction();
		String stringQuery = "DELETE FROM Product";
		
		Query query = session.createQuery(stringQuery);
		query.executeUpdate();
		t.commit();
		session.close();

	}

	public void deleteProductById(int id) throws ClassNotFoundException, SQLException {
		Session session= initHibernate();
		Transaction t = session.beginTransaction();
		Product obj = (Product) session.get(Product.class, id);
//		TransactionDAO dao = new TransactionDAO();
//		dao.alterTabledeleteproduct(obj);
//		String hql = "FROM User E WHERE E.username = :username and E.password= :password";
		String stringQuery = "DELETE FROM Product where id= :id";
		Query query = session.createQuery(stringQuery);
		query.setParameter("id",id);
		query.executeUpdate();
		t.commit();
		session.close();

	}
	public static void updateAfterIncreaseCount(List<Integer> count){
		Session session = initHibernate();
		Transaction t = session.beginTransaction();
//		System.out.println("count size is" + count.size());
		InventoryDAO dao = new InventoryDAO();
		List<Product> products = dao.findAll();
		//Update data record of table
		for (int i = 1; i <= count.size(); i++) {

			Product obj = (Product) session.get(Product.class, products.get(i-1).getId());
			int temp = obj.getQuantity();
			temp = temp + count.get(i-1);
			obj.setQuantity(temp);
			session.update(obj); 		
		}
		t.commit();
		session.close();
	}

	public static int updateAfterDecreaseCount(List<Integer> count){
		Session session = initHibernate();
		Transaction t = session.beginTransaction();
		InventoryDAO dao = new InventoryDAO();
		List<Product> products = dao.findAll();
		//Update data record of table
		for (int i = 1; i <= count.size(); i++) {

			Product obj = (Product) session.get(Product.class, products.get(i-1).getId());
			int temp = obj.getQuantity();
			if(temp < count.get(i - 1))
			{
				t.rollback();
				return i;
			}
			temp = temp - count.get(i-1);
			obj.setQuantity(temp);
			session.update(obj); 		
		}
		t.commit();
		session.close();
		return 0;
	}

	public void updateThreshold(List<Integer> count) {
		Session session = initHibernate();
		Transaction t = session.beginTransaction();
		//Update data record of table
		for (int i = 1; i <= count.size(); i++) {

			Product obj = (Product) session.get(Product.class, i);
			
			obj.setThreshold(count.get(i-1));
			session.update(obj); 		
		}
		t.commit();
		session.close();
	}

	public List<Product> getProductsBelowThreshold() {
		Session session = initHibernate();
		Transaction tx = null;
		List<Product> products = null;
		List<Product> belowThreshold = new LinkedList<Product>();
		try {
			tx = session.beginTransaction();
			products = session.createQuery("FROM Product").list();
			for (Iterator iterator = products.iterator(); iterator.hasNext();) {
				Product product = (Product) iterator.next();
				if(product.getQuantity()<product.getThreshold())
				{
					belowThreshold.add(product);
				}
//				System.out.println(" id: " + product.getId());
//				System.out.println(" Name: " + product.getName());
//				System.out.println("Count: " + product.getQuantity());

			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return belowThreshold;
	}
	public List<Product> BelowThreshold() {
		Session session = initHibernate();
		Transaction tx = null;
		List<Product> products = null;
		List<Product> belowThreshold = new LinkedList<Product>();
		try {
			products = session.createQuery("FROM Product where quantity<threshold").list();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return products;
	}
	public List<Product> getProductsOutOfStock() {
		Session session = initHibernate();
		Transaction tx = null;
		List<Product> products = null;
		List<Product> outOfStock = new LinkedList<Product>();
		try {
			products = session.createQuery("FROM Product where quantity=0").list();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return products;
	}
	public void changeProductById(Product newProduct, int parseInt) {
		Session session = initHibernate();
		Transaction t = session.beginTransaction();
		Product obj = (Product) session.get(Product.class, parseInt);
		obj.setName(newProduct.getName());
		obj.setQuantity(newProduct.getQuantity());
		obj.setThreshold(newProduct.getThreshold());
//		newProduct.setId(parseInt);
//		obj = newProduct;
//		obj.setThreshold(count.get(i-1));
		session.update(obj); 		
	
	t.commit();
	session.close();
	}



}
