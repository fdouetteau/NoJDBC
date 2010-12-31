/**
 * 
 */
package org.nojdbc.elasticsearch;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.get.GetField;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.xcontent.QueryBuilders;
import org.nojdbc.Collection;
import org.nojdbc.Cursor;
import org.nojdbc.Document;
import org.nojdbc.NoJDBCException;

/**
 * @author Florian Douetteau
 * An Elastic search 'collection' is mapped to a type
 */
public class ElasticSearchCollection implements Collection {

	String name; 
	ElasticSearchStore store; 
	
	ElasticSearchCollection(ElasticSearchStore store, String name) {
		this.name = name; 
		this.store = store;
	}
	
	/* (non-Javadoc)
	 * @see org.nojdbc.Collection#getDocument(java.lang.String)
	 */
	@Override
	public Document getDocument(String id) {
		GetResponse response = store.node.client().prepareGet(store.index, name, id).execute().actionGet();
		if (!response.exists()) {
			return null; 
		}
		return new ElasticSearchDocument(response); 
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Collection#newDocument(java.lang.String)
	 */
	@Override
	public Document newDocument(String id) {
		return new ElasticSearchDocument(id); 
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Collection#save(org.nojdbc.Document)
	 */
	@Override
	public void save(Document doc) {
		ElasticSearchDocument d = (ElasticSearchDocument) doc; 
		IndexResponse res = 
			store.node.client().prepareIndex(store.index, name, d.id)
			.setSource(d.map)
			.execute()
			.actionGet();
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Collection#enumerate()
	 * TODO devise a reasonable enumeration method ... 
	 */
	@Override
	public Cursor enumerate() {
		SearchResponse resp = store.node.client().prepareSearch()
		.setIndices(store.index)
		.setTypes(name)
		.setSearchType(SearchType.QUERY_AND_FETCH)
		.setFrom(0).execute().actionGet(); 
		return new ElasticSearchCursor(resp); 
	}

}
