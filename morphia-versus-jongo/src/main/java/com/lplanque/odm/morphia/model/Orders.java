package com.lplanque.odm.morphia.model;

import java.util.ArrayList;
import java.util.Collection;

public final class Orders {

	private Orders() {
		
	}
	
	public static Collection<Order> generate(final int n) {
		final Collection<Order> orders = new ArrayList<>(n);
		for(int i = 0; i < n ; i++) {
			orders.add(new Order());
		}
		return orders;
	}
}
