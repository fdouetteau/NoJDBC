/**
 * 
 */
package org.nojdbc.hbase;

import java.util.HashMap;
import java.util.Set;

import org.nojdbc.Document;

/**
 * @author Florian Douetteau
 *
 */
public class HBaseDocument extends HashMap<String, Object> implements Document {

	String id; 
	HBaseDocument(String id) {
		this.id = id; 
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
		put(key, value); 
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Document#get(java.lang.String)
	 */
	@Override
	public Object get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Document#getProperties()
	 */
	@Override
	public Set<String> getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

}
