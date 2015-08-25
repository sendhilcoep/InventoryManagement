package com.inventorymanagement.controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.inventorymanagement.dao.InventoryDAO;
import com.inventorymanagement.dao.TransactionDAO;
import com.inventorymanagement.model.OrderHistory;
import com.inventorymanagement.model.Product;

import authentication.dao.AuthenticationDAO;
import authentication.model.User;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public abstract class ServletImplementation extends HttpServlet {

	public ServletImplementation() {
		super();
	}
	public static List<OrderHistory> prevHistory = new ArrayList<OrderHistory>();
	public static List<String> tableColumnNames = new ArrayList<String>();
	
	protected String filterTransaction(String username, String product, String stime, String etime) {
		System.out.println(Timestamp.valueOf(stime));
		System.out.println(Timestamp.valueOf(etime));
		TransactionDAO tdao = new TransactionDAO();
		List<OrderHistory> orderHistory = null;
		try {
			orderHistory = tdao.queryTransaction(username, stime, etime, product);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> ColNames = new LinkedList();
		List<Product> products = new InventoryDAO().findAll();
		ColNames.add("username");
		ColNames.add("Timestamp");
		for (int i = 0; i < products.size(); i++) {
			ColNames.add(products.get(i).getName());
		}
	
		Gson gson = new Gson();
		String json1 = gson.toJson(orderHistory);
		String json2 = gson.toJson(ColNames);
		
		prevHistory = orderHistory;
		tableColumnNames = ColNames;
		String bothJson = "[" + json1 + "," + json2 + "]";
		return bothJson;
	}
	
	protected void createExcelSheet(WritableSheet s) throws WriteException, RowsExceededException {
		for (int i = 0; i < tableColumnNames.size(); i++) {
			s.addCell(new Label(i,0,tableColumnNames.get(i)));
		}
		for (int j = 0; j < prevHistory.size(); j++) {
			s.addCell(new Label(0, j+1, prevHistory.get(j).getUsername()));
			s.addCell(new Label(1, j+1, prevHistory.get(j).getTime().toString()));
			List<Integer> orderCount = prevHistory.get(j).getOrderCount();
			for (int i = 0; i < orderCount.size(); i++) {
				s.addCell(new Label(i+2, j+1, orderCount.get(i).toString()));		
			}
			
		}
		//s.addCell(new Label(0, 0, "Hello World"));
	}
	
	protected String showAllTransactions() {
		TransactionDAO dao = new TransactionDAO();
		List<OrderHistory> orderHistory = null;
		try {
			orderHistory = dao.findAllTransactions();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<String> ColNames = new LinkedList();
		List<Product> products = new InventoryDAO().findAll();
		ColNames.add("username");
		ColNames.add("Timestamp");
		for (int i = 0; i < products.size(); i++) {
			ColNames.add(products.get(i).getName());
		}

		Gson gson = new Gson();
		String json1 = gson.toJson(orderHistory);
		String json2 = gson.toJson(ColNames);
		String bothJson = "[" + json1 + "," + json2 + "]";
		return bothJson;
	}
	protected String giveUserTransaction(String username) {
		TransactionDAO tdao = new TransactionDAO();
		List<OrderHistory> orderHistory = null;
		try {
			orderHistory = tdao.queryUserTransaction(username);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("size of Transaction is"+ orderHistory.size());
		List<String> ColNames = new LinkedList();
		List<Product> products = new InventoryDAO().findAll();
		ColNames.add("username");
		ColNames.add("Timestamp");
		for (int i = 0; i < products.size(); i++) {
			ColNames.add(products.get(i).getName());
		}

		Gson gson = new Gson();
		String json1 = gson.toJson(orderHistory);
		String json2 = gson.toJson(ColNames);
		String bothJson = "[" + json1 + "," + json2 + "]";
		return bothJson;
	}
	

	protected void deleteProductById(String idStr) {
		System.out.println("Id is" + idStr);
		int id = Integer.parseInt(idStr);
		InventoryDAO dao = new InventoryDAO();
//		List<Product> products = dao.findAllHibernate();
		try {
			List<Product> products = dao.findAll();
			Product product = products.get(id);
			System.out.println(product.getName());
			
			String name = product.getName();
			dao.deleteProductById(product.getId());
			new TransactionDAO().alterTabledeleteproduct(product);
			

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected String executeUserOrder(String username, String[] names) {
		List<Integer> count = new LinkedList<Integer>();
		for (int i = 0; i < names.length; i++) {
			System.out.println(names[i]);
			count.add(Integer.parseInt(names[i]));

		}
		String JspName = new String();
		InventoryDAO dao = new InventoryDAO();
		int result = dao.updateAfterDecreaseCount(count);
		if (result == 0) {
			OrderHistory orderHistory = new OrderHistory(username, count);
			try {
				new TransactionDAO().saveTransaction(orderHistory);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			JspName = "welcome.jsp";
		} else {
			JspName = "user.jsp";
		}
		return JspName;
	}
	protected String changeProductById(String idStr) {
		System.out.println(idStr);
		int id = Integer.parseInt(idStr);
		Product product = (new InventoryDAO()).findByIndex(id);
		List<Product> products = new LinkedList<Product>();
		products.add(product);
		Gson gson = new Gson();
		String productJson = gson.toJson(product);
		System.out.println(productJson);
		return productJson;
	}
	protected void addProductInTable(String Pname, String Pcount, String Pthreshold)
			throws ClassNotFoundException, SQLException {
		Product newProduct = new Product(Pname, Integer.parseInt(Pcount), Integer.parseInt(Pthreshold));
		InventoryDAO dao = new InventoryDAO();
		dao.saveHibernate(newProduct);
		new TransactionDAO().alterTableAddproduct(newProduct);
	}
	protected void addUserInTable(String Uprivilege, String Uusername, String Upassword, String UemployeeID) {
		int Upriv = Integer.parseInt(Uprivilege);
		int empID = Integer.parseInt(UemployeeID);
		User user = new User(Uusername, Upassword, Upriv, empID);
		AuthenticationDAO dao = new AuthenticationDAO();
		dao.saveHibernate(user);
	}
	protected void editProductData(HttpServletRequest request, String Pname, String Pcount, String Pthreshold) {
		Product newProduct = new Product(Pname, Integer.parseInt(Pcount), Integer.parseInt(Pthreshold));
		InventoryDAO dao = new InventoryDAO();
		dao.changeProductById(newProduct, Integer.parseInt(request.getParameter("Pid")));
	}

}