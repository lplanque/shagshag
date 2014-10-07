package com.lplanque.odm.jongo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jongo.marshall.jackson.oid.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lplanque.odm.morphia.model.Item;

public class Order {

	private ObjectId id;
	private Date date;
	@JsonProperty("custInfo") private String customerInfo;
	List<Item> items;
	
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
