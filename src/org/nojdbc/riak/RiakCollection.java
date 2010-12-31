/**
 * 
 */
package org.nojdbc.riak;

import java.io.IOException;

import org.nojdbc.Collection;
import org.nojdbc.Cursor;
import org.nojdbc.Document;
import org.nojdbc.NoJDBCException;

import com.basho.riak.pbc.RiakObject;
import com.google.protobuf.ByteString;

/**
 * @author Florian Douetteau
 *
 */
public class RiakCollection implements Collection {

	String name; 
	RiakStore store; 
	RiakCollection(RiakStore store, String name) {
		this.name = name;
		this.store = store; 
	}
	
	/* (non-Javadoc)
	 * @see org.nojdbc.Collection#getDocument(java.lang.String)
	 */
	@Override
	public Document getDocument(String id) {
		try {
			RiakObject[] objects = store.client.fetch(name, id);
			if (objects.length == 0) return null; 
			return new RiakDocument(objects[0]);
		} catch (IOException e) {
			throw NoJDBCException.wrap(e); 
		}
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Collection#newDocument(java.lang.String)
	 */
	@Override
	public Document newDocument(String id) {
		return new RiakDocument(id); 
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Collection#save(org.nojdbc.Document)
	 */
	@Override
	public void save(Document doc) {
		RiakDocument d = (RiakDocument) doc;
		String content = d.json.toString();
		try {
		store.client.store(new RiakObject(name, d.id, content)); 
		} catch (IOException e) {
			NoJDBCException.wrapAndThrow(e); 
		}
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Collection#enumerate()
	 */
	@Override
	public Cursor enumerate() {	
		try {
		return new RiakCursor(this, store.client.listKeys(ByteString.copyFromUtf8(name))); 
		} catch (IOException e) {
			throw NoJDBCException.wrap(e);
		}
	}
	

}
