package org.nojdbc.mongodb;

import org.nojdbc.Cursor;
import org.nojdbc.Document;

import com.mongodb.DBCursor;

class MongoDBCursor implements Cursor {

	DBCursor cursor; 
	
	
	public MongoDBCursor(DBCursor cursor) {
		super();
		this.cursor = cursor;
	}

	@Override
	public boolean hasNext() {
		return cursor.hasNext();
	}

	@Override
	public Document next() {
		return new MongoDBDocument(cursor.next()); 
	}

}
