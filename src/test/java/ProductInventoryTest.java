
/*
 * Test file
 * 
 * 1. test for user and admin functions written
 * 2. tests for later parts related to services to be added
 * 3. Authentication to be included
 */

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.inventorymanagement.dao.InventoryDAO;
import com.inventorymanagement.dao.TransactionDAO;
import com.inventorymanagement.model.Product;

import authentication.dao.AuthenticationDAO;
import authentication.model.User;

public class ProductInventoryTest {
	private static final int USER = 0;
	private static final int ADMIN = 1;
	@Before
	public void init() throws ClassNotFoundException, SQLException
	{
		InventoryDAO dao = new InventoryDAO();
		dao.deleteProducts();
		AuthenticationDAO authDao = new AuthenticationDAO();
		authDao.deleteUsers();
		TransactionDAO tDao = new TransactionDAO();
		tDao.deleteAllTransactions();
		
	}
	// for database
	 @Test
	public void shouldDeleteProducts() {
		InventoryDAO dao = new InventoryDAO();
		;
		dao.deleteProducts();
		Assert.assertEquals(0, dao.findAll().size());
	}
	 @Test
		public void shouldDeleteProductById() {
			InventoryDAO dao = new InventoryDAO();
			;
			dao.deleteProducts();
			Assert.assertEquals(0, dao.findAll().size());
		}
	 @Test
	public void shouldAddEntry() throws ClassNotFoundException, SQLException {
		InventoryDAO dao = new InventoryDAO();
		;
		dao.deleteProducts();
		Product product = new Product("Pencils", 20, 5);
		dao.saveHibernate(product);
		Assert.assertEquals(1, dao.findAll().size());

	}

	 @Test
	public void shouldShowAllEntries() throws ClassNotFoundException, SQLException {
		InventoryDAO dao = new InventoryDAO();
		;
		dao.deleteProducts();
		Product product = new Product("Pencils", 20, 5);
		dao.saveHibernate(product);
		product = new Product("Erasers", 20, 5);
		dao.saveHibernate(product);
		product = new Product("Pens", 20, 5);
		dao.saveHibernate(product);
		Assert.assertEquals(3, dao.findAll().size());
	}

	/*
	 * users
	 */
	 @Test
	public void shouldGenerateAlert() {

	}

	 @Test
	public void shouldDecreaseCount() throws ClassNotFoundException, SQLException {
		int t1, t2;
		InventoryDAO dao = new InventoryDAO();
		;
		dao.deleteProducts();
		Product product = new Product("Pencils", 20, 5);
		dao.saveHibernate(product);
		product = new Product("Erasers", 20, 5);
		dao.saveHibernate(product);
		product = new Product("Pens", 20, 5);
		dao.saveHibernate(product);
		List<Product> products = dao.findAll();
		t1 = products.get(0).getQuantity();
		List<Integer> count = new ArrayList<Integer>();
		for (int i = 0; i < products.size(); i++) {
			count.add(0);
		}
		count.set(0, 5);

		dao.updateAfterDecreaseCount(count);
		products = dao.findAll();
		t2 = products.get(0).getQuantity();
		Assert.assertEquals(5, t1 - t2);
	}

	 @Test
	public void failRequest() throws ClassNotFoundException, SQLException {
		int t1, t2;
		InventoryDAO dao = new InventoryDAO();
		;
		dao.deleteProducts();
		Product product = new Product("Pencils", 20, 5);
		dao.saveHibernate(product);
		product = new Product("Erasers", 20, 5);
		dao.saveHibernate(product);
		product = new Product("Pens", 20, 5);
		dao.saveHibernate(product);
		List<Product> products = dao.findAll();
		t1 = products.get(0).getQuantity();
		List<Integer> count = new LinkedList<Integer>();
		for (int i = 0; i < products.size(); i++) {
			count.add(5);
		}
		count.set(1, 21);
		int output = dao.updateAfterDecreaseCount(count);
		Assert.assertEquals(2, output);
		products = dao.findAll();
		t2 = products.get(0).getQuantity();
		Assert.assertEquals(0, t1 - t2);

	}

	// Admin
	 @Test
	public void shouldShowProductsBelowThreshold() throws ClassNotFoundException, SQLException {
		 int QuantityBefore, QuantityAfter;
			InventoryDAO dao = new InventoryDAO();
			;
			dao.deleteProducts();
			Product product = new Product("Pencils", 5, 20);
			dao.saveHibernate(product);
			product = new Product("Erasers", 20, 5);
			dao.saveHibernate(product);
			product = new Product("Pens", 20, 5);
			dao.saveHibernate(product);

			List<Product> products = dao.BelowThreshold();
			Assert.assertEquals(1, products.size());
			Assert.assertEquals("Pencils",products.get(0).getName());
	}

	 @Test
		public void shouldShowProductsOutOfStock() throws ClassNotFoundException, SQLException {
			 int QuantityBefore, QuantityAfter;
				InventoryDAO dao = new InventoryDAO();
				;
				dao.deleteProducts();
				Product product = new Product("Pencils", 0, 20);
				dao.saveHibernate(product);
				product = new Product("Erasers", 20, 5);
				dao.saveHibernate(product);
				product = new Product("Pens", 20, 5);
				dao.saveHibernate(product);

				List<Product> products = dao.getProductsOutOfStock();
				Assert.assertEquals(1, products.size());
				Assert.assertEquals("Pencils",products.get(0).getName());
		}
	 
