/**
 * 
 */
package org.nojdbc.redis;

import java.util.HashMap;
import java.util.Set;

/**
 * @author Florian Douetteau
 *
 */
public class RedisDocument extends HashMap<String, Object> implements org.nojdbc.Document  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8465569218579733855L;

	transient String id; 
	
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
		put(key, value); 
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Document#get(java.lang.String)
	 */
	@Override
	public Object get(String key) {
		return ((HashMap<String, Object>) this).get(key); 
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Document#getProperties()
	 */
	@Override
	public Set<String> getProperties() {
		return keySet(); 
	}

}
