package com.example.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbopenhelper extends SQLiteOpenHelper{

	
	SQLiteDatabase db;
	public Dbopenhelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		
		database.execSQL(" CREATE TABLE table_prev_login(id INTEGER PRIMARY KEY,username TEXT,phone TEXT)"  
				+"");
		database.execSQL("CREATE TABLE table_contacts(id INTEGER PRIMARY KEY,name TEXT,phone TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS table_prev_login");
		db.execSQL("DROP TABLE IF EXISTS table_contacts");
		onCreate(db);
		
	}
	

}
