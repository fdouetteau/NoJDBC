/**
 * 
 */
package org.nojdbc.redis;

import java.util.Set;

import org.jredis.JRedis;
import org.jredis.ri.alphazero.JRedisClient;
import org.nojdbc.Collection;
import org.nojdbc.Store;

/**
 * @author Florian Douetteau
 * TODO JRedis client is not thread safe.
 * TODO JRedis client does not implement the 'select' method 
 * TODO Consider using Jedis and JOhm for serialization.
 */
public class RedisStore implements Store {

	JRedis redis; 
	
	public RedisStore(String database) {
		redis = new JRedisClient();
	}
	
	/* (non-Javadoc)
	 * @see org.nojdbc.Store#getCollection(java.lang.String)
	 */
	@Override
	public Collection getCollection(String name) {
		return new RedisCollection(this, name);
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Store#getDefaultCollection()
	 */
	@Override
	public Collection getDefaultCollection() {
		return getCollection("0"); 
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Store#addCollection(java.lang.String)
	 */
	@Override
	public Collection addCollection(String name) {
		return getCollection(name); 
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Store#listCollections()
	 */
	@Override
	public Set<String> listCollections() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Store#close()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
