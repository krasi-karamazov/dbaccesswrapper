package kpk.dbaccesswrapper.accessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import kpk.dbaccesswrapper.accessor.entities.AbstractObjectModel;
import kpk.dbaccesswrapper.accessor.entities.EntityParser;
import kpk.dbaccesswrapper.accessor.errors.DatabaseException;
import kpk.dbaccesswrapper.accessor.errors.ErrorMessages;
import kpk.dbaccesswrapper.accessor.utils.EntityFieldNameConverter;

import android.util.Log;


@SuppressWarnings("unused")

public class SQLiteDatabaseBuilder {

	private static String sTAG = SQLiteDatabaseBuilder.class.getSimpleName();
	private Database mDatabase;
	private ArrayList<String> mTables = new ArrayList<String>();
	private static final String PRIMARY_KEY_STRING = "(_id integer not null primary key ";
	private static final String CREATE_TABLE_STRING = "CREATE TABLE";
	private static final String DROP_TABLE_STRING = "DROP TABLE IF EXISTS";
	private static SQLiteDatabaseBuilder sInstance;
	private ArrayList<ArrayList<String>> mClassFields = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> mClassFieldsTypes = new ArrayList<ArrayList<String>>();
	
	public static SQLiteDatabaseBuilder getInstance() {
		if(sInstance == null) {
			sInstance = new SQLiteDatabaseBuilder();
		}
		return sInstance;
	}
	
	private SQLiteDatabaseBuilder() {
		
	}
	
	public void setDataBase(Database db) {
		this.mDatabase = db;
	}
	
	public Database getDatabase() {
		return this.mDatabase;
	}
	
	public Object[] getTables() {
		
		Object[] namesSet = this.mTables.toArray();
		
		return namesSet;
	}
	
	public int getTableCount() {
		
		return mTables.size();
		
	}
	
	public <T extends AbstractObjectModel> boolean createTableBasedOnClass(Class<T> mClass)
	{
		try{
			this.mTables.add(mClass.getSimpleName());
			mDatabase.exeqSQL(getCreateSQLStatement(mClass.getSimpleName()));
			return true;
		}catch(DatabaseException e) {
			return false;
		}
	}
	
	public <T extends AbstractObjectModel> boolean addEntity(String className, ArrayList<String> fieldsToInsert, ArrayList<String> fieldTypes) {
		
		try {
			
			this.mTables.add(className);
			mClassFields.add(fieldsToInsert);
			mClassFieldsTypes.add(fieldTypes);
			return true;
			
		}catch(NullPointerException e) {
			
			return false;
			
		}catch(IllegalArgumentException e) {
			
			return false;
			
		}catch(ClassCastException e) {
			
			return false;
			
		}
		
	}
	
	public <T extends AbstractObjectModel> String getCreateSQLStatement(String tableName) throws DatabaseException {

		if(!mTables.contains(tableName)) {
			
			throw new DatabaseException(ErrorMessages.CLASS_NOT_FOUND_ERROR);
		}
		
		final StringBuilder statementBuilder = new StringBuilder();
		statementBuilder.append(CREATE_TABLE_STRING + " " + tableName + PRIMARY_KEY_STRING);
		String tableClass = (String) mTables.get(mTables.indexOf(tableName));
		
		
		ArrayList<String> classFields = mClassFields.get(mTables.indexOf(tableName));
		ArrayList<String> classFieldsTypes = mClassFieldsTypes.get(mTables.indexOf(tableName));
		
		
		for(int i = 0; i < classFields.size(); i++) {
			String fieldName = getSQLStringFromJavaVariable(classFields.get(i));
			String fieldType = EntityFieldNameConverter.getSQLiteTypeString(classFieldsTypes.get(i));
			statementBuilder.append(", " + fieldName + " " + fieldType);
		}
		
		statementBuilder.append(")");
		
		return statementBuilder.toString();
	}
	
	public String getDropTableSQLStatement(String table) throws DatabaseException {
		
		return DROP_TABLE_STRING + " " + table;
	}
	
	private String getSQLStringFromJavaVariable(String fieldName) {
		
		String result = EntityFieldNameConverter.fromCamelCase(fieldName);
		return result;
	}
}
