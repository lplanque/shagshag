package com.lplanque.shagshag.test.jongo;

import static com.lplanque.shagshag.test.model.Models.*;
import static org.junit.Assert.*;

import com.lplanque.shagshag.jongo.JongoAction;
import com.lplanque.shagshag.jongo.ProxyDelegateMongoCollection;
import com.lplanque.shagshag.test.model.Order;
import com.mongodb.WriteResult;

public class ProxyDelegateMongoCollectionTest extends CommonTest {
	
	private static final JongoAction<Object> PRE_INSERT = new JongoAction<Object>() {
		@Override public void on(Object entity) {
			final Order order = (Order)entity;
			assertEquals("INSERT", order.customerInfo);
			order.customerInfo = "PRE_INSERT";
		}
	};
	
	private static final JongoAction<Object> PRE_SAVE = new JongoAction<Object>() {
		@Override public void on(Object entity) {
			final Order order = (Order)entity;
			assertEquals("SAVE", order.customerInfo);
			order.customerInfo = "PRE_SAVE";			
		}
	};
	
	private static final JongoAction<Object> POST_INSERT = new JongoAction<Object>() {
		@Override public void on(Object entity) {
			final Order order = (Order)entity;
			assertEquals("PRE_INSERT", order.customerInfo);
			order.customerInfo = "POST_INSERT";			
		}
	};
	
	private static final JongoAction<Object> POST_SAVE = new JongoAction<Object>() {
		@Override public void on(Object entity) {
			final Order order = (Order)entity;
			assertEquals("PRE_SAVE", order.customerInfo);
			order.customerInfo = "POST_SAVE";					
		}
	};
	
	private ProxyDelegateMongoCollection proxy;
	private final int times;
	
	public ProxyDelegateMongoCollectionTest() {
		super();
		times = 10; // Number of try to check PRE < POST
	}
	
	private void create(/* check proxy after instanciation */) {
		proxy = new ProxyDelegateMongoCollection(docs);
		// Add JsonAction for pre and post operation on save and insert methods 
		proxy.preInsert(PRE_INSERT);
		proxy.preSave(PRE_SAVE);
		proxy.postInsert(POST_INSERT);
		proxy.postSave(POST_SAVE);
		// Check getters
		assertEquals(docs, proxy.getSource());
		assertEquals(PRE_INSERT, proxy.getPreInsert());
		assertEquals(PRE_SAVE, proxy.getPreSave());
		assertEquals(POST_INSERT, proxy.getPostInsert());
		assertEquals(POST_SAVE, proxy.getPostSave());
	}
	
	private void insert(/* check insert */) {
		for(int i = 0; i < times; i++) {
			final Order order = order(i);
			order.customerInfo = "INSERT";
			final WriteResult res = proxy.insert(order);
			assertNotNull(res);
			assertEquals(1, res.getN());
			assertEquals("POST_INSERT", order.customerInfo);
		}
	}
	
	private void save(/* check save */) { // TODO factorize !
		for(int i = 0; i < times; i++) {
			final Order order = order(i);
			order.customerInfo = "SAVE";
			final WriteResult res = proxy.save(order);
			assertNotNull(res);
			assertEquals(1, res.getN());
			assertEquals("POST_SAVE", order.customerInfo);
		}
	}
	
	// TESTS
	// -----
	
	@Override
	public void go(/* SEQUENCE OF TESTS */) {
		create(/* check proxy after instanciation */);
		insert(/* check insert */);
		save(/* check save */);
	}
}
