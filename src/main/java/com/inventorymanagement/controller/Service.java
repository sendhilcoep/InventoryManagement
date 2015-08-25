
package com.inventorymanagement.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.inventorymanagement.dao.InventoryDAO;
import com.inventorymanagement.dao.TransactionDAO;
import com.inventorymanagement.model.OrderHistory;
import com.inventorymanagement.model.Product;

import authentication.controller.Authentication;
import authentication.dao.AuthenticationDAO;
import authentication.model.User;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

//@WebServlet("/Inventorymanagement/service")
public class Service extends ServiceDAO{
	// user service 1. processREquests()

	// Admin service 1. Add products 2.add to productQuantity

	public static void addNewProduct(Product product) throws ClassNotFoundException, SQLException {
		InventoryDAO dao = new InventoryDAO();
		dao.saveHibernate(product);
	}

	public static void increaseProductQuantity(List orderCount) {

	}

	private static final String LOGINPAGE = "loginpage";
	private static final String LOGIN = "login";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// super.doGet(req, resp);
		String Jspname = "testajax.jsp";
		String parameter = request.getParameter("action");
		System.out.println(parameter);
		response.setContentType("Text/Html");
		if (parameter == null) {
			System.out.println("servlet code add employee");
			Jspname = "login.html";
			RequestDispatcher rd = request.getRequestDispatcher(Jspname);
			rd.forward(request, response);
		} else {
			if (parameter.equals(LOGINPAGE)) {

				System.out.println("servlet code add employee");
				Jspname = "login.html";
				RequestDispatcher rd = request.getRequestDispatcher(Jspname);
				rd.forward(request, response);
			} else if (parameter.equals("changeProduct")) {
				changeProduct(request, response);
			}  else if (parameter.equals("showToUser")) {
				showProductsToAdmin(response);
			} else if (parameter.equals("showToAdmin")) {
				showProductsToAdmin(response);
			} else if (parameter.equals("showReport")) {
				showReport(response);
			} else if (parameter.equals("showFilterTransaction")) {
				String pageName = request.getParameter("page");
				if (pageName == null) {
					showFilteredTransaction(request, response);
				} else if (pageName.equals("Excel")) {
					OutputStream out = null;
					try {

						response.setContentType("application/vnd.ms-excel");

						response.setHeader("Content-Disposition", "attachment; filename=sampleName.xls");

						WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
						WritableSheet s = w.createSheet("Demo", 0);

						createExcelSheet(s);
						w.write();
						w.close();

					} catch (Exception e) {
						throw new ServletException("Exception in Excel Sample Servlet", e);
					} finally {
						if (out != null)
							out.close();
					}

				}
			} else if (parameter.equals("showUserTransaction")) {
				showUserTransaction(request, response);

			} else {
				Jspname = "user.jsp";
				RequestDispatcher rd = request.getRequestDispatcher(Jspname);
				rd.forward(request, response);
			}

		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("in post method");

		String parameter = request.getParameter("pagename");
		System.out.println(parameter);
		if (parameter.equals(LOGIN)) {
			authenticateUser(request, response);

		} else if (parameter.equals("request")) {
			getUserOrder(request, response);
		} else if (parameter.equals("addproduct")) {
			addProduct(request,response);
			response.sendRedirect("forwardadmin.jsp");
//			RequestDispatcher rd = request.getRequestDispatcher("forwardadmin.jsp");
//			rd.forward(request, response);
		} else if (parameter.equals("adduser")) {
			addUser(request, response);
			response.sendRedirect("forwardadmin.jsp");
//			RequestDispatcher rd = request.getRequestDispatcher("forwardadmin.jsp");
//			rd.forward(request, response);
		} else if (parameter.equals("editProduct")) {
			editProduct(request);
			RequestDispatcher rd = request.getRequestDispatcher("admin.jsp");
			rd.forward(request, response);
		} else if (parameter.equals("changepwd")) {
			changePassword(request, response);
		}else if (parameter.equals("deleteProduct")) {
			deleteProduct(request, response);
		}
	}

	
}
