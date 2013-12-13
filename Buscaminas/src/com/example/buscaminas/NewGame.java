package com.example.buscaminas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewGame extends Activity {

	//EditText txtJugador;
		//Button btnFacil, btnInter, btnAvanzado;
		
		protected void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			setContentView(R.layout.newgame);
			
			EditText txtJugador = (EditText)findViewById(R.id.txtJugador);
			Button btnFacil = (Button)findViewById(R.id.btnFacil);
			Button btnInter = (Button)findViewById(R.id.btnIntermedio);
			Button btnAvanzado = (Button)findViewById(R.id.btnAvanzado);
			
			btnFacil.setOnClickListener(new jugar_click());
			btnInter.setOnClickListener(new jugar_click());
			btnAvanzado.setOnClickListener(new jugar_click());
		}		
			/*
			//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, R.array.levels);
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.levels, android.R.layout.simple_spinner_item);
			final Spinner listOfLevels = (Spinner)findViewById(R.id.ListOfLevels);
			listOfLevels.animate();
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			listOfLevels.setAdapter(adapter);
			listOfLevels.setBackgroundColor(Color.GRAY);
			listOfLevels.setOnItemSelectedListener(
			new AdapterView.OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id){
										
				}
				public void onNothingSelected(AdapterView<?> parent) { 
				
				}
			});
		*/
		class jugar_click implements View.OnClickListener{

			@Override
			public void onClick(View v) {
				Button boton = (Button)v;
				Intent intent = null;
				EditText txtJugador = (EditText)findViewById(R.id.txtJugador);
				
				if (txtJugador.getText().toString().equals("")){
					AlertDialog.Builder builder = new AlertDialog.Builder(boton.getContext());
					builder.setMessage("Debe ingresar su nombre")
					.setTitle("Nombre")
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setCancelable(false)
					.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					});
					AlertDialog dialogo = builder.create();
					dialogo.show();
					return;
				}
				
						    
			    Modo modo = null;
				switch (boton.getId()){
					case R.id.btnFacil:
						modo = Modo.Facil;
						break;
					case R.id.btnIntermedio:
						modo = Modo.Intermedio;
						break;
					case R.id.btnAvanzado:
						modo = Modo.Avanzado;
				}
				
				//Bundle jugador = new Bundle();
				//jugador.putString("NOMBRE", txtJugador.getText().toString());
				Game.JUGADOR = txtJugador.getText().toString();
				Game.MODO = modo.toString();
				Game.NUMMINESDEFAULT = modo.mines;
				Game.SIZEX = modo.cols;
				Game.SIZEY = modo.rows;
				
				intent = new Intent(NewGame.this, Game.class);
				//intent.putExtras(jugador);
			    //Iniciamos la nueva actividad
				//Toast.makeText(boton.getContext(), String.valueOf(modo.getColumns()), Toast.LENGTH_SHORT).show();
			    startActivity(intent);
			}
		}
}
