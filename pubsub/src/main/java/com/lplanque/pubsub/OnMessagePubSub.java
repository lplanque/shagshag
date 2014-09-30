package com.lplanque.pubsub;

import redis.clients.jedis.JedisPubSub;

/**
 * Forces {@link JedisPubSub} implementation to fucus on {@link #onMessage(String, String)} method.
 * Others have an empty behaviour...
 * @author <a href="https://github.com/lplanque" target="_blank">Laurent Planque</a>
 */
public abstract class OnMessagePubSub extends JedisPubSub {

	@Override public abstract void onMessage(String channel, String message);

	@Override public final void onPMessage(String pattern, String channel, String message) {
		// Not implemented yet !
	}

	@Override public final void onSubscribe(String channel, int subscribedChannels) {
		// Not implemented yet !
	}

	@Override public final void onUnsubscribe(String channel, int subscribedChannels) {
		// Not implemented yet !
	}

	@Override public final void onPUnsubscribe(String pattern, int subscribedChannels) {
		// Not implemented yet !
	}

	@Override public final void onPSubscribe(String pattern, int subscribedChannels) {
		// Not implemented yet !
	}
}
