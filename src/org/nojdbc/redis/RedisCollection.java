/**
 * 
 */
package org.nojdbc.redis;

import org.jredis.ri.alphazero.support.DefaultCodec;
import org.nojdbc.Collection;
import org.nojdbc.Cursor;
import org.nojdbc.Document;
import org.nojdbc.NoJDBCException;

/**
 * @author Florian Douetteau
 *
 */
public class RedisCollection implements Collection {

	RedisStore store; 
	String name; 
	
	RedisCollection(RedisStore store, String name) {
		this.store = store; 
		this.name = name; 
	}
	
	/* (non-Javadoc)
	 * @see org.nojdbc.Collection#getDocument(java.lang.String)
	 */
	@Override
	public Document getDocument(String id) {
		try {
		RedisDocument doc = (RedisDocument) DefaultCodec.decode(store.redis.get(id));
		doc.id = id; 
		return doc; 
		} catch (Exception e) {
			throw NoJDBCException.wrap(e); 
		}
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Collection#newDocument(java.lang.String)
	 */
	@Override
	public Document newDocument(String id) {
		RedisDocument doc = new RedisDocument(); 
		doc.id = id; 
		return doc; 
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Collection#save(org.nojdbc.Document)
	 */
	@Override
	public void save(Document doc) {
		try {
		store.redis.set(((RedisDocument)doc).id, (RedisDocument) doc); 
		} catch (Exception e) {
			NoJDBCException.wrapAndThrow(e); 
		}
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Collection#enumerate()
	 */
	@Override
	public Cursor enumerate() {
		try {
		return new RedisCursor(this, store.redis.keys().iterator()); 
		} catch (Exception e) {
			throw NoJDBCException.wrap(e); 
		}
	}

}
