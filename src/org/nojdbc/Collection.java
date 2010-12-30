package org.nojdbc;

/**
 * A collection inside a store 
 * @author Florian Douetteau
 *
 */
public interface Collection {

	
	/**
	 * Retrieve a document
	 * @param id The unique id of the document in the collection. 
	 * @return The document, or null if the documents does not exists
	 */
	public Document getDocument(String id);
	
	/**
	 * Create a new document in this collection. 
	 * This document is not available until 'saved' 
	 * @param id  The unique id for the new document. 
	 * @return The newly created document
	 */
	public Document newDocument(String id); 
	
	/**
	 * Save a document. 
	 * @param doc
	 */
	public void save(Document doc); 
	
	/**
	 * Create a cursor that fully enumerates the collection. 
	 * @return 
	 */
	public Cursor enumerate(); 
	
}
