import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.inventorymanagement.dao.InventoryDAO;
import com.inventorymanagement.dao.TransactionDAO;
import com.inventorymanagement.model.OrderHistory;
import com.inventorymanagement.model.Product;

import authentication.controller.Authentication;
import authentication.dao.AuthenticationDAO;
import authentication.model.User;

public class ReportTest {

//	@Before
	@Test
	public void init()
	{
		InventoryDAO dao = new InventoryDAO();
		dao.deleteProducts();
		TransactionDAO tdao = new TransactionDAO();
		tdao.createTable();
		AuthenticationDAO authdao = new AuthenticationDAO();
		authdao.deleteUsers();
	}
//	@Test
//	public void shouldCreateTable()
//	{
//		TransactionDAO dao = new TransactionDAO();
//		dao.createTable();
//		
//	}
	@Test
	public void shouldAddProduct() throws SQLException
	{
		TransactionDAO dao = new TransactionDAO();
		dao.createTable();
		int nColBefore = dao.getTableColumnCount();
		addProduct(dao,"Pens");
		int nColAfter = dao.getTableColumnCount();
		Assert.assertEquals(3, nColAfter);
		Assert.assertEquals(2,nColBefore);
		
		
	}
	@Test
	public void shouldDeleteProduct() throws SQLException, ClassNotFoundException
	{
		TransactionDAO dao = new TransactionDAO();
		dao.createTable();
		addProduct(dao,"Pencils");
		int nColAfter = dao.getTableColumnCount();
		Assert.assertEquals(3, nColAfter);
		dao.alterTabledeleteproduct(new Product("Pencils",100,10));
		int nColAfterDelete = dao.getTableColumnCount();
		Assert.assertEquals(2, nColAfterDelete);
		
	}
	@Test
	public void shouldAddTransaction() throws SQLException, ClassNotFoundException
	{
		TransactionDAO dao = new TransactionDAO();
		dao.createTable();
		int nRowBefore = dao.findAllTransactions().size();
		Assert.assertEquals(0,nRowBefore);
		addProduct(dao,"Pencils");
		AuthenticationDAO authdao = new AuthenticationDAO();
		authdao.saveHibernate(new User("user","password", 0, 12));
		List<Integer> order = new ArrayList<Integer>();
		order.add(10);
		Timestamp time = new Timestamp(System.currentTimeMillis());
		OrderHistory orderHistory = new OrderHistory("user",order,time);
		
		dao.saveTransaction(orderHistory);
		int nRowAfter = dao.findAllTransactions().size();
		Assert.assertEquals(1, nRowAfter);
		try {
			dao.alterTableAddproduct(new Product("Pens",100,10));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		AuthenticationDAO authdao = new AuthenticationDAO();
		authdao.saveHibernate(new User("user1","password", 0, 12));
//		List<Integer> order = new ArrayList<Integer>();
//		order.add(10);
		order.add(1);
		time = new Timestamp(System.currentTimeMillis());
		orderHistory = new OrderHistory("user1",order,time);
		
		dao.saveTransaction(orderHistory);
		nRowAfter = dao.findAllTransactions().size();
		Assert.assertEquals(2, nRowAfter);
		
		int nColAfter = dao.getTableColumnCount();
		Assert.assertEquals(4, nColAfter);
		dao.alterTabledeleteproduct(new Product("Pens",100,10));
		int nColAfterDelete = dao.getTableColumnCount();
		Assert.assertEquals(3, nColAfterDelete);
		
		authdao.saveHibernate(new User("admin","admin", 1, 12));
		
	}
	private void addProduct(TransactionDAO dao, String productName) {
		try {
			Product product = new Product(productName,100,10);
			dao.alterTableAddproduct(product);
			new InventoryDAO().saveHibernate(product);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
////	@Test
//	public void shouldDeleteTransactions()
//	{
//		TransactionDAO dao = new TransactionDAO();
//		dao.createTable();
//	}
////	@Test
//	public void shouldDisplayTransactions()
//	{
//		TransactionDAO dao = new TransactionDAO();
//		dao.createTable();
//	}
////	@Test
//	public void shouldDisplayQueryTransactions()
//	{
//		TransactionDAO dao = new TransactionDAO();
//		dao.createTable();
//	}
//	
//	@Test
//    public void shouldShowReportToAdmin() throws ClassNotFoundException, SQLException{
//           TransactionDAO tdao = new TransactionDAO();
//           tdao.createTable();
//           Assert.assertEquals(5, tdao.findAllTransactions().size());
//    }
//    
    @Test
    public void shouldShowReportFilteredByUserNameToAdmin() throws ClassNotFoundException, SQLException{
           TransactionDAO tdao = new TransactionDAO();
           tdao.createTable();
           AuthenticationDAO authdao = new AuthenticationDAO();
           authdao.saveHibernate(new User("user1","password", 0, 12));
           authdao.saveHibernate(new User("user2","password", 0, 12));
           authdao.saveHibernate(new User("user3","password", 0, 12));
           InventoryDAO dao = new InventoryDAO();
           
           addProduct(tdao, "Pens");
           addProduct(tdao, "Pencils");
           addProduct(tdao, "Erasers");
           List<Integer> list = new ArrayList<Integer>();
           list.add(1);
           list.add(1);
           list.add(1);
           OrderHistory order = new OrderHistory("user1", list );
           tdao.saveTransaction(order);
           order = new OrderHistory("user2", list );
           Assert.assertEquals(1, tdao.findAllByUserName("user1").size());
    }
//    
    @Test
    public void shouldShowReportFilteredByDateRangeToAdmin() throws ClassNotFoundException, SQLException{
           TransactionDAO tdao= new TransactionDAO();
           tdao.createTable();
           AuthenticationDAO authdao = new AuthenticationDAO();
           authdao.saveHibernate(new User("user1","password", 0, 12));
           authdao.saveHibernate(new User("user2","password", 0, 12));
           authdao.saveHibernate(new User("user3","password", 0, 12));
           InventoryDAO dao = new InventoryDAO();
           
           addProduct(tdao, "Pens");
           addProduct(tdao, "Pencils");
           addProduct(tdao, "Erasers");
           List<Integer> list = new ArrayList<Integer>();
           list.add(1);
           list.add(1);
           list.add(1);
           OrderHistory order = new OrderHistory("user1", list );
           tdao.saveTransaction(order);
           order = new OrderHistory("user2", list );
           tdao.saveTransaction(order);
           Timestamp startTime = Timestamp.valueOf("2015-08-9 23:20:50.52");
           Timestamp endTime = new Timestamp(System.currentTimeMillis());
           Assert.assertEquals(2, tdao.findAllByDateRange(startTime,endTime).size());
    }
//    
    @Test
    public void shouldShowReportFilteredByProductNameToAdmin() throws ClassNotFoundException, SQLException{
           TransactionDAO tdao = new TransactionDAO();
           AuthenticationDAO authdao = new AuthenticationDAO();
           authdao.saveHibernate(new User("user1","password", 0, 12));
           authdao.saveHibernate(new User("user2","password", 0, 12));
           authdao.saveHibernate(new User("user3","password", 0, 12));
           InventoryDAO dao = new InventoryDAO();
           
           addProduct(tdao, "Pens");
           addProduct(tdao, "Pencils");
           addProduct(tdao, "Erasers");
           List<Integer> list = new ArrayList<Integer>();
           list.add(1);
           list.add(0);
           list.add(1);
           OrderHistory order = new OrderHistory("user1", list );
           tdao.saveTransaction(order);
           order = new OrderHistory("user2", list );
           tdao.saveTransaction(order);
           Assert.assertEquals(0, tdao.findAllByProductName("Pencils").size());
    }

}
