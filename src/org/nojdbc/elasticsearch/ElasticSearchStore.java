package org.nojdbc.elasticsearch;

import java.util.HashSet;
import java.util.Set;

import org.elasticsearch.action.admin.cluster.state.ClusterStateRequest;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.nojdbc.Collection;
import org.nojdbc.Store;

/**
 * @author Florian Douetteau
 */
public class ElasticSearchStore implements Store {

	Node node; 
	String index; 
	// TODO should node be a singleton ?
	public ElasticSearchStore(String databasename) {
		node = NodeBuilder.nodeBuilder().client(true).build(); 
		this.index = databasename; 
	}
	@Override
	public Collection getCollection(String name) {
		return new ElasticSearchCollection(this, name); 
	}

	@Override
	public Collection getDefaultCollection() {
		return getCollection("default"); 
	}

	// TODO Should we create explicitly index ?
	@Override
	public Collection addCollection(String name) {
		return getCollection(name); 
	}

	/**
	 * List all collections.
	 */
	@Override
	public Set<String> listCollections() {
		ClusterStateResponse res = node.client().admin().cluster().state(new ClusterStateRequest()).actionGet(); 
		Set<String> s = new HashSet<String>(); 
		for(String idx : res.state().getMetaData().concreteAllIndices()) {
			s.add(idx); 
		}
		return s; 
	}

	@Override
	public void close() {
		node.close(); 
	}
	
}
