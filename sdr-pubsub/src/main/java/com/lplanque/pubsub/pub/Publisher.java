package com.lplanque.pubsub.pub;

public interface Publisher<E> {
	void publish(E msg);
}
