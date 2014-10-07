package com.lplanque.odm.morphia.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

public class Order {
	
	@Id 
	private ObjectId id;
	private final Date date;
	@Property("custInfo") 
	private final String customerInfo;
	@Embedded 
	final List<Item> items;
	
	public Order() {
		date = new Date();
		customerInfo = "Clark Kent";
		items = new ArrayList<Item>(100);
		for(int i = 0; i < 100; i++) {
			items.add(new Item());
		}
	}

	public ObjectId getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public String getCustomerInfo() {
		return customerInfo;
	}

	public List<Item> getItems() {
		return items;
	}
}
