package kpk.dbaccesswrapper.accessor.errors;

@SuppressWarnings("serial")
public class DatabaseException extends Exception {
	
	public DatabaseException(){
		super();
	}
	
	public DatabaseException(String message) {
		super(message);
	}
	
	public DatabaseException(Throwable cause) {
		super(cause);
	}

}
