/**
 * 
 */
package org.nojdbc.elasticsearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.nojdbc.Cursor;
import org.nojdbc.Document;

/**
 * @author Florian Douetteau
 *
 */
public class ElasticSearchCursor implements Cursor {

	SearchResponse resp; 
	int i ; 
	SearchHit[] hits; 
	
	public ElasticSearchCursor(SearchResponse resp) {
			this.resp = resp; 
			hits = resp.getHits().hits();
	}
	/* (non-Javadoc)
	 * @see org.nojdbc.Cursor#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return i < hits.length; 
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Cursor#next()
	 */
	@Override
	public Document next() {
		SearchHit h = hits[i]; 
		i++; 
		return new ElasticSearchDocument(h.getId(), h.getFields()); 
	}

}
