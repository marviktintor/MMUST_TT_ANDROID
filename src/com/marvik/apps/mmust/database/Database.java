package com.marvik.apps.mmust.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.marvik.apps.mmust.api.MmustAPI;


public class Database extends SQLiteOpenHelper{

	MmustAPI api;
	
	public Database(Context context) {
		super(context, DbConsts.MMUST_DATABASE, null, DbConsts.MMUST_DATABASE_VERSION);
		// TODO Auto-generated constructor stub
		
		api = new MmustAPI(context);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		for (int i = 0; i < api.createTablesSQL().size(); i++) {
			db.execSQL(api.createTablesSQL().get(i));
			Log.i("Executing SQL : "+i, api.createTablesSQL().get(i));
		}
	}

	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		for (int i = 0; i < api.dropTablesSQL().size(); i++) {
			db.execSQL(api.dropTablesSQL().get(i));
			Log.i("Executing SQL : "+i, api.dropTablesSQL().get(i));
		}
		onCreate(db);
	}
 
	
}
