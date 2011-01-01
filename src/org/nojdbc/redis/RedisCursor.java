/**
 * 
 */
package org.nojdbc.redis;

import java.util.Iterator;

import org.nojdbc.Cursor;
import org.nojdbc.Document;

/**
 * @author Florian Douetteau
 *
 */
public class RedisCursor implements Cursor {

	RedisCollection collection; 
	Iterator<String> keys; 
	RedisCursor(RedisCollection collection, Iterator<String> keys) {
		this.collection = collection; 
		this.keys = keys; 
	}
	
	/* (non-Javadoc)
	 * @see org.nojdbc.Cursor#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return keys.hasNext(); 
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Cursor#next()
	 */
	@Override
	public Document next() {
		String key = keys.next(); 
		return collection.getDocument(key); 
	}

}
