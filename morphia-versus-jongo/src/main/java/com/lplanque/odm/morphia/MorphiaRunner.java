package com.lplanque.odm.morphia;

import java.net.UnknownHostException;
import java.util.Collection;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.lplanque.odm.morphia.model.Order;
import com.lplanque.odm.morphia.model.Orders;
import com.mongodb.MongoClient;

public class MorphiaRunner {

	public static void main(String... args) throws UnknownHostException {
		final Morphia morphia = new Morphia();
		final MongoClient mongo = new MongoClient();
		final Datastore ds = morphia.createDatastore(mongo, "test");
		ds.ensureIndexes(); //creates indexes from @Index annotations in your entities
		ds.ensureCaps(); //creates capped collections from @Entity
		final Collection<Order> orders = Orders.generate(10000);
		final long start = System.currentTimeMillis();
		for(Order order: orders) {
			ds.save(order);
		}
		final long end = System.currentTimeMillis();
		System.out.println(String.format("morphia runtime %d (ms)", end - start));
	}
}
