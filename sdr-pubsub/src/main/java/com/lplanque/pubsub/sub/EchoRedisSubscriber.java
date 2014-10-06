package com.lplanque.pubsub.sub;

import static java.lang.String.format;
import static java.lang.System.out;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class EchoRedisSubscriber implements MessageListener {

	@Override
	public void onMessage(Message msg, byte[] any) {
		out.println(format("echo from channel %s: %s", msg.getChannel(), msg));
	}
}
