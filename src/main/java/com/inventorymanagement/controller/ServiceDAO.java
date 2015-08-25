package com.inventorymanagement.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;

import com.google.gson.Gson;
import com.inventorymanagement.dao.InventoryDAO;
import com.inventorymanagement.dao.TransactionDAO;
import com.inventorymanagement.model.OrderHistory;
import com.inventorymanagement.model.Product;
import com.mysql.fabric.Response;

import authentication.controller.Authentication;
import authentication.dao.AuthenticationDAO;
import authentication.model.User;

public class ServiceDAO  extends ServletImplementation {
	protected void showProductsToAdmin(HttpServletResponse response) throws IOException {
		InventoryDAO dao = new InventoryDAO();
		List<Product> products = dao.findAll();
		Gson gson = new Gson();
		String json = gson.toJson(products);
		writeWithPrintWriter(response, json);
	}

	protected void writeWithPrintWriter(HttpServletResponse response, String json) throws IOException {
		PrintWriter pw = response.getWriter();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		
		pw.print(json);
		pw.close();
	}

	protected void showReport(HttpServletResponse response) throws IOException {
		String bothJson = showAllTransactions();
		writeWithPrintWriter(response, bothJson);
//		PrintWriter pw = response.getWriter();
//		response.setCharacterEncoding("UTF-8");
//		response.setContentType("application/json");
//		
//		pw.write(bothJson);
//		pw.close();
	}

	
	protected void showFilteredTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		System.out.println(request.getParameter("username"));
//		System.out.println(request.getParameter("stime"));
//		System.out.println(request.getParameter("etime"));
//		System.out.println(request.getParameter("product"));
		String username = request.getParameter("username");
		String product = request.getParameter("product");
		String stime = request.getParameter("stime");
		String etime = request.getParameter("etime");
		// String datetimeLocal = "2015-08-9 23:20:50.52";
		// String datetimeLocal = "2015-08-11 23:20:50.52";
		String bothJson = filterTransaction(username, product, stime, etime);
		writeWithPrintWriter(response, bothJson);
//		PrintWriter pw = response.getWriter();
//		response.setCharacterEncoding("UTF-8");
//		response.setContentType("application/json");
//		pw.write(bothJson);
//		pw.close();
	}

	protected void showUserTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println(request.getParameter("username"));
//		System.out.println(request.getParameter("stime"));
//		System.out.println(request.getParameter("etime"));
//		System.out.println(request.getParameter("product"));
		String username = request.getParameter("username");
//		String product = request.getParameter("product");
//		String stime = request.getParameter("stime");
//		String etime = request.getParameter("etime");
		// String datetimeLocal = "2015-08-9 23:20:50.52";
		// String datetimeLocal = "2015-08-11 23:20:50.52";
//		System.out.println(Timestamp.valueOf(stime));
//		System.out.println(Timestamp.valueOf(etime));
		String bothJson = giveUserTransaction(username);
		writeWithPrintWriter(response, bothJson);
//		PrintWriter pw = response.getWriter();
//		response.setCharacterEncoding("UTF-8");
//		response.setContentType("application/json");
//		pw.write(bothJson);
//		pw.close();
	}

	

	protected void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idStr = request.getParameter("Id");
		deleteProductById(idStr);
		writeWithPrintWriter(response, "success");
//		PrintWriter pw = response.getWriter();
//		response.setCharacterEncoding("UTF-8");
//		response.setContentType("application/json");
//
//		pw.print("success");
//		pw.close();
	}


	@SuppressWarnings("static-access")
	protected void changeProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idStr = request.getParameter("Id");
		String productJson = changeProductById(idStr);
		writeWithPrintWriter(response, productJson);
