package com.lplanque.shagshag.test.jongo;

import static com.lplanque.shagshag.test.model.Models.*;
import static org.junit.Assert.*;

import org.jongo.Find;
import org.jongo.MongoCursor;

import com.lplanque.shagshag.jongo.FindBuilder;
import com.lplanque.shagshag.test.model.Order;

public class FindBuilderTest extends CommonTest {
	
	private void create(/* Check query just after new instance */) {
		try(final FindBuilder fb = new FindBuilder()) {
			assertEquals("{}", fb.queryTemplate());
			assertFalse(fb.isClosed());
			Find find = fb.toFind(docs);
			MongoCursor<Order> orders = find.as(Order.class);
			for(Order order: orders) {
				assertEquals(order(nitems), order);
			}
		}
	}
	
	private void idempotence(/* check idempotence of close method (non-exhaustive) */) {
		try(final FindBuilder fb = new FindBuilder()) {
			// First close
			fb.close();
			final String template = fb.queryTemplate();
			final int arity = fb.parametersArity();
			assertTrue(fb.isClosed());
			// One more time !
			fb.close();
			assertTrue(fb.isClosed());
			assertEquals(template, fb.queryTemplate());
			assertEquals(arity, fb.parametersArity());
		}
	}
	
	private void eq(/* check when matching (field1 = "value1") */) {
		try(FindBuilder builder = new FindBuilder()) {
			builder.eq("info", String.format(INFO_FORMAT, nitems));
			Find find = builder.toFind(docs);
			MongoCursor<Order> cursor = find.limit(1).as(Order.class);
			assertTrue(cursor.hasNext());
		}
	}
	
	private void neq(/* check when no match (field1 = "value1" AND field2 = "value2") */) {
		try(FindBuilder builder = new FindBuilder()) {
			builder.eq("info", String.format(INFO_FORMAT, nitems + 1));
			Find find = builder.toFind(docs);
			MongoCursor<Order> cursor = find.limit(1).as(Order.class);
			assertFalse(cursor.hasNext());
		}		
	}
	
	// TESTS
	// -----
	
	@Override
	public void go(/* SEQUENCE OF TESTS */) {
		create(/* Check query just after new instance  */);
		idempotence(/* check idempotence of close method (non-exhaustive) */);
		eq(/* check when (field1 = "value1" AND field2 = "value2") */);
		neq(/* check when no match (field1 = "value1" AND field2 = "value2") */);
	}
}
