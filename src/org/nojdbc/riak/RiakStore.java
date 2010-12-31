/**
 * 
 */
package org.nojdbc.riak;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.nojdbc.Collection;
import org.nojdbc.NoJDBCException;
import org.nojdbc.Store;

import com.basho.riak.pbc.RiakClient;
import com.google.protobuf.ByteString;

/**
 * @author Florian Douetteau
 *
 */
public class RiakStore implements Store {

	RiakClient client; 
	
	public RiakStore(String databasename) {
		try {
		client = new RiakClient(databasename); 
		} catch (IOException e) {
			NoJDBCException.wrapAndThrow(e); 
		}
	}
	
	
	/* (non-Javadoc)
	 * @see org.nojdbc.Store#getCollection(java.lang.String)
	 *  TODO  check that the collection exists.
	 */
	@Override
	public Collection getCollection(String name) {
		return new RiakCollection(this, name);
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Store#getDefaultCollection()
	 */
	@Override
	public Collection getDefaultCollection() {
		return getCollection("default"); 
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
		try {
		HashSet<String> s = new HashSet<String>(); 
		for(ByteString ss : client.listBuckets()) {
			s.add(ss.toStringUtf8()); 
		}
			return s;
		} catch (IOException e) {
			throw NoJDBCException.wrap(e); 
		}
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Store#close()
	 */
	@Override
	public void close() {
		
	}

}
