package com.lplanque.pubsub;

import com.lplanque.pubsub.util.Action;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

public final class PubSubs {
	
	private PubSubs() {
		// Utilitary class...
	}
	
	private static void assertNotNull(final String meth, final Object... params) {
		int i = 0;
		for(Object param: params) {
			if(param == null) {
				final String msg = String.format("%d parameter for \"%s\" is null", i, meth);
				throw new RuntimeException(msg);
			}
		}
	}
	
	private static void bulk(final Action<Jedis> action, final JedisPool... pools) {
		for(final JedisPool pool: pools) {
			try(final Jedis jedis = pool.getResource()) {
				new Thread(() -> action.on(jedis)).start();
			}
		}		
	}
	
	public static void bulkPub(final String msg, final String ch, final JedisPool... pools) {
		final String meth = "bulkPub(String, String, JedisPool...)";
		assertNotNull(meth, msg, ch, pools);
		bulk(j -> j.publish(ch, msg), pools);
	}
	
	public static void bulkSub(final JedisPubSub ps, final String ch, final JedisPool... pools) {
		final String meth = "bulkSub(JedisPubSub, String, JedisPool...)";
		assertNotNull(meth, ps, ch, pools);
		bulk(j -> j.subscribe(ps, ch), pools);
	}
	
	public static void ohe(final String ch, final JedisPool... pools) { // TODO refactor
		final String meth = "ohe(String, JedisPool...)";
		assertNotNull(meth, ch, pools);
		bulk(j -> j.publish(ch, String.format("ohé %s", j)), pools);
	}
}
