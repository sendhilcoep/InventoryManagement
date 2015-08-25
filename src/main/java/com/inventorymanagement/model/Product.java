package com.inventorymanagement.model;

public class Product {
	int id;
	String name;
	int quantity;
	int threshold;
	
	public Product() {
	}

	public Product(String name, int quantity, int threshold){
		this.name = name;
		this.quantity = quantity;
		this.threshold = threshold;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getThreshold() {
		return threshold;
	}
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	
	
}
