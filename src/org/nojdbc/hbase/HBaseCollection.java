package org.nojdbc.hbase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.thrift.generated.Mutation;
import org.apache.hadoop.hbase.thrift.generated.TCell;
import org.apache.hadoop.hbase.thrift.generated.TRowResult;
import org.apache.hadoop.io.serializer.JavaSerialization;
import org.elasticsearch.index.cache.filter.FilterCacheModule;
import org.nojdbc.Collection;
import org.nojdbc.Cursor;
import org.nojdbc.Document;
import org.nojdbc.NoJDBCException;

public class HBaseCollection implements Collection {

	HBaseStore store; 
	byte[] nameBytes; 
	byte[] valueColumnNameBytes; 
	
	HBaseCollection(HBaseStore store, String name) {
		this.store = store; 
		nameBytes = HBaseStore.bytes(name);
		valueColumnNameBytes = HBaseStore.bytes("value"); 
	}
	
	@Override
	public Document getDocument(String id) {
		try {
			List<TCell> cells = store.client.get(nameBytes, HBaseStore.bytes(id), valueColumnNameBytes);
			if (cells.size() == 0) return null; 
			byte[] bytes = cells.get(0).getValue();
			HBaseDocument doc = (HBaseDocument) new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject(); 
			return doc; 
			/*			
 * Initial implementation based on a key<->column mapping. 
 * List<TRowResult> rows = store.client.getRow(nameBytes, HBaseStore.bytes(id)); 
			if (rows.size() == 0) {
				return null; 
			}
			TRowResult row = rows.get(0);
			HBaseDocument d = new HBaseDocument(id); 
			for(Map.Entry<byte[], TCell> entry : row.getColumns().entrySet()) {
				String name = HBaseStore.utf8(entry.getKey()); 
				Object v = entry.getValue().value;
				d.put(name, v); 
			}
			return d; */
		} catch (Exception e) {
			throw NoJDBCException.wrap(e); 
		}
	}

	@Override
	public Document newDocument(String id) {
		return new HBaseDocument(id); 
	}

	@Override
	public void save(Document doc) {
		try {
			HBaseDocument d = (HBaseDocument) doc; 
			ByteArrayOutputStream s = new ByteArrayOutputStream(); 
			ObjectOutputStream ss = new ObjectOutputStream(s); 
			ss.writeObject(d); 
			ArrayList<Mutation> mutations = new ArrayList<Mutation>();
			mutations.add(new Mutation(false, valueColumnNameBytes, s.toByteArray())); 
			store.client.mutateRow(nameBytes, HBaseStore.bytes(d.id), mutations); 
		} catch (Exception e) {
			NoJDBCException.wrapAndThrow(e); 
		}
	}

	@Override
	public Cursor enumerate() {
		// TODO Auto-generated method stub
		return null;
	}

}
