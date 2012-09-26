package kpk.dbaccesswrapper.accessor.errors;

public class ErrorMessages {
	
	public static final String DATABASE_NOT_OPEN_ERROR = "The Database is not open yet. Call open() first";
	public static final String SQL_ERROR = "Error while executing sql statement. Check your syntax.";
	public static final String CLASS_NOT_FOUND_ERROR = "The selected class cannot be found. Add it with addEntity(Class<T extends AbstractEntity>) first.";
	public static final String ADDED_NULL_CLASS_ERROR = "You are trying to add a null class";
	public static final String CLASS_NOT_ABSTRACT_OBJECT_MODEL_SUBCLASS_ERROR = "The value cannot be added. Does it subclass AbstractEntity?";
	public static final String TABLE_NOT_FOUND_ERROR = "The table does not exist.";
}