package com.lplanque.pubsub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public interface PubSubAction {
	void doIt(final JedisPubSub ps, final String ch, final Jedis jedis);
}
