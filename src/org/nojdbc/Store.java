package org.nojdbc;
import java.util.Set;

public interface Store {

	/**
	 * Retrieve an existing collection. 
	 * @param name
	 * @return The existing collection, or null if it does not exist. 
	 */
	public Collection getCollection(String name);
	
	/**
	 * 
	 * @return A 'default' collection for the store.
	 */
	public Collection getDefaultCollection(); 
	
	/**
	 * Create a new collection. 
	 * @param name
	 * @return The newly created collection. 
	 */
	public Collection addCollection(String name); 
	
	/**
	 * List all existing collections. 
	 */
	public Set<String> listCollections();
	
	/**
	 * Close the handle / any connections etc..
	 */
	public void close(); 
	
}
