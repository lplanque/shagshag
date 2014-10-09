package com.lplanque.shagshag.test.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Item {
	
	public int quantity;
	public double price;
	
	@JsonProperty("desc") 
	public String description;
	
	@Override
	public boolean equals(Object o) {
	    if(this == o) return true;
	    if(o == null || getClass() != o.getClass()) return false;
	    final Item that = (Item)o;
	    return Objects.equal(this.quantity, that.quantity) 
	    	&& Objects.equal(this.price, that.price) 
	    	&& Objects.equal(this.description, that.description);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hashCode(quantity, price, description);
	}
	
	@Override
	public String toString() {
	    return MoreObjects.toStringHelper(this)
	    	.add("quantity", quantity)
	    	.add("price", price)
	    	.add("description", description)
	    	.toString();
	}
}
