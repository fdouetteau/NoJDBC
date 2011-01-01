package org.nojdbc.couchdb;

import java.util.Set;

import org.jcouchdb.db.Database;
import org.nojdbc.Collection;
import org.nojdbc.Store;

/**
 * @author Florian Douetteau
 */
public class CouchDBStore extends Database implements Store {

	public CouchDBStore(String database) {
		super("localhost", database);
		// TODO: find a better way to check that the database exists.
		getStatus(); 
	}
	
	@Override
	public Collection getCollection(String name) {
		return new CouchDBViewCollection(this, name); 
	}
	
	@Override
	public Collection getDefaultCollection() {
		return new CouchDBAllDocsCollection(this); 
	}

	@Override
	public Collection addCollection(String name) {
		throw new RuntimeException("Unable to add a collection dynamically in couchdb"); 
	}

	@Override
	public Set<String> listCollections() {
		throw new RuntimeException("list collections not implemented in couchdb"); 
	}

	@Override
	public void close() {
		// No explicit close in couchdb
	}

}
