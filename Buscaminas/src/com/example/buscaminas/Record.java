package com.example.buscaminas;

import java.util.Date;

public class Record {

	public String nombre;
	public Date fecha;
	public String tiempo;
	public String modo;
	
	public String getNombre(){
		return this.nombre;
	}
	
	public Date getFecha(){
		return this.fecha;
	}
	
	public String getTiempo(){
		return this.tiempo;
	}
	
	public String getModo(){
		return this.modo;
	}
	
	public Record(String nombre, Date fecha, String tiempo, String modo){
		this.nombre = nombre;
		this.fecha = fecha;
		this.tiempo = tiempo;
		this.modo = modo;
	}

}
