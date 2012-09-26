package kpk.dbaccesswrapper.accessor.entities;


import java.util.ArrayList;
import java.util.List;

import kpk.dbaccesswrapper.accessor.Database;
import kpk.dbaccesswrapper.accessor.SQLiteDatabaseBuilder;
import kpk.dbaccesswrapper.accessor.errors.DatabaseException;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;


public abstract class AbstractObjectModel {

	protected static SQLiteDatabaseBuilder sDatabaseBuilder;
	protected static Database sDatabase;
	public long _id;
	private static String sTAG = AbstractObjectModel.class.getSimpleName();
	public static void setBuilder(SQLiteDatabaseBuilder builder) {
		
		sDatabaseBuilder = builder;
	}
	
	public static void setDatabase(Database database) {
		
		sDatabase = database;
	}
	
	public static Database getDatabase() {
		return sDatabase;
	}
	
	public abstract ArrayList<String> getFields();
	
	public abstract ArrayList<String> getFieldTypes();
	
	public abstract ArrayList<Object> getFieldValues();
	
	public <T extends AbstractObjectModel> void addToDatabase() {
		
		ContentValues values = EntityParser.writeEntityIntoContentValues(this);
		try{
			_id = sDatabase.insert(this.getClass(), values);
		}catch(DatabaseException e) {
			Log.d("Error", e.getMessage());
		}
	}
	
	public List<String> getColumnFields() {
		final List<String> columns = new ArrayList<String>();
		
		for(String field : getFields()) {
			Log.d("Field columns", field);
			
			columns.add(field);
		}
		Log.d("Field columns", Integer.valueOf(columns.size()).toString());
		return columns;
	}
	
	public abstract void inflate(Cursor cursor) throws DatabaseException; 
	
	public boolean update() {
		ContentValues values = EntityParser.writeEntityIntoContentValues(this);
		int affectedRows = sDatabase.update(this.getClass().getSimpleName(), values, "_id=" + this._id, null);
		if(affectedRows > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean delete() {
		try {
			sDatabase.delete(this.getClass(), "_id=" + this._id, null);
			return true;
		}catch(DatabaseException e) {
			return false;
		}
		
	}
	
	public String toString() {
		
		final StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + " ");
		final ArrayList<String> fields = getFields();
		final ArrayList<Object> fieldsValues = getFieldValues();
		for(int i = 0; i < fields.size(); i++) {
			builder.append(fields.get(i) + ":" + fieldsValues.toString() + ", ");
		}
		
		return builder.toString();
	}
}
