package org.nojdbc.couchdb;

import org.jcouchdb.db.Database;
import org.nojdbc.Collection;
import org.nojdbc.Cursor;
import org.nojdbc.Document;

public class CouchDBViewCollection extends CouchDBAllDocsCollection implements Collection {

	String view; 

	
	public CouchDBViewCollection(Database db, String view) {
		super(db);
		this.view = view;
	}

	@Override
	public void save(Document doc) {
		throw new RuntimeException("Unable to save a document to a view"); 
	}

	@Override
	public Cursor enumerate() {
		return new CouchDBCursor(db.queryView(view, CouchDBDocument.class, null, null)); 
	}

}
