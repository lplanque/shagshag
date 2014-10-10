package com.lplanque.shagshag.jongo;

import org.bson.types.ObjectId;
import org.jongo.Aggregate;
import org.jongo.Distinct;
import org.jongo.Find;
import org.jongo.FindAndModify;
import org.jongo.FindOne;
import org.jongo.MongoCollection;
import org.jongo.Update;

import com.mongodb.DBCollection;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

/**
 * This class wraps an instance of {@link MongoCollection} to add <I>post</I> and <I>pre</I>-treatments
 * when calling {@link #save(Object)} and {@link #insert(Object)}, instead of directly calling eponym
 * methods on the targeted collection.
 * @author <a href="https://github.com/lplanque" target="_blank">Laurent Planque</a>
 */
public final class ProxyDelegateMongoCollection extends MongoCollection {
	
	private final MongoCollection source;
	
	private JongoAction<Object> preInsert;
	private JongoAction<Object>	preSave;
	
	private JongoAction<Object> postInsert;
	private JongoAction<Object> postSave;
	
	/**
	 * Create the proxy delegate for the given {@link MongoCollection}.<br/>
	 * <B>Beware, you have to pass a non-null object !</B>
	 * @param source Non-null instance of {@link MongoCollection}.
	 */
	public ProxyDelegateMongoCollection(MongoCollection source) {
		super(source.getDBCollection(), null);
		this.source = source;
	}
	
	// Proxy methods, limited to insert(Object) and save(Object) at the moment

	private void preInsert(Object pojo) {
		if(preInsert != null) {
			preInsert.on(pojo);
		}
	}

	private void postInsert(Object pojo) {
		if(postInsert != null) {
			postInsert.on(pojo);
		}
	}
	
	private void preSave(Object pojo) {
		if(preSave != null) {
			preSave.on(pojo);
		}
	}
	
	private void postSave(Object pojo) {
		if(postSave != null) {
			postSave.on(pojo);
		}
	}
	
	public WriteResult insert(Object pojo) {
		preInsert(pojo);
		final WriteResult wr = source.insert(pojo);
		postInsert(pojo);
		return wr;
	}

	public WriteResult save(Object pojo) {
		preSave(pojo);
		final WriteResult wr = source.save(pojo);
		postSave(pojo);
		return wr;
	}
	
	// Self-builders
	
	public ProxyDelegateMongoCollection preInsert(JongoAction<Object> preInsert) {
		this.preInsert = preInsert;
		return this;
	}
	
	public ProxyDelegateMongoCollection preSave(JongoAction<Object> preSave) {
		this.preSave = preSave;
		return this;
	}
	
	public ProxyDelegateMongoCollection postInsert(JongoAction<Object> postInsert) {
		this.postInsert = postInsert;
		return this;
	}
	
	public ProxyDelegateMongoCollection postSave(JongoAction<Object> postSave) {
		this.postSave = postSave;
		return this;
	}
	
	// Accessors
	
	/**
	 * Returns the targeted {@link MongoCollection}.
	 * @return The targeted {@link MongoCollection}.
	 */
	public MongoCollection getSource() {
		return source;
	}
	
	public JongoAction<Object> getPreInsert() {
		return preInsert;
	}

	public JongoAction<Object> getPreSave() {
		return preSave;
	}

	public JongoAction<Object> getPostInsert() {
		return postInsert;
	}

	public JongoAction<Object> getPostSave() {
		return postSave;
	}

	// Delegate methods
	public int hashCode() {
		return source.hashCode();
	}

	public MongoCollection withWriteConcern(WriteConcern concern) {
		return source.withWriteConcern(concern);
	}

	public MongoCollection withReadPreference(ReadPreference readPreference) {
		return source.withReadPreference(readPreference);
	}

	public FindOne findOne(ObjectId id) {
		return source.findOne(id);
	}

	public FindOne findOne() {
		return source.findOne();
	}

	public FindOne findOne(String query) {
		return source.findOne(query);
	}

	public FindOne findOne(String query, Object... parameters) {
		return source.findOne(query, parameters);
	}

	public Find find() {
		return source.find();
	}

	public Find find(String query) {
		return source.find(query);
	}

	public Find find(String query, Object... parameters) {
		return source.find(query, parameters);
	}

	public FindAndModify findAndModify() {
		return source.findAndModify();
	}

	public FindAndModify findAndModify(String query) {
		return source.findAndModify(query);
	}

	public FindAndModify findAndModify(String query, Object... parameters) {
		return source.findAndModify(query, parameters);
	}

	public long count() {
		return source.count();
	}

	public long count(String query) {
		return source.count(query);
	}

	public boolean equals(Object obj) {
		return source.equals(obj);
	}

	public long count(String query, Object... parameters) {
		return source.count(query, parameters);
	}

	public Update update(String query) {
		return source.update(query);
	}

	public Update update(ObjectId id) {
		return source.update(id);
	}

	public Update update(String query, Object... parameters) {
		return source.update(query, parameters);
	}

	public WriteResult insert(String query) {
		return source.insert(query);
	}

	public WriteResult insert(Object... pojos) {
		return source.insert(pojos);
	}

	public WriteResult insert(String query, Object... parameters) {
		return source.insert(query, parameters);
	}

	public WriteResult remove(ObjectId id) {
		return source.remove(id);
	}

	public WriteResult remove() {
		return source.remove();
	}

	public WriteResult remove(String query) {
		return source.remove(query);
	}

	public WriteResult remove(String query, Object... parameters) {
		return source.remove(query, parameters);
	}

	public Distinct distinct(String key) {
		return source.distinct(key);
	}

	public Aggregate aggregate(String pipelineOperator) {
		return source.aggregate(pipelineOperator);
	}

	public Aggregate aggregate(String pipelineOperator, Object... parameters) {
		return source.aggregate(pipelineOperator, parameters);
	}

	public void drop() {
		source.drop();
	}

	public void dropIndex(String keys) {
		source.dropIndex(keys);
	}

	public void dropIndexes() {
		source.dropIndexes();
	}

	public void ensureIndex(String keys) {
		source.ensureIndex(keys);
	}

	public void ensureIndex(String keys, String options) {
		source.ensureIndex(keys, options);
	}

	public String getName() {
		return source.getName();
	}

	public DBCollection getDBCollection() {
		return source.getDBCollection();
	}

	public String toString() {
		return source.toString();
	}
}
