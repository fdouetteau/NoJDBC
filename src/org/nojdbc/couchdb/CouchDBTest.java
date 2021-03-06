/**
 * 
 */
package org.nojdbc.couchdb;

import org.junit.Assert;
import org.junit.Test;
import org.nojdbc.Collection;
import org.nojdbc.Document;
import org.nojdbc.Factory;
import org.nojdbc.Factory.StoreException;
import org.nojdbc.Store;

/**
 * @author Florian Douetteau
 *
 */
public class CouchDBTest {

	@Test
	public void simpleConnect() throws StoreException {
		Store store = Factory.getStore("CouchDB", "db");
		Collection collection = store.getDefaultCollection(); 
		Document document = collection.newDocument("testkey");
		document.set("k", "aa"); 
		collection.save(document); 
		String key = document.getID();
		Assert.assertEquals(key, "testkey"); 
		Document document2 = collection.getDocument(key);
		Assert.assertNotNull(document2); 
		Assert.assertNotNull(document2.get("k")); 
		Assert.assertEquals(document2.get("k"), "aa"); 
	}
}
