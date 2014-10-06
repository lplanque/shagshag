package com.lplanque.pubsub.sub;

import static java.lang.String.format;
import static java.lang.System.out;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class SimpleSubscriber implements MessageListener {

	@Override
	public void onMessage(Message msg, byte[] pattern) {
		out.println(format("Message received from %s: %s", msg.getChannel(), msg));
	}
}
