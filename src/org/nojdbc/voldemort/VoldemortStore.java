package org.nojdbc.voldemort;
import java.util.Map;
import java.util.Set;

import org.nojdbc.Collection;
import org.nojdbc.Store;

import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;
import voldemort.client.protocol.admin.AdminClient;
import voldemort.client.protocol.admin.AdminClientConfig;
import voldemort.serialization.ObjectSerializer;
import voldemort.serialization.Serializer;
import voldemort.serialization.StringSerializer;


public class VoldemortStore implements Store {

	StoreClient<String, Map<String, Object>> client; 
	AdminClient admin; 
	String storeName; 
	Serializer<String> keySerializer; 
	Serializer<Map<String, Object>> valueSerializer; 
	public VoldemortStore(String database) {
		 String bootstrapUrl = "tcp://localhost:6666";
		 StoreClientFactory factory = new SocketStoreClientFactory(new ClientConfig().setBootstrapUrls(bootstrapUrl));
		 
		 // create a client that executes operations on a single store
		 client = factory.getStoreClient(database);
		 storeName = database; 
		 admin = new AdminClient(bootstrapUrl, new AdminClientConfig());
		 
		 keySerializer = new StringSerializer(); 
		 valueSerializer = new ObjectSerializer<Map<String, Object>>(); 
		 // TODO: check that the serializers of this stores match those ones ...
	}
	
	@Override
	public Collection getCollection(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getDefaultCollection() {
		return new VoldemortCollection(this); 
	}

	@Override
	public Collection addCollection(String name) {
		throw new RuntimeException("Add collection not supported by voldermort"); 
	}

	@Override
	public Set<String> listCollections() {
		return null; 
// TODO: store -> store ? or to instance ... 
/*		Cluster cluster = admin.getAdminClientCluster();
		List<StoreDefinition> stores = RebalanceUtils.getStoreNameList(cluster, admin);
		List<String> names =  RebalanceUtils.getStoreNames(stores);
		Set<String> set = new HashSet<String>(); 
		set.addAll(names); 
		return set; */ 
	}

	@Override
	public void close() {
		// Nothing special 
	}

}
