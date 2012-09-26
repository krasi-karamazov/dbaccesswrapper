package kpk.dbaccesswrapper.accessor;

import java.util.ArrayList;
import java.util.List;

import kpk.dbaccesswrapper.accessor.entities.AbstractObjectModel;
import kpk.dbaccesswrapper.accessor.errors.DatabaseException;
import kpk.dbaccesswrapper.accessor.errors.ErrorMessages;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Database {

	private static String sTAG = Database.class.getSimpleName();
	private DBHelper mDBHelper;
	private SQLiteDatabase mDatabase;
	private static Database sInstance;
	private Database(Context context, String name, int version, SQLiteDatabaseBuilder dbBuilder) {
		
		mDBHelper = new DBHelper(context, name, null, version, dbBuilder);
		dbBuilder.setDataBase(this);
	}
	
	public static Database getInstance(Context context, String name, int version, SQLiteDatabaseBuilder dbBuilder) {
		if(sInstance == null) {
			sInstance = new Database(context, name, version, dbBuilder);
		}
		return sInstance;
	}
	
	public synchronized void open() {
		
		if(mDatabase == null) {
			Log.d("MDATABASE", "NULL");
			mDatabase = mDBHelper.getWritableDatabase();
		}
	}
	
	public synchronized void close() throws DatabaseException {
		
		if(mDatabase == null && !mDatabase.isOpen()) {
			throw new DatabaseException(ErrorMessages.DATABASE_NOT_OPEN_ERROR);
		}

		mDatabase.close();
		mDatabase = null;
	}
	
	public synchronized void exeqSQL(String sql) throws DatabaseException {
		
		if(mDatabase == null && !mDatabase.isOpen()) {
			throw new DatabaseException(ErrorMessages.DATABASE_NOT_OPEN_ERROR);
		}
		
		try {
			mDatabase.execSQL(sql);
		}catch(SQLException e) {
			throw new DatabaseException(ErrorMessages.SQL_ERROR);
		}
	}
	
	public synchronized Cursor rawQuery(String sql) throws DatabaseException {
		
		if(mDatabase == null && !mDatabase.isOpen()) {
			
			throw new DatabaseException(ErrorMessages.DATABASE_NOT_OPEN_ERROR);
		}
		
		try {
			
			return mDatabase.rawQuery(sql, null);
			
		}catch(SQLException e) {
			throw new DatabaseException(ErrorMessages.SQL_ERROR);
		}
	}
	
	public synchronized <T extends AbstractObjectModel> Cursor query(Class<T> entityClass, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) throws DatabaseException {
		
		if(mDatabase == null && !mDatabase.isOpen()) {
			
			throw new DatabaseException(ErrorMessages.DATABASE_NOT_OPEN_ERROR);
		}
		
		try {
			final String tableName = entityClass.getSimpleName();
			return mDatabase.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
			
		}catch(SQLException e) {
			throw new DatabaseException(ErrorMessages.SQL_ERROR);
		}
	}
	
	public synchronized <T extends AbstractObjectModel> Cursor query(Class<T> entityClass, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) throws DatabaseException {
		
		if(mDatabase == null && !mDatabase.isOpen()) {
			
			throw new DatabaseException(ErrorMessages.DATABASE_NOT_OPEN_ERROR);
		}
		
		try {
			final String tableName = entityClass.getSimpleName();
			return mDatabase.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy);
			
		}catch(SQLException e) {
			throw new DatabaseException(ErrorMessages.SQL_ERROR);
		}
	}
	
	public synchronized <T extends AbstractObjectModel> Cursor query(boolean distinct, Class<T> entityClass, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)  throws DatabaseException {
		
		if(mDatabase == null && !mDatabase.isOpen()) {
			
			throw new DatabaseException(ErrorMessages.DATABASE_NOT_OPEN_ERROR);
		}
		
		try {
			final String tableName = entityClass.getSimpleName();
			return mDatabase.query(distinct, tableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
			
		}catch(SQLException e) {
			throw new DatabaseException(ErrorMessages.SQL_ERROR);
		}
	}
	
	public synchronized Cursor rawQuery(String sql, String[] selectionArgs) throws DatabaseException {
	
		if(mDatabase == null && !mDatabase.isOpen()) {
			
			throw new DatabaseException(ErrorMessages.DATABASE_NOT_OPEN_ERROR);
		}
		
		try {
			return mDatabase.rawQuery(sql, selectionArgs);
		}catch(SQLException e) {
			throw new DatabaseException(ErrorMessages.SQL_ERROR);
		}
	}
	
	public synchronized <T extends AbstractObjectModel> long insert(Class<T> entityClass, ContentValues values) throws DatabaseException {
		
		if(mDatabase == null && !mDatabase.isOpen()) {
			throw new DatabaseException(ErrorMessages.DATABASE_NOT_OPEN_ERROR);
		}
		final String tableName = entityClass.getSimpleName();
		return mDatabase.insert(tableName, null, values);
	}
	
	public synchronized int update(String table, ContentValues values, String whereClause,
			String[] whereArgs) {
		return mDatabase.update(table, values, whereClause, whereArgs);
	}
	
	public synchronized <T extends AbstractObjectModel> int delete(Class<T> entityClass, String whereClause, String[] whereArgs) throws DatabaseException{
		
		if(mDatabase == null && !mDatabase.isOpen()) {
			
			throw new DatabaseException(ErrorMessages.DATABASE_NOT_OPEN_ERROR);
		}
		final String tableName = entityClass.getSimpleName();
		return mDatabase.delete(tableName, whereClause, whereArgs);
	}
	
	public boolean isOpen() {
		
		if(mDatabase != null && mDatabase.isOpen()) {
			return true;
		}
		return false;
	}
	
	public int getDatabaseVersion() {
		
		if(mDatabase != null && !mDatabase.isOpen()) {
			return mDatabase.getVersion();
		}
		return -1;
	}
	
	public <T extends AbstractObjectModel> List<T> getAllFromTable(Class<T> entityClass) {
		final String tableName = entityClass.getSimpleName();
		final List<T> items = new ArrayList<T>();
		final Cursor cursor;
		try {
			cursor = rawQuery("select * from " + tableName);
			
			while(cursor.moveToNext()) {
				
			}
		}catch(DatabaseException e) {
			Log.e(sTAG, e.getMessage());
		}
		 
		
		return items;
	}
	
	public boolean isDatabaseInUseByThread() {
		if(mDatabase != null && !mDatabase.isOpen()) {
			if(mDatabase.isDbLockedByCurrentThread() || mDatabase.isDbLockedByOtherThreads()) {
				try {
					close();
					return true;
				}catch(DatabaseException e) {
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}
