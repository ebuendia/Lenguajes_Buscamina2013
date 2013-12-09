package com.example.buscaminas;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Chronometer;

public class Start extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		
		final Chronometer cr = (Chronometer)findViewById(R.id.cronometro);
		
		
		cr.setBase(SystemClock.elapsedRealtime());
		cr.start();
		cr.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){

			@Override
			public void onChronometerTick(Chronometer chronometer) {
				// TODO Auto-generated method stub
				if (cr.getText().equals("00:03")){
					cr.stop();
					Intent i = new Intent(Start.this,Main.class);
					startActivity(i);
				}
			}
			
		});
	}


}
