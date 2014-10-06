package com.lplanque.pubsub.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

public class SimpleRedisPublisher implements Publisher<Object> {
    
	@Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, Object> template;
 
    @Autowired
    private ChannelTopic topic;
 
    @Override 
    public void publish(Object message) {
        template.convertAndSend(topic.getTopic(), message);
    }
}
