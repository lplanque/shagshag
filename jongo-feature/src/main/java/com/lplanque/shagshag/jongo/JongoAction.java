package com.lplanque.shagshag.jongo;

/**
 * Functionnal interface used by {@link ProxyDelegateMongoCollection} to do
 * actions before and after insert and save operations on POJOs.
 * @author <a href="https://github.com/lplanque" target="_blank">Laurent Planque</a>
 * @param <E> The type on which the action will be done.
 */
public interface JongoAction<E> {
	void on(E entity);
}
