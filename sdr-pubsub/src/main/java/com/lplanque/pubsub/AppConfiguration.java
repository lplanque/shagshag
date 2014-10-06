package com.lplanque.pubsub;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.lplanque.pubsub.sub.SimpleRedisSubscriber;

import redis.clients.jedis.JedisPoolConfig;

//TODO AppConfiguration.class.getPackage().getName() instead ?
@ComponentScan("com.lplanque.pubsub")
@Configuration
public final class AppConfiguration {

	// Just for test...
	@Bean public RedisConnectionFactory getConnectionFactory() {
		final JedisPoolConfig config = new JedisPoolConfig();
        final JedisConnectionFactory factory = new JedisConnectionFactory(config);
        factory.setHostName("localhost");
        factory.setPort(6379);
        factory.setPassword("");
        return factory;
 
    }
	
	@Bean(name = "redisTemplate")
	public <V> RedisTemplate<String,V> getRedisTemplate() {
		final RedisTemplate<String,V> template =  new RedisTemplate<>();
	    template.setConnectionFactory(getConnectionFactory());
	    template.setKeySerializer(new StringRedisSerializer());
	    return template;
	}
	
	@Bean
    public MessageListenerAdapter messageListenerAdapter() {
		return new MessageListenerAdapter(new SimpleRedisSubscriber());
    }
}
