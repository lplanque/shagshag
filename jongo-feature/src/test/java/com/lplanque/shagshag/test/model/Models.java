package com.lplanque.shagshag.test.model;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.jongo.Mapper;
import org.jongo.marshall.jackson.JacksonMapper;

import com.fasterxml.jackson.datatype.jdk7.Jdk7Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public final class Models {

	public static final DateTime DATE = new DateTime();
	public static final String DESCRIPTION = "price = quantity";
	public static final String INFO_FORMAT = "%d items";
	
	public static final Mapper JACKSON_MAPPER = new JacksonMapper.Builder()
		.registerModule(new JodaModule())
		.registerModule(new Jdk7Module())
		.build();
	
	private Models() {
		// Utilities
	}
	
	public static Item item(int q) {
		final Item item = new Item();
		item.quantity = q;
		item.price = q;
		item.description = DESCRIPTION;
		return item;
	}
	
	public static List<Item> items(final int n) {
		final List<Item> items = new ArrayList<>(n);
		for(int i = 0; i < n; i++) {
			items.add(item(n));
		}
		return items;
	}
	
	public static Order order(final int n) {
		final Order order = new Order();
		order.date = DATE;
		order.customerInfo = format(INFO_FORMAT, n);
		order.items = items(n);
		return order;
	}
	
	public static Collection<Order> orders(final int no, final int ni) {
		final Collection<Order> orders = new ArrayList<>(no);
		for(int i = 0; i < no ; i++) {
			orders.add(order(ni));
		}
		return orders;
	}
}
