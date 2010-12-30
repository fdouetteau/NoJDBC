package org.nojdbc;

/**
 * A Cursor that iterates over a collection or part of a collection.
 * @author Florian Douetteau
 *
 */
public interface Cursor {

	/**
	 * Whether a next element is available
	 * @return
	 */
	public boolean hasNext(); 
	
	/**
	 * Retrieve the next document. 
	 * @return
	 */
	public Document next(); 
}
