package com.lplanque.pubsub.util;

public interface Action<E> {
	void on(E elt);
}
