package com.inventorymanagement.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.inventorymanagement.model.OrderHistory;
import com.inventorymanagement.model.Product;

/*
 * Functions to be included in this DAO
 * 
 * 1. Save a product to alter table in the database
 * 2. Order to update table by add entry
 * 3. Search for transactions by username
 * 4. Search for transactions by date/time
 * 
 * 
 */
public class TransactionDAO extends ConnectionFactory {

	private static final String TABLENAME = "thistorytestPurpose";
	
	public void createTable()
	{
		Connection connection = null;
		try {
			connection = create_connection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String query = "DROP TABLE "+TABLENAME+";";
		String query1 = "CREATE TABLE thistorytestPurpose (\r\n" + 
				"  `username` VARCHAR(30) NOT NULL,\r\n" + 
				"  `time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\r\n" + 
				"  PRIMARY KEY (`username`,`time`)\r\n" + 
				") ENGINE=INNODB DEFAULT CHARSET=utf8";
		System.out.println(query1);
		try {
			connection.createStatement().executeUpdate(query);
			connection.createStatement().executeUpdate(query1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<OrderHistory> findAllTransactions() throws ClassNotFoundException, SQLException {
		
		 Connection connection = create_connection();
		 ResultSet result = connection.createStatement().executeQuery("Select * from "+TABLENAME);
		 ResultSetMetaData metadata = result.getMetaData();
		 int columnCount = metadata.getColumnCount();
		 List<OrderHistory> orders = new ArrayList<OrderHistory>();
		 while(result.next()){
			 OrderHistory order = new OrderHistory();
			 List<Integer> count = new ArrayList<Integer>();
			 order.setUsername(result.getString(1));
			 order.setTime(result.getTimestamp(2));
			 for (int column = 3; column <= columnCount; column++) {
				count.add(result.getInt(column));
			}
			 order.setOrderCount(count);
			 orders.add(order);
		 }
		 connection.close();
		 return orders;
	}

	public void saveTransaction(OrderHistory order) throws ClassNotFoundException, SQLException {
		Connection connection = create_connection();
		StringBuffer tString = new StringBuffer( "INSERT INTO "+TABLENAME+" VALUES( \""+order.getUsername()
				+"\", NOW() ");
//		String temp = new String();
		System.out.println("size: "+order.getOrderCount().size());
		for (int i = 0; i < order.getOrderCount().size(); i++) {
			
			tString.append(", " + order.getOrderCount().get(i));
			System.out.println(tString);
		}
		tString.append(")");
		System.out.println(tString.toString());
		connection.createStatement().executeUpdate(tString.toString());
		connection.close();
		
	}

	public List<OrderHistory> queryTransaction(String username, String stime, String etime, String ProductName) throws ClassNotFoundException, SQLException {
		if((username.equals(""))&&(stime.equals(""))&&(etime.equals(""))&&(ProductName.equals("")))
		{
			return(findAllTransactions());
		}
		String query = "Select * from "+TABLENAME+" WHERE ";
		int count = 0;
		StringBuilder tUser = new StringBuilder("");
		if(!username.equals(""))
		{
			if(count!=0)
			{
				tUser.append(" and ");
			}
			tUser.append("username like '"+username+"'");
			count++;
		}
		StringBuilder tSTime = new StringBuilder("");
		if(!stime.equals(""))
		{
			if(count!=0)
			{
				tSTime.append(" and ");
			}
			tSTime.append(" time > '"+Timestamp.valueOf(stime)+"'");
			count++;
		}
		StringBuilder tETime = new StringBuilder("");
		if(!etime.equals(""))
		{
			if(count!=0)
			{
				tETime.append(" and ");
			}
			tETime.append(" time < '"+Timestamp.valueOf(etime)+"'");
			count++;
		}
		StringBuilder tPName = new StringBuilder("");
		if(!ProductName.equals(""))
		{
			if(count!=0)
			{
				tPName.append(" and ");
			}
			tPName.append(" "+ProductName+ ">0");
			count++;
		}
		System.out.println(query+tUser+tSTime+tETime+tPName);
		String sql = query + tUser + tSTime + tETime + tPName;
		Connection connection = create_connection();
		ResultSet result = connection.createStatement().executeQuery(sql);
		ResultSetMetaData metadata = result.getMetaData();
		 int columnCount = metadata.getColumnCount();
		 List<OrderHistory> orders = new ArrayList<OrderHistory>();
		 while(result.next()){
			 OrderHistory order = new OrderHistory();
			 List<Integer> count1 = new ArrayList<Integer>();
			 order.setUsername(result.getString(1));
			 order.setTime(result.getTimestamp(2));
			 for (int column = 3; column <= columnCount; column++) {
				count1.add(result.getInt(column));
			}
			 order.setOrderCount(count1);
			 orders.add(order);
		 }
		 connection.close();
		 return orders;

	}

	public List<OrderHistory> queryUserTransaction(String username) throws ClassNotFoundException, SQLException {
		if((username.equals("")))
		{
			return(findAllTransactions());
		}
		String query = "Select * from "+TABLENAME+" WHERE ";
		int count = 0;
		StringBuilder tUser = new StringBuilder("");
		if(!username.equals(""))
		{
			if(count!=0)
			{
				tUser.append(" and ");
			}
			tUser.append("username like '"+username+"' ORDER BY TIME DESC");
			count++;
		}
		
		String sql = query + tUser ;
		Connection connection = create_connection();
		ResultSet result = connection.createStatement().executeQuery(sql);
		ResultSetMetaData metadata = result.getMetaData();
		 int columnCount = metadata.getColumnCount();
		 List<OrderHistory> orders = new ArrayList<OrderHistory>();
		 while(result.next()){
			 OrderHistory order = new OrderHistory();
			 List<Integer> count1 = new ArrayList<Integer>();
			 order.setUsername(result.getString(1));
			 order.setTime(result.getTimestamp(2));
			 for (int column = 3; column <= columnCount; column++) {
				count1.add(result.getInt(column));
			}
			 order.setOrderCount(count1);
			 orders.add(order);
		 }
		 connection.close();
		 return orders;

	}
	public void alterTableAddproduct(Product product) throws ClassNotFoundException, SQLException {
		Connection connection = create_connection();
		String query = "ALTER TABLE " + TABLENAME+" ADD COLUMN "+product.getName()+" int default 0";
		 System.out.println(query);
		 connection.createStatement().executeUpdate(query);
		 connection.close();
		
		
	}

	public void alterTabledeleteproduct(Product product) throws ClassNotFoundException, SQLException {
		Connection connection = create_connection();
		String query = "ALTER TABLE "+ TABLENAME + " DROP COLUMN "+product.getName() ;
		 System.out.println(query);
		 connection.createStatement().executeUpdate(query);
		 connection.close();
		;
	}

	public void deleteAllTransactions() throws ClassNotFoundException, SQLException {
		Connection connection = create_connection();
		String query = "DELETE FROM "+TABLENAME;
		 System.out.println(query);
		 connection.createStatement().executeUpdate(query);
		 connection.close();
	}
	
	
	public int getCountAllTransactions() throws ClassNotFoundException, SQLException{
		Connection connection = create_connection();
		ResultSet result = connection.createStatement().executeQuery("Select count(*) from "+TABLENAME);
		while(result.next())
		{
			return(result.getInt(1));
		}
		connection.close();
		return 0;
		
	}
	
	public int getTableColumnCount() throws SQLException
	{
		Connection connection = null;
		try {
			connection = create_connection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String query = "select * from "+TABLENAME;
		ResultSet result = null;
		try {
			result = connection.createStatement().executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSetMetaData rsmd = result.getMetaData();

		int columnsNumber = rsmd.getColumnCount();
		connection.close();
		return columnsNumber;
	}

	public List<OrderHistory> findAllByUserName(String string) throws ClassNotFoundException, SQLException {
		return(queryTransaction(string, "", "", ""));
	}

	public List<OrderHistory> findAllByDateRange(Timestamp startTime, Timestamp endTime) throws ClassNotFoundException, SQLException {
		return queryTransaction("", startTime.toString(), endTime.toString(), "");
	}

	public List<OrderHistory> findAllByProductName(String string) throws ClassNotFoundException, SQLException {
		return queryTransaction("", "", "", string);
	}

}
