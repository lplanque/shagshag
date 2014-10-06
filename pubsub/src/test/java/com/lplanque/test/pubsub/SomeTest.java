package com.lplanque.test.pubsub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lplanque.pubsub.sub.EchoSubscriber;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import redis.embedded.RedisServer;

/*
 * Seems to be hazardous... :s
 */
public class SomeTest {
	
	private final int port;
	private final int nos;
	
	private final JedisPubSub pubSub;
	private final RedisServer[] servers;
	private final JedisPool[] pools;

	public SomeTest() {
		port = 6379;
		nos = 2;
		pubSub = EchoSubscriber.INSTANCE;
		servers = new RedisServer[nos];
		pools = new JedisPool[nos];
	}
	
	private void nonBlockingSub(final int i) throws Exception {
		new Thread(() -> pools[i].getResource().subscribe(pubSub, "echo")).start();
	}
	
	private void initServers() throws Exception {
		for(int i = 0; i < nos; i++) {
			servers[i] = new RedisServer(port + i);
			servers[i].start();
		}			
	}
	
	private void initPools() throws Exception {
		for(int i = 0; i < nos; i++) {
			pools[i] = new JedisPool("localhost", port + i);
			nonBlockingSub(i);			
		}
	}
	
	@Before public void setup() throws Exception {
		initServers();
		initPools();
	}

	@Test public void test() throws Exception {
		for(int i = 0; i < nos; i++) {
			final String msg = String.format("ohe %d", port + i);
			pools[i].getResource().publish("echo", msg);
		}
	}

	@After public void tearDown() throws Exception {
		for(int i = 0; i < nos; pools[i].close(), servers[i++].stop());
	}
}
