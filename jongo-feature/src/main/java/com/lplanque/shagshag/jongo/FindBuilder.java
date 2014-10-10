package com.lplanque.shagshag.jongo;

import java.util.ArrayList;
import java.util.List;

import org.jongo.Find;
import org.jongo.MongoCollection;

/**
 * TODO Explain ! :)
 * @author <a href="https://github.com/lplanque" target="_blank">Laurent Planque</a>
 */
public final class FindBuilder implements AutoCloseable {
	
	private boolean closed;
	private final StringBuilder query; 
	private final List<Object> acc; 
	
	/**
	 * TODO Explain ! :)
	 */
	public FindBuilder() {
		closed = false;
		query = new StringBuilder();
		query.append('{');
		acc = new ArrayList<>();
	}
	
	// API METHODS
	// -----------
	
	public FindBuilder eq(String field, Object value) {
		notClosed();
		// Append the field
		query.append(field);
		// Remove the last ',' when closed
		query.append(":#,");
		// Accumulator for query parameters
		acc.add(value);
		return this;
	}

	public Find toFind(MongoCollection mc) {
		close();
		// Return the 'Find' object
		return mc != null
			? mc.find(query.toString(), acc.toArray())
			: null;
	}
	
	/**
	 * Checks if builder is closed.
	 * @return <code>true</code> if and only if the builder is closed.
	 */
	public boolean isClosed() {
		return closed;
	}
	
	/**
	 * Get the current query template.
	 * @return The current query template.
	 */
	public String queryTemplate() {
		return closed
			? query.toString()
			: close(new StringBuilder(query));
	}
	
	/*
	 * Size of 'acc', i.e. number of current parameters for the query template.
	 */
	public int parametersArity() {
		return acc.size();
	}
	
	// OVERRIDES
	// ---------
	
	/**
	 * Close the current builder. After this operation, no builder method
	 * can be called (such as <code>eq</code>), except {@link #close()} itself,
	 * or '@link {@link #toFind(MongoCollection)}. This method is idempotent.
	 */
	@Override public void close() {
		if(!closed) {
			close(query);
			closed = true;
		}
	}

	// INNER METHODS
	// -------------
	
	/* 
	 * Close the string query template.
	 */
	private String close(StringBuilder sb) {
		// Get the last char
		final int last = sb.length() - 1;
		// Check if last char is not ','
		if(sb.charAt(last) == ',') {
			sb.deleteCharAt(last);
		}
		sb.append('}');
		return sb.toString();
	}
	
	/*
	 * Check is builder is not closed.
	 * If it is, then an IllegalStateException is thrown.
	 */
	private void notClosed() {
		if(closed) {
			throw new IllegalStateException("Builder is closed ! :p");
		}
	}
}
