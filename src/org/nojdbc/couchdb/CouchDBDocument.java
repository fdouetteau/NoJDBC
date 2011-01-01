package org.nojdbc.couchdb;

import java.util.HashMap;
import java.util.Set;

import org.nojdbc.Document;

/**
 * @author Florian Douetteau
 */
public class CouchDBDocument extends HashMap<String, Object> implements Document {

	/**
	 * 
	 */
	private static final long serialVersionUID = 65086652625149576L;

	@Override
	public String getID() {
		return (String) get("_id");
	}

	@Override
	public void set(String key, Object value) {
		((HashMap<String, Object>) this).put(key, value); 
	}

	@Override
	public Object get(String key) {
		return ((HashMap<String, Object>) this).get(key); 
	}

	@Override
	public Set<String> getProperties() {
		return keySet(); 
	}

}
