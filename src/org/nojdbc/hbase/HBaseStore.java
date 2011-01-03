package org.nojdbc.hbase;
import java.io.UnsupportedEncodingException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.hbase.thrift.generated.ColumnDescriptor;
import org.apache.hadoop.hbase.thrift.generated.Hbase;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.nojdbc.Collection;
import org.nojdbc.NoJDBCException;
import org.nojdbc.Store;

/**
 * 
 */

/**
 * @author Florian Douetteau
 *
 */
public class HBaseStore implements Store {

	TTransport transport; 
	Hbase.Client client; 
	static CharsetDecoder decoder = null;
	
	static {
	    decoder = Charset.forName("UTF-8").newDecoder();
	}

	
	public HBaseStore(String database) {
		String[] ss = database.split(":"); 
		String host = ss[0]; 
		int port = Integer.parseInt(ss.length > 1 ? ss[1] : "9090");  
		transport = new TSocket(host, port);
		TProtocol protocol = new TBinaryProtocol(transport, true, true);
		client = new Hbase.Client(protocol);

		try {
			transport.open(); 
		} catch (TTransportException e) {
			NoJDBCException.wrapAndThrow(e); 
		}
	}
	
	/* (non-Javadoc)
	 * @see org.nojdbc.Store#getCollection(java.lang.String)
	 */
	@Override
	public Collection getCollection(String name) {
		return new HBaseCollection(this, name); 
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Store#getDefaultCollection()
	 */
	@Override
	public Collection getDefaultCollection() {
		return addCollection("default"); 
	}

	// Helper to translate strings to UTF8 bytes
	  static  byte[] bytes(String s) {
	    try {
	      return s.getBytes("UTF-8");
	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	      return null;
	    }
	  }
	  
	  // Helper to translate byte[]'s to UTF8 strings
	  static String utf8(byte[] buf) {
		  try {
			  return decoder.decode(ByteBuffer.wrap(buf)).toString();
		  } catch (CharacterCodingException e) {
			  throw new NoJDBCException("Invalid UTF8"); 
		  }
	  }

	
	/* (non-Javadoc)
	 * @see org.nojdbc.Store#addCollection(java.lang.String)
	 */
	@Override
	public Collection addCollection(String name) {
		if (listCollections().contains(name)) {
			return getCollection(name); 
		}
		ArrayList<ColumnDescriptor> columns = new ArrayList<ColumnDescriptor>();
		ColumnDescriptor col = null;
		col = new ColumnDescriptor();
		col.name = bytes("entry:");
		col.maxVersions = 10;
		columns.add(col);
		col = new ColumnDescriptor();
		col.name = bytes("unused:");
		columns.add(col);

		try {
			client.createTable(bytes(name), columns);
		} catch (Exception ae) {
			throw NoJDBCException.wrap(ae); 
		}
		return getCollection(name); 
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Store#listCollections()
	 */
	@Override
	public Set<String> listCollections() {
		try {
			HashSet<String> set = new HashSet<String>();
			 for (byte[] name : client.getTableNames()) {
				 set.add(utf8(name)); 
			 }
			return set;
		} catch (Exception e) {
			throw NoJDBCException.wrap(e); 
		}
	}

	/* (non-Javadoc)
	 * @see org.nojdbc.Store#close()
	 */
	@Override
	public void close() {
		transport.close(); 
	}

}
