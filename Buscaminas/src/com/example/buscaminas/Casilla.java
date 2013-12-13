package com.example.buscaminas;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;


public class Casilla extends ImageView {
	
	public static final boolean HIDDEN=true;
	public static final boolean SHOWN=false;
	public static final boolean MINE=true;
	public static final boolean NOMINE=false;
	public static final boolean FLAG=true;
	public static final boolean NOFLAG=false;
	public static final int BADFLAGGED = 9;
	public static final int NOTFLAGGED = 10;
	
	
	public static int count = 0;
	//private Context context;
	private Coord pos;
	private boolean state;
	private boolean mine;
	private boolean flag;
	private boolean bad_flagged = false;
	private int num_mines = 0;
	
	public Casilla(Context context, int x, int y){
		super(context);
		
		this.pos = new Coord(x,y);
		//double random = Math.random();
		
		this.setBackgroundResource(R.drawable.free);
		this.setTag(new Integer(R.drawable.free).intValue());
		//this.setOnClickListener(new casilla_Click());
		//this.setOnLongClickListener(new casilla_LongClick());
	}
	
	public void setPosition(int x, int y){
		this.pos.setX(x);
		this.pos.setY(y);
	}
	
	public void setToMine(){
		this.mine = true;
	}
	
	public void setToFlag(){
		this.flag = true;
	}
	
	public void removeFlag(){
		this.flag = false;
	}
	
	public void setToBadFlagged(){
		bad_flagged = true;
	}
	
	public void setToNotBadFlagged(){
		bad_flagged = false;
	}
	
	public Coord getPosition(){
		return this.pos;
	}

	public boolean getState(){
		return this.state;
	}
	
	public boolean hasMine(){
		return this.mine;
	}
	
	public boolean hasFlag(){
		//if (this.getback)
		return this.flag;
	}
	
	public boolean isBadFlagged(){
		return bad_flagged;
	}
	
	public int getImageResource(ImageView v) {
	    return (Integer) v.getTag();
	}
	
	public void setNumMines(int value){
		num_mines=value;
	}
	
	public void addMineAround(){
		num_mines++;
	}
	
	public void setToShown(){
		state=SHOWN;
	}
	
	public int getNumMines(){
		return num_mines;
	}
	
	public void setToNotFlag(){
		flag=NOFLAG;
	}
	
	public void setStatus(int status){
		num_mines=status;
	}
	
	
	
	class casilla_LongClick implements View.OnLongClickListener{

		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			ImageView boton = (ImageView)v;
			int image = new Integer((Integer) boton.getTag()).intValue();
			/*Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.flag);
			boton.setImageBitmap(image);
			boton.setMinimumWidth(image.getWidth());  
			boton.setMinimumHeight(image.getHeight());
			*/
			if ( image == R.drawable.flag )
				boton.setImageResource(R.drawable.free);
			else
				boton.setImageResource(R.drawable.flag);
			
			return true;
		}
		
	}

}
