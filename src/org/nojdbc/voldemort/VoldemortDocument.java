package org.nojdbc.voldemort;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.nojdbc.Document;

public class VoldemortDocument implements Document {

	String id; 
	Map<String, Object> content;
	
	public VoldemortDocument(String id) {
		this.id = id; 
		content = new HashMap<String, Object>(); 
	}
	
	public VoldemortDocument(String id, Map<String, Object> content) {
		super();
		this.id = id; 
		this.content = content;
	}

	@Override
	public String getID() {
		return id; 
	}

	@Override
	public void set(String key, Object value) {
		content.put(key, value); 
	}

	@Override
	public Object get(String key) {
		return content.get(key); 
	}

	@Override
	public Set<String> getProperties() {
		return content.keySet(); 
	}

}
