package com.lplanque.pubsub;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.lplanque.pubsub.sub.SimpleRedisSubscriber;

import redis.clients.jedis.JedisPoolConfig;

//TODO AppConfiguration.class.getPackage().getName() instead ?
@ComponentScan("com.lplanque.pubsub")
@Configuration
public final class AppConfiguration {

	// Configuration methods
	private String topic() {
		return "echo";
	}
	
	private String host() {
		return "localhost";
	}
	
	private String pwd() {
		return "";
	}
	
	private int port() {
		return 6379;
	}
	
	// Simple factory...
	@Bean public RedisConnectionFactory connectionFactory() {
		final JedisPoolConfig config = new JedisPoolConfig();
        final JedisConnectionFactory factory = new JedisConnectionFactory(config);
        factory.setHostName(host());
        factory.setPassword(pwd());
        factory.setPort(port());
        return factory;
 
    }
	
	@Bean(name="redisTemplate")
	public <V> RedisTemplate<String,V> redisTemplate() {
		final RedisTemplate<String,V> template =  new RedisTemplate<>();
	    template.setConnectionFactory(connectionFactory());
	    template.setKeySerializer(new StringRedisSerializer());
	    return template;
	}
	
	@Bean
    ChannelTopic channelTopic() {
        return new ChannelTopic(topic());
    }

	@Bean
    public MessageListenerAdapter messageListenerAdapter() {
		return new MessageListenerAdapter(new SimpleRedisSubscriber());
    }
}
