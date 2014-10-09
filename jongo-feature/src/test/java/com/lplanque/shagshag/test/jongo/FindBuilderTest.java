package com.lplanque.shagshag.test.jongo;

import static com.lplanque.shagshag.test.model.Models.*;
import static org.junit.Assert.*;

import java.util.Collection;

import org.jongo.Find;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.fakemongo.junit.FongoRule;
import com.lplanque.shagshag.jongo.FindBuilder;
import com.lplanque.shagshag.test.model.Order;

public class FindBuilderTest {
	
	@Rule
	public FongoRule rule = new FongoRule();

	private MongoCollection docs;
	
	private final int no = 10; // Number of orders
	private final int ni = 10; // Number of items
	
	@Before public void init(/* Create the Jongo instance */) {
		final Jongo jongo = new Jongo(rule.getDB(), JACKSON_MAPPER);
		final Collection<Order> orders = orders(no, ni);
		docs = jongo.getCollection("doc");
		for(Order order: orders) {
			docs.save(order);
		}
	}
	
	private void create(/* Check query just after new instance */) {
		try(final FindBuilder fb = new FindBuilder()) {
			assertEquals("{}", fb.queryTemplate());
			assertFalse(fb.isClosed());
			Find find = fb.toFind(docs);
			MongoCursor<Order> orders = find.as(Order.class);
			for(Order order: orders) {
				assertEquals(order(ni), order);
			}
		}
	}
	
	// ALL TESTS !
	// -----------
	
	@Test
	public void go() {
		create(/* Check query just after new instance */);
	}
}
