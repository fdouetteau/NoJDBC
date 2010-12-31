package org.nojdbc.riak;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.nojdbc.Document;

import com.basho.riak.pbc.RiakObject;

/**
 * A RiakDocument abstract a Riak entry where the document is stored as a json object
 * // TODO : dispatch per content type. 
 * @author Florian Douetteau
 *
 */
public class RiakDocument implements Document {

	JSONObject json; 
	
	String id; 
	
	RiakDocument(String id) {
		this.id = id;  
		json = new JSONObject(); 
	}
	RiakDocument(RiakObject object)  {
		try {
			if (object.getValue() == null) {
				json = new JSONObject(); 
			} else {
				json = new JSONObject(object.getValue().toStringUtf8()); 
			}
		} catch (JSONException e) {
			throw new RuntimeException(e); 
		}
	}
	
	@Override
	public String getID() {
		return id; 
	}

	@Override
	public void set(String key, Object value) {
		try {
			json.put(key, value); 
		} catch (JSONException e) {
			throw new RuntimeException(e); 
		}
	}

	@Override
	public Object get(String key) {
		try {
			return json.get(key); 
		} catch (JSONException e) {
			throw new RuntimeException(e); 
		}
	}

	@Override
	public Set<String> getProperties() {
		try {
		HashSet<String> set = new HashSet<String>(); 
		JSONArray a = json.names(); 
		for (int i = 0; i < a.length(); i++) {
			set.add(a.getString(i)); 
		}
		return set; 
		} catch (JSONException e) {
			throw new RuntimeException(e); 
		}
	}

}
