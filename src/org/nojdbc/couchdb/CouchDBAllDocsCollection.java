package org.nojdbc.couchdb;

import org.jcouchdb.db.Database;
import org.nojdbc.Collection;
import org.nojdbc.Cursor;
import org.nojdbc.Document;

public class CouchDBAllDocsCollection implements Collection {

	Database db; 
	public CouchDBAllDocsCollection(Database db) {
		this.db = db; 
	}
	
	@Override
	public Document getDocument(String id) {
		return db.getDocument(CouchDBDocument.class, id);
	}

	@Override
	public Document newDocument(String id) {
		CouchDBDocument d = new CouchDBDocument();
		d.put("_id", id);
		return d; 
	}

	@Override
	public void save(Document doc) {
		db.createOrUpdateDocument(doc); 
	}

	@Override
	public Cursor enumerate() {
		return new CouchDBCursor(db.queryView("_all_docs", CouchDBDocument.class, null, null)); 
	}

}
