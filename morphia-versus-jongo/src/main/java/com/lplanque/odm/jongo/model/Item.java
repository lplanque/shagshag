package com.lplanque.odm.jongo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
	
	private int quantity;
	private double price;
	@JsonProperty("desc") private String description;
	
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
