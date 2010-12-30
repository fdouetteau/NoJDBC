package org.nojdbc.lucene;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


import org.nojdbc.Collection;
import org.nojdbc.Store;


public class LuceneStore implements Store {
	
	static final String idFieldName = "_id"; 

	File baseDir; 
	
	public LuceneStore(String database) throws Exception {
		baseDir = new File(database); 
		baseDir = baseDir.getAbsoluteFile();
		if (!baseDir.canRead() || !baseDir.canWrite() || !baseDir.isDirectory()) {
			throw new Exception("Unable to read or write or does not exists directory: " + baseDir); 
		}
	}
	
	@Override
	public Collection getCollection(String name) {
		// TODO Recycle collection. 	
		return new LuceneCollection(new File(baseDir, name)); 
	}

	@Override
	public Collection getDefaultCollection() {
		return addCollection("default"); 
	}

	@Override
	public Collection addCollection(String name) {
		File collDir = new File(baseDir, name); 
		if (!collDir.exists()) {
			collDir.mkdir(); 
		}
		return getCollection(name); 
	}

	@Override
	public Set<String> listCollections() {
		Set<String> s = new HashSet<String>(); 
		for(File file : baseDir.listFiles()) {
			if (file.isDirectory()) {
				s.add(file.getName()); 
			}
		}
		return s; 
	}

	@Override
	public void close() {
		// Nothing special until we do some collection recycling ... 
	}

}
