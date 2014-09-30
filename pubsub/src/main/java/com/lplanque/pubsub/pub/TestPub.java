package com.lplanque.pubsub.pub;

import com.lplanque.pubsub.PubSubs;

import redis.clients.jedis.JedisPool;

public final class TestPub {

	public static void main(String[] args) {
		
		final JedisPool[] pools = new JedisPool[] {
			new JedisPool("localhost", 6379),
			new JedisPool("localhost", 6380)
		};
		
		PubSubs.ohe("echo", pools);
	}
}
