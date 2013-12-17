package com.example.buscaminas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
        
        Button btnNewGame = (Button)findViewById(R.id.btnNewGame);
    	Button btnRanking = (Button)findViewById(R.id.btnRanking);
    	Button btnAbout = (Button)findViewById(R.id.btnAbout);
        
        btnNewGame.setOnClickListener(new boton_click());
        btnRanking.setOnClickListener(new boton_click());
        btnAbout.setOnClickListener(new boton_click());
        
        //crearDB();
	}
	
	class boton_click implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Button b = (Button)v;
			Intent i = null;
			
			switch(b.getId()){
				case R.id.btnNewGame:
					i = new Intent(Main.this, NewGame.class);
					break;
				case R.id.btnRanking:
					i = new Intent(Main.this, Ranking.class);
					break;
				case R.id.btnAbout:
					i = new Intent(Main.this, About.class);
					break;
			}
			
			startActivity(i);
		}
		
	}

}
