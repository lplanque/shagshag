package com.lplanque.shagshag.test.jongo;

import static com.lplanque.shagshag.test.model.Models.JACKSON_MAPPER;
import static com.lplanque.shagshag.test.model.Models.orders;

import java.util.Collection;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.fakemongo.junit.FongoRule;
import com.lplanque.shagshag.test.model.Order;

public abstract class CommonTest {

	@Rule
	public FongoRule rule = new FongoRule();
	
	protected MongoCollection docs;
	protected int no = 10; // Number of orders
	protected int ni = 10; // Number of items
	
	@Before 
	public void init(/* Create the Jongo instance */) {
		final Jongo jongo = new Jongo(rule.getDB(), JACKSON_MAPPER);
		final Collection<Order> orders = orders(no, ni);
		docs = jongo.getCollection(getClass().getSimpleName());
		for(Order order: orders) {
			docs.save(order);
		}
	}
	
	@Test
	public abstract void go();
}
