package com.lplanque.pubsub.sub;

import redis.clients.jedis.JedisPool;

import com.lplanque.pubsub.PubSubs;

public final class TestSub {

	public static void main(String[] args) {
		
		final JedisPool[] pools = new JedisPool[] {
			new JedisPool("localhost", 6379),
			new JedisPool("localhost", 6380)
		};
		
		PubSubs.sub(EchoSubscriber.INSTANCE, "echo", pools);
	}
}
