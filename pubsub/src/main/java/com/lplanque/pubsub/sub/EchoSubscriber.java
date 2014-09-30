package com.lplanque.pubsub.sub;

import com.lplanque.pubsub.OnMessagePubSub;

public final class EchoSubscriber extends OnMessagePubSub {

	public static final EchoSubscriber INSTANCE = new EchoSubscriber();
	
	private EchoSubscriber() {
		// Singleton
	}
	
    @Override public void onMessage(String channel, String message) {
    	System.out.println(message);
    }
}
