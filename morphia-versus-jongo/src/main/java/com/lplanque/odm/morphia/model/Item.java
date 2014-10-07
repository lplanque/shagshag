package com.lplanque.odm.morphia.model;

import org.mongodb.morphia.annotations.Property;

public class Item {
	
	private final int quantity;
	private final double price;
	@Property("desc") 
	private final String description;
	
	public Item() {
		quantity = 1;
		price = 10.0;
		description = "Wonderful item with a low price";
	}

	public int getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}
}
