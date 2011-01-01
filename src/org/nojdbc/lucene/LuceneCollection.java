package org.nojdbc.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.nojdbc.Collection;
import org.nojdbc.Cursor;
import org.nojdbc.Document;

/**
 * @author Florian Douetteau
 */
public class LuceneCollection implements Collection {
	IndexWriter writer; 
	IndexReader reader; 
	Version version = Version.LUCENE_30;
	Searcher searcher;
	File INDEX_DIR; 
	
	LuceneCollection(File INDEX_DIR) {
		this.INDEX_DIR = INDEX_DIR; 
		try {
			if (! (INDEX_DIR.canRead() && INDEX_DIR.isDirectory())) {
				throw new RuntimeException("Unable to read index directory:" + INDEX_DIR); 
			}
				
	    writer = new IndexWriter(FSDirectory.open(INDEX_DIR), 
	    		new StandardAnalyzer(version), true, IndexWriter.MaxFieldLength.LIMITED);
	    reader = IndexReader.open(FSDirectory.open(INDEX_DIR)); 
	    searcher = new IndexSearcher(reader); 

	    
		} catch (IOException e) {
			throw new RuntimeException(e); 
		}
	}
	
	protected Searcher getSearcher() {
		try {
		if (searcher != null) {
			return searcher; 
		}
		searcher = new IndexSearcher(writer.getReader()); 
		return searcher; 
		} catch (IOException e) {
			throw new RuntimeException(e); 
		}
	}
	
	@Override
	public Document getDocument(String id) {
		try {
			Searcher searcher = getSearcher(); 
			TopDocs docs = searcher.search(new TermQuery(new Term(LuceneStore.idFieldName, id)), 1);
			if (docs.totalHits == 0) {
				return null; 
			}
			org.apache.lucene.document.Document doc = searcher.doc(docs.scoreDocs[0].doc); 
			return new LuceneDocument(doc); 
		} catch (Exception e) {
			throw new RuntimeException(e); 
		}
	}

	@Override
	public Document newDocument(String id) {
		return new LuceneDocument(id); 
	}

	@Override
	public void save(Document doc) {
		try {
			writer.addDocument(((LuceneDocument) doc).doc);
			searcher =  null; 
		} catch (IOException e) {
			throw new RuntimeException(e); 
		}
	}

	@Override
	public Cursor enumerate() {
		// TODO Auto-generated method stub
		return null;
	}

}
