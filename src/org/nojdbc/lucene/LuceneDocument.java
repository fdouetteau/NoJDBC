package org.nojdbc.lucene;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;

public class LuceneDocument implements org.nojdbc.Document {

	String id; 
	Document doc; 
	
	LuceneDocument(String id) {
		this.id = id; 
		this.doc = new Document(); 
		doc.add(new Field(LuceneStore.idFieldName, id, Field.Store.YES, Field.Index.NOT_ANALYZED)); 
	}
	LuceneDocument(Document document) {
		this.doc = document; 
		id = document.get(LuceneStore.idFieldName); 
	}
	
	@Override
	public String getID() {
		return id; 
	}

	@Override
	public void set(String key, Object value) {
		try {
			if (key.equals(LuceneStore.idFieldName)) {
				throw new RuntimeException("Illegal key name: " + key); 
			}
			ByteArrayOutputStream stream = new ByteArrayOutputStream(); 
			ObjectOutputStream stream2 = new ObjectOutputStream(stream); 
			stream2.writeObject(value); 
			stream2.close(); 
			doc.add(new Field(key, stream.toByteArray(), Field.Store.YES));
		} catch (IOException e) {
			throw new RuntimeException(e); 
		}
	}

	@Override
	public Object get(String key) {
		byte[] bytes = doc.getBinaryValue(key); 
		if (bytes == null) return null; 
		try {
			return new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject(); 
		} catch (Throwable e) {
			throw new RuntimeException(e); 
		} 
	}

	@Override
	public Set<String> getProperties() {
		HashSet<String> props = new HashSet<String>(); 
		for(Fieldable f : doc.getFields()) {
			String n = f.name(); 
			if (n.equals(LuceneStore.idFieldName)) continue; 
			props.add(n); 
		}
		return props; 
	}

}
