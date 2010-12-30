package org.nojdbc.mongodb;

import java.net.UnknownHostException;
import java.util.Set;

import org.nojdbc.Collection;
import org.nojdbc.Store;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class MongoDBStore implements Store {

	Mongo mongo; 
	DB db ; 
	
	static String idKeyName = "_id"; 
	
	public MongoDBStore(String database) throws UnknownHostException {
		mongo = new Mongo(); 
		db = mongo.getDB(database); 
	}
	
	@Override
	public Collection getCollection(String name) {
		return new MongoDBCollection(db.getCollection(name)); 
	}
	
	/**
	 * For mongo DB we take the 
	 * convention of naming the default collection after the database.
	 */
	@Override
	public Collection getDefaultCollection() {
		return addCollection(db.getName()); 
	}

	@Override
	public Collection addCollection(String name) {
		DBCollection coll = db.getCollection(name); 
		if (coll == null) {
			coll = db.createCollection(name, null);
		}
		return new MongoDBCollection(coll);
	}

	@Override
	public Set<String> listCollections() {
		return db.getCollectionNames(); 
	}
	
	@Override
	public void close() {
		mongo.close(); 
	}

}
