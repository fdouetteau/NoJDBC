/**
 * 
 */
package org.nojdbc.riak;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.nojdbc.Cursor;
import org.nojdbc.Document;
import org.nojdbc.NoJDBCException;

import com.basho.riak.pbc.KeySource;
import com.google.protobuf.ByteString;

/**
 * @author Florian Douetteau
 *
 */
public class RiakCursor implements Cursor {

	KeySource keySource; 
	RiakCollection collection;  
	RiakCursor(RiakCollection collection, KeySource keySource) {
		this.keySource = keySource; 		
		this.collection = collection;
	}
	/* (non-Javadoc)
	 * @see org.nojdbc.Cursor#hasNext()
	 */
	@Override
	public boolean hasNext() {
		try {
		return keySource.hasNext(); 
		} catch (IOException e) {
			throw NoJDBCException.wrap(e); 
		}
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Cursor#next()
	 * TODO some ByteString<->String conversion could be avoided here.
	 */
	@Override
	public Document next() {
		try {
			ByteString key = keySource.next();
			return collection.getDocument(key.toStringUtf8());
		} catch (IOException e) {
			throw NoJDBCException.wrap(e); 
		}
	}

}