	 @Test
	public void shouldAddNewProduct() throws ClassNotFoundException, SQLException {
		InventoryDAO dao = new InventoryDAO();

		dao.deleteProducts();
		Product product = new Product("Pencils", 20, 5);
		dao.saveHibernate(product);
		Assert.assertEquals(1, dao.findAll().size());
		product = new Product("Erasers", 15, 3);
		dao.saveHibernate(product);
		Assert.assertEquals(2, dao.findAll().size());

	}

	 @Test
	public void shouldChangeProductCount() throws ClassNotFoundException, SQLException {
		int QuantityBefore, QuantityAfter;
		InventoryDAO dao = new InventoryDAO();
		;
		dao.deleteProducts();
		Product product = new Product("Pencils", 20, 5);
		dao.saveHibernate(product);
		product = new Product("Erasers", 20, 5);
		dao.saveHibernate(product);
		product = new Product("Pens", 20, 5);
		dao.saveHibernate(product);

		List<Product> products = dao.findAll();
		QuantityBefore = products.get(0).getQuantity();

		List<Integer> count = new LinkedList<Integer>();
		for (int i = 0; i < products.size(); i++) {
			count.add(0);
		}

		count.set(0, 5);
		dao.updateAfterIncreaseCount(count);

		products = dao.findAll();
		QuantityAfter = products.get(0).getQuantity();
		Assert.assertEquals(5, QuantityAfter - QuantityBefore);
	}

	@Test
	public void shouldChangeThreshold() throws ClassNotFoundException, SQLException {
		int thresholdBefore, thresholdAfter;
		InventoryDAO dao = new InventoryDAO();
		;
		dao.deleteProducts();
		Product product = new Product("Pencils", 20, 5);
		dao.saveHibernate(product);
		product = new Product("Erasers", 20, 5);
		dao.saveHibernate(product);
		product = new Product("Pens", 20, 5);
		dao.saveHibernate(product);

		List<Product> products = dao.findAll();
		thresholdBefore = products.get(0).getThreshold();

		List<Integer> count = new LinkedList<Integer>();
		for (int i = 0; i < products.size(); i++) {
			count.add(0);
		}

		count.set(0, 10);
		dao.updateThreshold(count);

		products = dao.findAll();
		thresholdAfter = products.get(0).getThreshold();
		Assert.assertEquals(5, thresholdAfter - thresholdBefore);
	}

	// Authentication

	// for database
	@Test
	public void shouldDeleteUsers() {
		// InventoryDAO dao = new InventoryDAO();
		AuthenticationDAO dao = new AuthenticationDAO();
		dao.deleteUsers();
		Assert.assertEquals(0, dao.findAllHibernate().size());
	}

	@Test
	public void shouldAddUser() {
		AuthenticationDAO dao = new AuthenticationDAO();
		dao.deleteUsers();
		User user = new User("user", "user", USER, 1);
		dao.saveHibernate(user);
		Assert.assertEquals(1, dao.findAllHibernate().size());

	}

	@Test
	public void shouldShowAllUsers() {
		AuthenticationDAO dao = new AuthenticationDAO();
		dao.deleteUsers();
		User user = new User("user", "user", USER, 1);
		dao.saveHibernate(user);
		user = new User("admin", "admin", ADMIN, 1);
		dao.saveHibernate(user);
		Assert.assertEquals(2, dao.findAllHibernate().size());
	}

	@Test
	public void shouldLoginAsUser() {
		AuthenticationDAO dao = new AuthenticationDAO();
		dao.deleteUsers();
		User user = new User("user", "user", USER, 1);
		dao.saveHibernate(user);
		user = new User("admin", "admin", ADMIN, 1);
		dao.saveHibernate(user);
		user = new User("user", "user");
		List<User> auth = dao.authenticate(user);
		Assert.assertEquals(1, auth.size());
		Assert.assertEquals(USER, auth.get(0).getPrivilege());
		// Long auth = dao.authenticate(user);
		System.out.println("Success Auth is " + auth.get(0).getUsername() + auth.get(0).getPrivilege());

	}

	@Test
	public void shouldFailLoginAsUser() {

		AuthenticationDAO dao = new AuthenticationDAO();
		dao.deleteUsers();
		User user = new User("user", "user", USER, 1);
		dao.saveHibernate(user);
		user = new User("admin", "admin", ADMIN, 1);
		dao.saveHibernate(user);
		user = new User("user", "admin");
		List<User> auth = dao.authenticate(user);
		Assert.assertEquals(0, auth.size());
		System.out.println("Fail Auth is " + auth);
		// auth = dao.authenticate(user);
		// Assert.assertEquals(1,auth);
		// System.out.println("Fail Auth is " + auth);
	}

	@Test
	public void shouldLoginAsAdmin() {
		AuthenticationDAO dao = new AuthenticationDAO();
		dao.deleteUsers();
		User user = new User("user", "user", USER, 1);
		dao.saveHibernate(user);
		user = new User("admin", "admin", ADMIN, 1);
		dao.saveHibernate(user);
		user = new User("admin", "admin");
		List<User> auth = dao.authenticate(user);
		Assert.assertEquals(1, auth.size());
		Assert.assertEquals(ADMIN, auth.get(0).getPrivilege());
	}

	@Test
	public void shouldFailLoginAsAdmin() {

		AuthenticationDAO dao = new AuthenticationDAO();
		dao.deleteUsers();
		User user = new User("user", "user", USER, 1);
		dao.saveHibernate(user);
		user = new User("admin", "admin", ADMIN, 1);
		dao.saveHibernate(user);
		user = new User("admin", "anything");
		List<User> auth = dao.authenticate(user);
		Assert.assertEquals(0, auth.size());
		System.out.println("Fail Auth is " + auth);
	}

	// functions if needed
	public void deleteAllProducts() {

	}

	public void insertAProduct() {
	}

}
