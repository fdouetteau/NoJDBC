package org.nojdbc.voldemort;

import java.util.Map;

import org.nojdbc.Collection;
import org.nojdbc.Cursor;
import org.nojdbc.Document;

import voldemort.cluster.Cluster;
import voldemort.cluster.Node;
import voldemort.versioning.Versioned;

public class VoldemortCollection implements Collection {

	VoldemortStore store; 
	VoldemortCollection(VoldemortStore store) {
		this.store = store; 
	}
	
	@Override
	public Document getDocument(String id) {
		Versioned<Map<String, Object>> v = store.client.get(id);
		if (v == null ) return null; 
		return new VoldemortDocument(id, v.getValue()); 
	}

	@Override
	public Document newDocument(String id) {
		return new VoldemortDocument(id); 
	}

	@Override
	public void save(Document doc) {
		VoldemortDocument d = (VoldemortDocument) doc; 
		store.client.put(d.id, d.content); 
	}

	@Override
	public Cursor enumerate() {
		Cluster cluster = store.admin.getAdminClientCluster(); 
		for(Node n : cluster.getNodes()) {
			store.admin.fetchEntries(n.getId(), 
					store.storeName, 
					n.getPartitionIds(), null, false); 
		}
		// TODO Auto-generated method stub
		return null;
	}

}
