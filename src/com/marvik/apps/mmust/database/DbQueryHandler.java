package com.marvik.apps.mmust.database;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DbQueryHandler {
	private Context context;
	public DbQueryHandler(Context context){
		this.setContext(context);
	}
	
	Context getContext() {
		return context;
	}
	void setContext(Context context) {
		this.context = context;
	}
	public String getColumnIndexValue(String table,String columnKey, String key,String resultColumn) {
		// TODO Auto-generated method stub
		
		Database db = new Database(getContext());
		SQLiteDatabase sql = db.getReadableDatabase();
		
		Cursor cursor = sql.query(table,null, columnKey+"='"+key+"'", null, null,null,null);
		
		String rowEntry="Not found";
		
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			rowEntry = cursor.getString(cursor.getColumnIndex(resultColumn));
			
		}
		cursor.close();
		sql.close();
		db.close();
		
		return rowEntry;
		
	}

	public String getColumnIndexValue(String table,String column) {
		// TODO Auto-generated method stub
		Database db = new Database(getContext());
		SQLiteDatabase sql = db.getReadableDatabase();
		
		Cursor cursor = sql.query(table,null, null, null, null,null,null);
		
		String rowEntry="Not found";
		
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			rowEntry = cursor.getString(cursor.getColumnIndex(column));
			
		}
		cursor.close();
		sql.close();
		db.close();
		
		return rowEntry;

	}

	public String [] getDeviceRegisteredEmail() {
		// TODO Auto-generated method stub
		AccountManager mann = (AccountManager)context.getSystemService(Context.ACCOUNT_SERVICE);
		Account [] accounts = mann.getAccounts();
		String [] emails = new String [accounts.length];
		int x = 0;
		for (Account ac : accounts) {
			if (ac.type.contains("com.google")) {
				emails[x] = ac.name;
				x++;
				Log.i("EMAIL", ac.name);
			}
		}
		
		return emails;
	}
}
