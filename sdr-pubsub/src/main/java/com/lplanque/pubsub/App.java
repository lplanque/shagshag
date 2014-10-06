package com.lplanque.pubsub;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import com.lplanque.pubsub.pub.StringRedisPublisher;

public final class App {
	
	private App() {
		// Application...
	}

	public static void main(String[] args) {

		@SuppressWarnings("resource")
		final ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		final StringRedisPublisher publisher = context.getBean(StringRedisPublisher.class);
		final RedisMessageListenerContainer container = context.getBean(RedisMessageListenerContainer.class);
		
		// Publish some messages (do u love the "Chemical brothers" ? :p)
		publisher.publish("Hey boy");
		publisher.publish("Hey girl");
		publisher.publish("Superstar DJ's");
		publisher.publish("Here we go !");
	    
		// Stop the subscriber container
		container.stop();
	}
}
