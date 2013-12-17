package com.example.buscaminas;

import java.util.ArrayList;
import java.util.Date;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBBuscamina extends SQLiteOpenHelper {
	public static final String DB_NAME = "buscamina.db";
	public static final int DB_VERSION = 1;
	
	public DBBuscamina(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		final String sql = "CREATE TABLE ranking(id INTEGER PRIMARY KEY AUTOINCREMENT,jugador TEXT NOT NULL ,fecha TIMESTAMP DEFAULT current_timestamp,tiempo TEXT NOT NULL ,modo TEXT NOT NULL);";
		arg0.execSQL(sql);
		addRecords();
	}
	
	public long addRanking(String jugador, String tiempo, String modo){
		ContentValues values = new ContentValues();
		values.put("jugador", jugador);
		values.put("tiempo", tiempo);
		values.put("modo", modo);
		
		SQLiteDatabase db = this.getWritableDatabase();
		long result = db.insert("ranking", null, values);
		
		return result;
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	public void addRecords(){
		addRanking("Jose", "05:00", "Facil");
		addRanking("Henry", "05:00", "Intermedio");
		addRanking("Ivan", "05:00", "Intermedio");
		addRanking("Wilson", "05:00", "Facil");
		addRanking("Jose", "05:00", "Avanzado");
		addRanking("Ivan", "05:00", "Avanzado");
	}
	
	public ArrayList<Record> getAllRanking(){
		ArrayList<Record> array = new ArrayList<Record>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select jugador, (strftime('%s', fecha)*1000), tiempo, modo from ranking where modo=? order by tiempo;", new String[]{"Facil"} );
		
		while(cursor.moveToNext()){
			String nombre = cursor.getString(0);
			long fecha = cursor.getLong(1);
			String tiempo = cursor.getString(2);
			String modo = cursor.getString(3);
			
			Record r = new Record(nombre, new Date(fecha),tiempo, modo);
			array.add(r);
		}
		
		cursor.close();
		db.close();
		
		return array;
	}

}
