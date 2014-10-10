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
				assertEquals(order(ni), order);
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
	
	// ALL TESTS !
	// -----------
	
	@Override
	public void go() {
		create(/* Check query just after new instance  */);
		idempotence(/* check idempotence of close method (non-exhaustive) */);
	}
}
