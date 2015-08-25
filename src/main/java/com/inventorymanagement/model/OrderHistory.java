package com.inventorymanagement.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class OrderHistory {
	private String username;
	private static List<String> products = new LinkedList<String>();
	private List<Integer> orderCount = new LinkedList<Integer>();
	
	private Timestamp time;
	public OrderHistory()
	{
		
	}
	public OrderHistory(String username, List<Integer> order, Timestamp time)
	{
		this.username = username;
		this.orderCount = order;
		this.time = time;
	}
	public OrderHistory(String username, List<Integer> order) {
		this.username = username;
		this.orderCount = order;
		this.time = new Timestamp(System.currentTimeMillis());
		
		// TODO Auto-generated constructor stub
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<String> getProducts() {
		return products;
	}
	public List<Integer> getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(List<Integer> orderCount) {
		this.orderCount = orderCount;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	
}
