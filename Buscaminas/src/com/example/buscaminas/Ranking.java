package com.example.buscaminas;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Ranking extends Activity {
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ranking);
			
		loadThisOnCreate();
	}
	
	public void loadThisOnCreate(){
		DBBuscamina db = new DBBuscamina(Ranking.this);
		TableLayout tabla = (TableLayout)findViewById(R.id.tabla);
		ArrayList<Record> lista;
		TextView text[];
		TableRow row[];
		ImageView image[];
		
		lista = db.getAllRanking();
		
		if (lista.size() > 0){
			text = new TextView[lista.size()];
			row = new TableRow[lista.size()];
			image = new ImageView[lista.size()];
		
			for(int i=0; i<lista.size(); i++){
				Record r = lista.get(i);
				text[i] = new TextView(Ranking.this);
				image[i] = new ImageView(Ranking.this);
				row[i] = new TableRow(Ranking.this);
				
				image[i].setBackgroundResource(R.drawable.trofeo);
				text[i].setId(i+1);
				text[i].setText(r.getNombre());
			
				row[i].addView(image[i]);
				row[i].addView(text[i]);
				
				tabla.addView(row[i]);
			}
		}
		
		lista.clear();

		db.close();
		
	}
	
}