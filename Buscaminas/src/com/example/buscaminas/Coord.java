package com.example.buscaminas;

import java.util.ArrayList;
import java.util.Iterator;


public class Coord {

	private int x;
	private int y;
	
	public Coord(){
		this(0,0);
	}
	
	public Coord(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	public Coord(int x){
		this(x,x);
	}
	public Coord(Coord c){
		this(c.getX(), c.getY());
	}
	
	public void setX(int x){
		this.x=x;
	}
	public void setY(int y){
		this.y=y;
	}	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setCoord(Coord c){
		setX(c.x);
		setY(c.y);
	}
	
	@Override
	public boolean equals(Object o){
		Coord a = (Coord)o;
		
		return ( (this.getX()==a.getX()) && (this.getY()==a.getY()) ) ? true : false;
	}
	
	public boolean isGood(){
		return ( (x<1)||(x>8)||(y<1)||(y>8) ) ? false : true;
	}
	
	public boolean isAdyacent(Coord pos){
		boolean res = false;
		int difx = 0;
		int dify = 0;
		
		if(this.isGood()){
			if( this.equals(pos) == false ){
				difx = this.getX() - pos.getX();
				dify = this.getY() - pos.getY();
				
				if( (difx>=-1) && (difx<=1) )
					if( (dify>=-1) && (dify<=1) )
						res=true;
			}
		}
		return res;
	}
	
	public boolean isAdyacent(ArrayList<Coord> list){
		boolean res = false;
		Iterator<Coord> iterator = list.iterator();
		while(iterator.hasNext())
			if(isAdyacent(iterator.next())) return true;
		return res;
	}
	
	public boolean isInList(ArrayList<Coord> list){
		boolean res=false;
		Iterator<Coord> iterator = list.iterator();
		while(iterator.hasNext())
			if(iterator.next().equals(this)) return true;
		return res;
	}
}
