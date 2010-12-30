/**
 * 
 */
package org.nojdbc.lucene;

import java.io.File;

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
public class LuceneTest {

	
	@Test
	public void simpleConnect() throws StoreException {
		File f = new File("/tmp/lucene-tmp"); 
		f.mkdir(); 
		Store store = Factory.getStore("Lucene", f.getAbsolutePath());
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
