/**
 * 
 */
package org.nojdbc.elasticsearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.avro.ipc.Responder;
import org.elasticsearch.action.get.GetField;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.search.SearchHitField;
import org.nojdbc.Document;

/**
 * @author Florian Douetteau
 *
 */
public class ElasticSearchDocument implements Document {

	String id; 
	Map<String, Object> map; 
	
	public ElasticSearchDocument(GetResponse response) {
		id = response.getId();
		map = new HashMap<String, Object>(); 
		for(Map.Entry<String, GetField> e : response.getFields().entrySet()) {
			map.put(e.getKey(), e.getValue().getValues().get(0)); 
		}
	}
	
	public ElasticSearchDocument(String id, Map<String, SearchHitField> m) {
		this.id = id; 
		map = new HashMap<String, Object>(); 
		for(Map.Entry<String, SearchHitField> e : m.entrySet()) {
			map.put(e.getKey(), e.getValue().getValues().get(0)); 
		}
	}
	
	
	public ElasticSearchDocument(String id) {
		this.id = id; 
		this.map = new HashMap<String, Object>(); 
	}
	
	/* (non-Javadoc)
	 * @see org.nojdbc.Document#getID()
	 */
	@Override
	public String getID() {
		return id; 
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Document#set(java.lang.String, java.lang.Object)
	 */
	@Override
	public void set(String key, Object value) {
		map.put(key,value);
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Document#get(java.lang.String)
	 */
	@Override
	public Object get(String key) {
		return map.get(key); 
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Document#getProperties()
	 */
	@Override
	public Set<String> getProperties() {
		return map.keySet(); 
	}

}
