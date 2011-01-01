package org.nojdbc.mongodb;

import org.nojdbc.Collection;
import org.nojdbc.Cursor;
import org.nojdbc.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * @author Florian Douetteau
 */
class MongoDBCollection implements Collection {

	DBCollection c; 
	
	public MongoDBCollection(DBCollection c ) {
		this.c = c; 
	}
	
	@Override
	public Document getDocument(String id) {
		DBObject ref = new BasicDBObject(MongoDBStore.idKeyName, id); 
		DBObject result = c.findOne(ref);
		if (result == null) return null; 
		return new MongoDBDocument(result); 
	}

	@Override
	public Document newDocument(String id) {
		return new MongoDBDocument(new BasicDBObject(MongoDBStore.idKeyName, id));
	}

	@Override
	public void save(Document doc) {
		MongoDBDocument d = (MongoDBDocument) doc; 
		c.insert(d.object); 
	}

	@Override
	public Cursor enumerate() {
		DBCursor cursor = c.find();
		return new MongoDBCursor(cursor); 
	}
}
