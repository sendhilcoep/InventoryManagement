package com.inventorymanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class ConnectionFactory {
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;

	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/inventory";
	
	static final String USER = "root";
	static final String PASS = "root";
	
	protected Connection create_connection() throws ClassNotFoundException, SQLException{
		Connection conn = null;
		Statement stmt = null;
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			return conn;
	}
	
	
	public static SessionFactory createSessionFactory(Configuration configuration) {
		//Configuration configuration = new Configuration();
		//configuration.configure();
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
				configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		return sessionFactory;
	}
	protected static Session initHibernate() {
		//creating configuration object
		Configuration cfg=new Configuration();
//		cfg.addClass(type name = new type(););
		
		cfg.configure("hibernate.cfg.xml");//populates the data of the configuration file
		
		//creating seession factory object
		SessionFactory factory= createSessionFactory(cfg);
		
		//creating session object
		Session session=factory.openSession();
		return session;
	}
	protected Session getHibernateSession(){
		Session temp = initHibernate();
		return temp;
	}
}
