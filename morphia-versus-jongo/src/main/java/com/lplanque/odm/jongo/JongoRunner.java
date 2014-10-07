package com.lplanque.odm.jongo;

import java.net.UnknownHostException;
import java.util.Collection;

import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.lplanque.odm.jongo.model.Order;
import com.lplanque.odm.jongo.model.Orders;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public final class JongoRunner {

	public static void main(String... args) throws UnknownHostException {
		
		final DB db = new MongoClient().getDB("test");
		final Jongo jongo = new Jongo(db);
		final Collection<Order> orders = Orders.generate(10000);
		final MongoCollection mc = jongo.getCollection(Order.class.getSimpleName());
		final long start = System.currentTimeMillis();
		for(Order order: orders) {
			mc.save(order);
		}
		final long end = System.currentTimeMillis();
		System.out.println(String.format("jongo runtime %d (ms)", end - start));
	}
}
