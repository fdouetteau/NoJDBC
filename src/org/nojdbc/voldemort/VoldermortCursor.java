package org.nojdbc.voldemort;

import java.util.Iterator;

import org.nojdbc.Cursor;
import org.nojdbc.Document;

import voldemort.utils.ByteArray;
import voldemort.utils.Pair;
import voldemort.versioning.Versioned;

public class VoldermortCursor implements Cursor {

	Iterator<Pair<ByteArray, Versioned<byte[]>>> iterator; 
	VoldemortStore store; 
	public VoldermortCursor(VoldemortStore store, Iterator<Pair<ByteArray, Versioned<byte[]>>> iterator) {
		this.iterator = iterator;
		this.store = store; 
	}
	@Override
	public boolean hasNext() {
		return iterator.hasNext(); 
	}

	@Override
	public Document next() {
		Pair<ByteArray, Versioned<byte[]>> val = iterator.next(); 
		return new VoldemortDocument(store.keySerializer.toObject(val.getFirst().get()), store.valueSerializer.toObject(val.getSecond().getValue())); 		
	}

}