//		PrintWriter pw = response.getWriter();
//		response.setCharacterEncoding("UTF-8");
//		response.setContentType("application/json");
//
//		pw.print(productJson);
//		pw.close();
	}

	

	

	@SuppressWarnings("static-access")
	protected void getUserOrder(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println(request.getParameterValues("quantity").length);
		String username = request.getParameter("username");
		// System.out.println("Dummy"+request.getParameter("username"));
		System.out.println("Order made by " + username);
		String[] names = request.getParameterValues("quantity");
		String JspName = executeUserOrder(username, names);
		redirectToUser(request, response, username, JspName);
	}



	private void redirectToUser(HttpServletRequest request, HttpServletResponse response, String username, String JspName)
			throws IOException {
		request.setAttribute("username", username);
		response.sendRedirect(JspName);
	}

	protected void authenticateUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("txtUserName");
		String password = request.getParameter("txtPassword");
		System.out.println(userName + " pass  " + password);
		User user = new User(userName, password);
		Authentication authentication = new Authentication();
		List<User> auth = authentication.authenticate(user);
		System.out.println("auth is " + auth.size());
		if (auth.size() == 0) {
			RequestDispatcher rd = request.getRequestDispatcher("login.html");
			rd.forward(request, response);
			// response.sendRedirect("login.html");
		} else if (auth.get(0).getPrivilege() == 1) {

			InventoryDAO dao = new InventoryDAO();
			// set products for warning
			List<Product> products = dao.getProductsBelowThreshold();
			String string = new String();
			for (int i = 0; i < products.size(); i++) {
				string = string + products.get(i).getName() + "\n";
			}
			request.setAttribute("belowThresholds", products);
			request.setAttribute("belowThreshold", string);
			// set products for out of stock
			List<Product> OutOfStock = dao.getProductsOutOfStock();
			String stringOutOfStock = new String();
			for (int i = 0; i < OutOfStock.size(); i++) {
				stringOutOfStock = stringOutOfStock + OutOfStock.get(i).getName() + "\n";
			}
			request.setAttribute("outOfStock", stringOutOfStock);
			request.setAttribute("outOfStocks", OutOfStock);
			RequestDispatcher rd = request.getRequestDispatcher("admin.jsp");
			rd.forward(request, response);
			// response.sendRedirect("admin.jsp");
		} else {
			System.out.println("Logged in User" + userName);
			
			request.setAttribute("username", userName);
			RequestDispatcher rd = request.getRequestDispatcher("user.jsp");
			rd.forward(request, response);
			// response.sendRedirect("welcome.jsp");
		}
	}

	protected void addProduct(HttpServletRequest request,HttpServletResponse response) throws IOException {
//		System.out.println(request.getParameter("Pname"));
//		System.out.println(request.getParameter("Pcount"));
//		System.out.println(request.getParameter("Pthreshold"));
		String Pname = request.getParameter("Pname");
		String Pcount = request.getParameter("Pcount");
		String Pthreshold = request.getParameter("Pthreshold");
		try {
			addProductInTable(Pname, Pcount, Pthreshold);
		} catch (ClassNotFoundException e) {
		
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch(HibernateException e){
			writeWithPrintWriter(response, "The product Name already exists");
//			PrintWriter pw = response.getWriter();
//		pw.write("The product Name already exists");
//		pw.close();
		}
		catch (SQLException e) {
			writeWithPrintWriter(response, "The product Name already exists");
//			PrintWriter pw = response.getWriter();
//			pw.write("The product Name already exists");
//			pw.close();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	protected void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		System.out.println(request.getParameter("Uusername"));
//		System.out.println(request.getParameter("UemployeeID"));
//		System.out.println(request.getParameter("optradio"));
		String Uprivilege = request.getParameter("optradio");
		String Uusername = request.getParameter("Uusername");
		String Upassword = request.getParameter("Upassword");
		String UemployeeID = request.getParameter("UemployeeID");
		try{
			addUserInTable(Uprivilege, Uusername, Upassword, UemployeeID);
		}
		catch(HibernateException e){
			writeWithPrintWriter(response, "The User Name already exists");
//			PrintWriter pw = response.getWriter();
//			pw.append("");
//			pw.close();
		}
	}

	

	protected void editProduct(HttpServletRequest request) {
//		System.out.println("Edit product" + request.getParameter("Pid"));
//		System.out.println(request.getParameter("Pname"));
//		System.out.println(request.getParameter("Pcount"));
//		System.out.println(request.getParameter("Pthreshold"));
		String Pname = request.getParameter("Pname");
		String Pcount = request.getParameter("Pcount");
		String Pthreshold = request.getParameter("Pthreshold");
		editProductData(request, Pname, Pcount, Pthreshold);
		// dao.saveHibernate(newProduct);
	}

	

	protected void changePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		System.out.println("Edit product" + request.getParameter("Name"));
//		System.out.println(request.getParameter("oldPassword"));
//		System.out.println(request.getParameter("newPassword"));
//		System.out.println(request.getParameter("newPassword1"));
		String username = request.getParameter("Name");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String newPassword1 = request.getParameter("newPassword1");
		if (newPassword.equals(newPassword1)) {
			AuthenticationDAO dao = new AuthenticationDAO();
			int result = dao.changePassword(username, oldPassword, newPassword);
			if(result == 0)
			{
				writeWithPrintWriter(response,  "password successfully changed");
			}else
			{
				writeWithPrintWriter(response,  "Old password is incorrect");
			}
//			PrintWriter pw = response.getWriter();
//			pw.print(result == 0 ? "password successfully changed" : "Old password is incorrect");
//			pw.close();
		} else {
			writeWithPrintWriter(response," new passwords do not match");
//			PrintWriter pw = response.getWriter();
//			pw.print(" new passwords do not match");
//			pw.close();
		}
	}

}
