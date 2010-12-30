package org.nojdbc.couchdb;

import java.util.List;

import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.nojdbc.Cursor;
import org.nojdbc.Document;

class CouchDBCursor implements Cursor {

	ViewResult<CouchDBDocument> viewResult; 
	List<ValueRow<CouchDBDocument> > rows; 
	int cursor = 0; 
	int size; 
	CouchDBCursor(ViewResult<CouchDBDocument> viewResult) {
		this.viewResult  = viewResult; 
		this.rows = viewResult.getRows();
		this.size = rows.size(); 
	}
	
	@Override
	public boolean hasNext() {
		return cursor < size;
	}

	@Override
	public Document next() {
		cursor++; 
		return rows.get(cursor-1).getValue(); 
	}

}
