package com.lplanque.shagshag.test.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Order {
	
	public ObjectId _id;
	public DateTime date;
	public List<Item> items;
	
	@JsonProperty("info")
	public String customerInfo;
	
	@Override
	public boolean equals(Object o) {
	    if(this == o) return true;
	    if(o == null || getClass() != o.getClass()) return false;
	    final Order that = (Order)o;
	    return //Objects.equal(this.date, that.date) 
	    	/* && */Objects.equal(this.items, that.items)
	    	&& Objects.equal(this.customerInfo, that.customerInfo);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hashCode(date, items, customerInfo);
	}
	
	@Override
	public String toString() {
	    return MoreObjects.toStringHelper(this)
	    	//.add("id", _id)
	    	//.add("date", date)
	    	.add("items", items)
	    	.add("info", customerInfo)
	    	.toString();
	}
}
