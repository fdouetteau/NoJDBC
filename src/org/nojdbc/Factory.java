package org.nojdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


/**
 * Factory for creating an handle to a store.
 * @author Florian Douetteau
 * TODO add a method for creating a new store: createStore(storeKind, databaseName)
 * TODO add a method for starting a local instance startLocalInstance(storeKind)
 * TODO add a method for starting a remote instance sartRemoteInstance(storeKind, host) 
 */
public class Factory {

	static public class StoreException extends Exception {

		StoreException(Throwable t) {
			super(t); 
		}
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
	}
	
	/**
	 * Create a store handle
	 * @param name Name of the store.
	 * @param databasename Name of the database
	 * @return
	 */
	public static Store getStore(String storeKind, String databasename) throws StoreException {
		String packageName = "org.nojdbc." + storeKind.toLowerCase(); 
		String className = storeKind + "Store"; 
		String fullClassName = packageName + "." + className; 
		
		try {
		Class<?> driverClass = Class.forName(fullClassName); 
		
		Constructor<?> constructor = 
			driverClass.getConstructor(String.class);
		
		Store store = (Store) constructor.newInstance(databasename);
		
		return store;
		} catch (Throwable t) {
			if (t instanceof InvocationTargetException) {
				t = t.getCause(); 
			}
			throw new StoreException(t); 
		}
	}
}
