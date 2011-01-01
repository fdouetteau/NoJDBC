package org.nojdbc.mongodb;

import java.util.Set;

import org.nojdbc.Document;

import com.mongodb.DBObject;

/**
 * @author Florian Douetteau
 */
class MongoDBDocument implements Document {

	DBObject object; 
	
	MongoDBDocument(DBObject object) {
		if (object == null) {
			throw new NullPointerException(); 
		}
		this.object = object; 
	}
	
	@Override
	public String getID() {
		return (String) object.get(MongoDBStore.idKeyName);
	}

	@Override
	public void set(String key, Object value) {
		object.put(key,value); 
	}

	@Override
	public Object get(String key) {
		return  object.get(key); 
	}
	
	@Override
	public Set<String> getProperties() {
		return object.keySet();
	}

}
