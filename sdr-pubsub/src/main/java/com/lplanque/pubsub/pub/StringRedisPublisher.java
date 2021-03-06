package com.lplanque.pubsub.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

public class StringRedisPublisher implements Publisher<String> {
    
	@Autowired 
	@Qualifier("template")
    private RedisTemplate<String, Object> template;
 
    @Autowired 
    @Qualifier("topic")
    private ChannelTopic topic;
 
    @Override 
    public void publish(String message) {
        template.convertAndSend(topic.getTopic(), message);
    }
}
