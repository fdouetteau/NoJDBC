/**
 * 
 */
package org.nojdbc;

/**
 * @author Florian Douetteau
 *
 */
public class NoJDBCException extends RuntimeException{

	public NoJDBCException(String message) {
		super(message);
	}
	
	public NoJDBCException(Throwable cause) {
		super(cause);
	}
	public static NoJDBCException wrap(Throwable t) {
		return new NoJDBCException(t); 
	}
	public static void wrapAndThrow(Throwable t) {
		throw new NoJDBCException(t);
	}
}
