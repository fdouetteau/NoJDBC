package org.nojdbc;

import java.util.Set;

/**
 * A document in a collection
 * @author Florian Douetteau 
 * TODO add support for set(..Object), Object get()
 */
public interface Document {

	/**
	 * Get the identifier of the document in the collection 
	 * @return
	 */
	public String getID(); 
	
	/**
	 * Set a string property 
	 * @param key Name for the property 
	 * @param value Value for the property 
	 * @return
	 */
	public void set(String key, Object value); 
	
	/**
	 * Get a string property 
	 * @param key Name of the property 
	 * @return the value of the property or null if the property is not set. 
	 */
	public Object get(String key); 
	
	
	/**
	 * @return a list of the properties contained in this Document
	 */
	public Set<String> getProperties(); 
	
}
