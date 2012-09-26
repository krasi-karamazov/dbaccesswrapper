package kpk.dbaccesswrapper.accessor;

import kpk.dbaccesswrapper.accessor.errors.DatabaseException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private static String sTAG = DBHelper.class.getSimpleName();
	private SQLiteDatabaseBuilder mDBBuilder;
	public DBHelper(Context context, String name, CursorFactory cursorFactory, int version, SQLiteDatabaseBuilder dbBuilder) {
		super(context, name, cursorFactory, version);
		this.mDBBuilder = dbBuilder;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {

		Object[] tables = mDBBuilder.getTables(); 
		for(int i = 0; i < tables.length; i++) {
			try {
				Log.d("tables[i]", tables[i].toString());
				db.execSQL(mDBBuilder.getCreateSQLStatement(tables[i].toString()));
			}catch(DatabaseException e) {
				Log.e(sTAG, e.getMessage());
			}
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Object[] tables = mDBBuilder.getTables(); 
		for(int i = 0; i < tables.length; i++) {
			try {
				db.execSQL(mDBBuilder.getDropTableSQLStatement(tables[i].toString()));
			}catch(DatabaseException e) {
				Log.e(sTAG, e.getMessage());
			}
			
		}
		
		onCreate(db);
	}

}
