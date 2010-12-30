package org.nojdbc.benchmark;

import org.junit.Assert;
import org.nojdbc.Collection;
import org.nojdbc.Document;
import org.nojdbc.Factory;
import org.nojdbc.Factory.StoreException;
import org.nojdbc.Store;

public class Benchmarker {

	public static class Result {
		// Write time in milliseconds; 
		String kind; 
		long writeTimeMS; 
		long readTimeMS;
		@Override
		public String toString() {
			return "Benchmark\n" + 
			"\tKind:" + kind + "\n" + 
			"\t\tWrite:" + writeTimeMS + " ms \n" + 
			"\t\tRead : " + readTimeMS + " ms \n"; 
		}
	}
	public Result simpleBenchmark(String kind, String db, int ndocs) throws StoreException {
		Store store = Factory.getStore(kind, db);
		Collection collection = store.getDefaultCollection(); 
		
		long st1 = System.currentTimeMillis(); 
		for(int i = 0; i < ndocs; i++) {
			Document document = collection.newDocument(Integer.toString(i));
			document.set("k", "aa"); 
			collection.save(document); 
		}
		long st2  = System.currentTimeMillis(); 
		for(int i = 0; i < ndocs; i++) {
			Document document = collection.getDocument(Integer.toString(i)); 
			document.get("k"); 
		}
		long st3 = System.currentTimeMillis(); 
		Result r = new Result();
		r.kind = kind; 
		r.writeTimeMS = st2 - st1; 
		r.readTimeMS = st3  - st2; 
		store.close(); 
		return r; 
	}
	
	public static void main(String[] args) {
		try {
			Benchmarker bench = new Benchmarker(); 
			System.err.println(bench.simpleBenchmark("Lucene", "/tmp/lucene-tmp", 100000).toString()); 
			System.err.println(bench.simpleBenchmark("MongoDB", "db", 100000).toString()); 
			System.err.println(bench.simpleBenchmark("Lucene", "/tmp/lucene-tmp", 100000).toString()); 

		} catch (StoreException e) {
			e.printStackTrace(); 
		}
	}
}
