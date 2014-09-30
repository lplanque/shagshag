package com.lplanque.pubsub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

public final class PubSubs {

	private PubSubs() {
		// Utilitary class...
	}
	
	public static void bulk(final PubSubAction action, final JedisPubSub ps, final String ch, final JedisPool... pools) {
		if(action == null || ch == null || pools == null) return;
		for(final JedisPool pool: pools) {
			try(final Jedis jedis = pool.getResource()) {
				new Thread(() -> action.doIt(ps, ch, jedis)).start();
			}			
		}
	}
	
	public static void sub(final JedisPubSub ps, final String ch, final JedisPool... pools) {
		bulk((p, c, j) -> j.subscribe(p, c), ps, ch, pools);
	}
	
	public static void ohe(final String ch, final JedisPool... pools) {
		bulk((p, c, j) -> j.publish(ch, String.format("ohé %s", j)), null, ch, pools);
	}
}
