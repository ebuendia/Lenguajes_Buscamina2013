package com.example.buscaminas;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;

public class Game extends Activity implements OnClickListener,OnLongClickListener{
	Casilla casillas[][];
	Date start;
	
	public static final int CLICK = 1;
	public static final int LONGCLICK = 2;
	
	public static final String msg_won = "!!!FELICIDADES!!!\nHas Ganado la partida ";
	public static final String msg_explod = "Fin del Juego!";
	public static final String msg_wrong = "Fin del Juego!\n";
	public static final String msg_start = "Click largo para abrir la casilla!";
	
	private String messageClick;
	
	public static String JUGADOR;
	public static String MODO;
	public static int NUMMINESDEFAULT;
	public static int SIZEX ;
	public static int SIZEY ;
	
	private int mines = 0;
	private int flags = 0;
	private Chronometer cr;
	private ImageView lblEstado;
	//TextView lblOpciones;
	//ImageView lblEstado;
	//LinearLayout tablero;
	//final TextClock lblTime = (TextClock)findViewById(R.id.lblTime);
	//Malla malla;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		
		TextView lblOpciones = (TextView)findViewById(R.id.lblOpciones);
		lblEstado = (ImageView)findViewById(R.id.lblEstado);
		GridLayout tablero = (GridLayout)findViewById(R.id.tablero);
		Bundle bundle = this.getIntent().getExtras();
		
		/*Malla malla = new Malla(tablero, 
							bundle.getInt("rows"), 
							bundle.getInt("columns"));
		*/
		//modo = bundle.getString("NOMBRE");
		this.casillas = new Casilla[SIZEX][SIZEY];
		lblEstado.setBackgroundResource(R.drawable.icon_estado1);
		tablero.setRowCount(SIZEX);
		tablero.setColumnCount(SIZEY);
		generarCasillas(tablero,SIZEX,SIZEY);
		inicializarEventos();
		startTiempo();
		initBoard(NUMMINESDEFAULT);
	}
	
	private void startTiempo(){
		cr = (Chronometer)findViewById(R.id.cronometer);
		
		cr.setBase(SystemClock.elapsedRealtime());
		cr.stop();
		
	}
	
	public void initBoard(int nummines){
		  
		start = new Date();
		//Establishing mines randomly
		int ramdx,ramdy;
		Random randomGenerator = new Random();
		while(mines<nummines){
			ramdx = randomGenerator.nextInt(SIZEX); if(ramdx==0) ramdx=1;
			ramdy = randomGenerator.nextInt(SIZEY); if(ramdy==0) ramdy=1;
			if(casillas[ramdx-1][ramdy-1].hasMine()==false){
				casillas[ramdx-1][ramdy-1].setToMine();
				mines++;
			}
		}
		
		//Establish number of mines around
		for (int x=0;x<SIZEX;x++){
			for (int y=0;y<SIZEY;y++){
				if(casillas[x][y].hasMine()){
					casillas[x][y].setNumMines(11);
				}
				else{
					setNumMinesAround(casillas[x][y]);
				}
			}
		}
	}
	
	public Casilla getBox(Coord pos){
		if((pos.getX()<1)||(pos.getY()<1)||(pos.getX()>SIZEX)||(pos.getY()>SIZEY)) return null;
		else return casillas[pos.getX()-1][pos.getY()-1];
	}
	
	public ArrayList<Coord> getBoxesAround(Coord pos){//gets an array with the positions Around the box
		ArrayList<Coord> res = new ArrayList<Coord>();
		if((getBox(new Coord(pos.getX()-1,pos.getY()+1)))!=null) res.add(new Coord(pos.getX()-1,pos.getY()+1));
		if((getBox(new Coord(pos.getX(),pos.getY()+1)))!=null) res.add(new Coord(pos.getX(),pos.getY()+1));
		if((getBox(new Coord(pos.getX()+1,pos.getY()+1)))!=null) res.add(new Coord(pos.getX()+1,pos.getY()+1));
		if((getBox(new Coord(pos.getX()-1,pos.getY())))!=null) res.add(new Coord(pos.getX()-1,pos.getY()));
		if((getBox(new Coord(pos.getX()+1,pos.getY())))!=null) res.add(new Coord(pos.getX()+1,pos.getY()));
		if((getBox(new Coord(pos.getX()-1,pos.getY()-1)))!=null) res.add(new Coord(pos.getX()-1,pos.getY()-1));
		if((getBox(new Coord(pos.getX(),pos.getY()-1)))!=null) res.add(new Coord(pos.getX(),pos.getY()-1));
		if((getBox(new Coord(pos.getX()+1,pos.getY()-1)))!=null) res.add(new Coord(pos.getX()+1,pos.getY()-1));
		return res;	
	}
	
	public void setNumMinesAround(Casilla box){//set the numbers of mines around for every box in the board	
		ArrayList<Coord> around = getBoxesAround(box.getPosition());
		Iterator<Coord> iterator = around.iterator();
		Coord i = new Coord();
		while (iterator.hasNext()){
			i = iterator.next();
			if(getBox(i).hasMine())
				box.addMineAround();
		}
	}
	
	public int numFlagsAround(Casilla box){//returns the number of flags around a box
		int res = 0;
		ArrayList<Coord> around = getBoxesAround(box.getPosition());
		Iterator<Coord> iterator = around.iterator();
		Coord i = new Coord();
		while (iterator.hasNext()){
			i = iterator.next();
			if(getBox(i).hasFlag())
				res++;
		}
		return res;
	}
	
	public ImageView getView(Coord pos){
		if((pos.getX()<1)||(pos.getY()<1)||(pos.getX()>SIZEX)||(pos.getY()>SIZEY)) return null;
		else return casillas[pos.getX()-1][pos.getY()-1];
	}
	
	private void generarCasillas(GridLayout l, int rows, int cols){
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		
		int count=0;
		for(int i = 0; i < rows; i++){
			for (int j = 0; j < cols; j++){
				casillas[i][j] = new Casilla(Game.this, i+1, j+1);
				casillas[i][j].setId(++count);
				casillas[i][j].setBackgroundResource(R.drawable.free);
				if (MODO != "Avanzado")
					casillas[i][j].setLayoutParams(new ViewGroup.LayoutParams(width/SIZEX, width/SIZEX));
				else
					casillas[i][j].setLayoutParams(new ViewGroup.LayoutParams(width/SIZEY, height/SIZEY));
				l.addView(casillas[i][j]);
			}
		}
	}
	
	/*
	private void loadThisOnCreate(){
		setContentView(R.layout.game);
		
		Bundle bundle = this.getIntent().getExtras();
		//nameLblOut.setText("Playing: " + bundle.getString("NOMBRE"));
		genTable(6,4);
	}
	*/
	/*flag
	private void genTable(int numRows, int numColumns){
		final GridView table = (GridView)findViewById(R.id.table);
		int index = numRows*numColumns;
		table.setNumColumns(numColumns);
		table.setAdapter(new CellAdapter(this, index));
	}
	*/
	public String click(Coord pos, int type){
		
		String msg = "";
		
		if (type==this.LONGCLICK){//Procedure for a SORT CLICK
			if(getBox(pos)!=null){
				
				if(getBox(pos).isShown()){//if box is already shown, try to clean around
					msg=clean(pos);
				}
				else{//if box is hidden, flag it or remove the flag
					if(getBox(pos).hasFlag()){//if box was flagged, remove the flag
						flags--;
						getBox(pos).removeFlag();
						getBox(pos).setImageResource(R.drawable.free);
					}
					else{
						if(flags<=mines){//if still positions to put flags
							if(noBoxIsShown()) msg = msg_start;
							flags++;
							getBox(pos).setToFlag();
							getBox(pos).setImageResource(R.drawable.flag);
						}
					}
				}
			}
		}
		if(type==this.CLICK){//Procedure for a LONG CLICK
			
			if(getBox(pos).hasMine()){//if box is a mine -> Game Over!
				lblEstado.setBackgroundResource(R.drawable.icon_lose);
				msg = msg_explod;
				getBox(pos).setStatus(Casilla.NOTFLAGGED);
				uncover(pos);
				cr.stop();
				deleteListeners();
			}
			else{
				uncover(pos);
			}
		}
		//check for finish (if there is no more boxes to open or to flag)
		if (isFinished())
			if((msg.compareTo("")==0)||(msg.compareTo("clean")==0)){
				if(this.getFlags()==this.getMines()){
					Date end = new Date();
					long timeUsed = (end.getTime() - start.getTime())/1000;
					lblEstado.setBackgroundResource(R.drawable.icon_win);
					cr.stop();
					msg = msg_won + timeUsed + " seconds!";
					deleteListeners();
				}
		}
		return msg;
	}
	
	public String clean(Coord pos){
		
		String msg = "";
		if(getBox(pos).getNumMines()==numFlagsAround(getBox(pos))){	
			//if num of mines around is equal to the boxes flagged around, we can uncover the boxes around
			ArrayList<Coord> around = getBoxesAround(pos);
			Iterator<Coord> iterator = around.iterator();
			Coord i = new Coord();
			if(badFlagged(pos)){ 
				msg = msg_wrong;
				while(iterator.hasNext()){
					i = iterator.next();
					uncover(i);
				} 
				deleteListeners();
			}
			else{
				while(iterator.hasNext()){
					i = iterator.next();
					uncover(i);
					if(getBox(i).getNumMines()==0) msg="clean";
				}
			}
		}
		return msg;
	}
	
	public void autoUncover(Coord pos){
		Coord aux = new Coord(pos);
    	ArrayList<Coord> array = new ArrayList<Coord>();
    	boolean more=true;
    	array.add(pos);
    	while(more){
    		more=false;
    		for (int x=0;x<SIZEX;x++){
      			for (int y=0;y<SIZEY;y++){
      				aux = new Coord(x+1,y+1);
      				if(aux.isAdyacent(array)) if(getBox(aux).getNumMines()==0) if(aux.isInList(array)==false){
      					array.add(aux);
      					more=true;
      				}
      			}
    		}
    	}
    	
    	Iterator<Coord> iterator=array.iterator();
    	while(iterator.hasNext()){
    		aux = iterator.next();
    			uncover(aux);
    			click(aux,this.CLICK);
    	}
    }
	
	public boolean isFinished(){
		if ((getNumBoxesShown()+getFlags())==(Game.SIZEX*Game.SIZEY)) return true;
		else return false;
	}
	
	public int getNumBoxesShown(){
		int res=0;
		for (int x=0;x<SIZEX;x++){
			for (int y=0;y<SIZEY;y++){
				if(casillas[x][y].isShown()) res++;
			}
		}
		return res;
	}
	
	public boolean noBoxIsShown(){
		boolean res=true;
		for (int x=0;x<SIZEX;x++){
			for (int y=0;y<SIZEY;y++){
				if(casillas[x][y].isShown()) return false;
			}
		}
		return res;
	}
	
	public boolean badFlagged(Coord pos){
		
		boolean res = false;
		ArrayList<Coord> around = getBoxesAround(pos);
		Iterator<Coord> iterator = around.iterator();
		Coord i = new Coord();
		while(iterator.hasNext()){
			i = iterator.next();
			if((getBox(i).hasFlag())&&(!getBox(i).hasMine())){//if is a bad flagged box
				getBox(i).setStatus(Casilla.BADFLAGGED);
				getBox(i).setToNotFlag();
				res=true;
			}
			if((!getBox(i).hasFlag())&&(getBox(i).hasMine())){//if is a not flagged box
				getBox(i).setStatus(Casilla.NOTFLAGGED);
				getBox(i).setToNotFlag();
				res=true;
			}
		}
		return res;
		
	}
		
	
	public void AddFlag(){
		flags++;
	}
	
	private void recorrer(int fil, int col) {
        if (fil >= 0 && fil < SIZEX && col >= 0 && col < SIZEY) {
            if (!casillas[fil][col].hasMine()) {
                casillas[fil][col].setBackgroundResource(R.drawable.c0);
                recorrer(fil, col + 1);
                recorrer(fil, col - 1);
                recorrer(fil + 1, col);
                recorrer(fil - 1, col);
                recorrer(fil - 1, col - 1);
                recorrer(fil - 1, col + 1);
                recorrer(fil + 1, col + 1);
                recorrer(fil + 1, col - 1);
            } else 
            	uncover(new Coord(fil+1, col+1));
            }
        }
    
	
	public void deleteListeners(){
		for (int x=0;x<SIZEX;x++){
    		for (int y=0;y<SIZEY;y++){
				casillas[x][y].setOnClickListener(null);
				casillas[x][y].setOnLongClickListener(null);
				if(casillas[x][y].hasMine()) uncover(new Coord(x+1,y+1));
			}
		}
	}
	
	public void uncover(Coord pos){
		if (getBox(pos)!=null){
			
			if (getBox(pos).hasFlag()==false){
				getBox(pos).setToShown();
		
				switch(getBox(pos).getNumMines()){
				case 0: 
					getBox(pos).setImageResource(R.drawable.c0);
					
					return;
				case 1:
					getBox(pos).setImageResource(R.drawable.c1);
					return;
				case 2:
					getBox(pos).setImageResource(R.drawable.c2);
					return;
				case 3:
					getBox(pos).setImageResource(R.drawable.c3);
					return;
				case 4:
					getBox(pos).setImageResource(R.drawable.c4);
					return;
				case 5:
					getBox(pos).setImageResource(R.drawable.c5);
					return;
				case 6:
					getBox(pos).setImageResource(R.drawable.c6);
					return;
				case 7:
					getBox(pos).setImageResource(R.drawable.c7);
					return;
				case 8:
					getBox(pos).setImageResource(R.drawable.c8);
					return;
				case 9:
					getBox(pos).setImageResource(R.drawable.flag_wrong);
					return;
				case 10:
					getBox(pos).setImageResource(R.drawable.mine_wrong);
					return;
				case 11:
					getBox(pos).setImageResource(R.drawable.mine);
					return;
				}
			}
		}
	}
	
	public int getMines(){
		return mines;
	}
	public int getFlags(){
		return flags;
	}
	
	public void showMessage(String msg){
    	int duration = Toast.LENGTH_SHORT;
    	Toast toast = Toast.makeText(Game.this, msg, duration);
    	toast.show();
    }
	
	private void inicializarEventos(){
		for (int x=0;x<SIZEX;x++){
    		for (int y=0;y<SIZEY;y++){
				casillas[x][y].setOnClickListener(this);
				casillas[x][y].setOnLongClickListener(this);
			}
		}
	}
	
	public void onClick (View arg0) {
		// TODO Auto-generated method stub
		arg0 = (ImageView)arg0;
		
		lblEstado.setBackgroundResource(R.drawable.icon_click);
		for (int x=0;x<SIZEX;x++){
  			for (int y=0;y<SIZEY;y++){
  				if(arg0.getId()==casillas[x][y].getId()){
  					messageClick = click(new Coord(x+1,y+1),CLICK);
  					if (messageClick.compareTo("")!=0){
  						if(messageClick.compareTo("clean")!=0) showMessage(messageClick);
  						else autoUncover(new Coord(x+1,y+1));
  					}
  				}
  			}
  		}	
		lblEstado.setBackgroundResource(R.drawable.icon_estado1);
		//return true;
	}
	
		public boolean onLongClick(View arg0) {
			// TODO Auto-generated method stub
			arg0 = (ImageView)arg0;
			for (int x=0;x<SIZEX;x++){
	  			for (int y=0;y<SIZEY;y++){
	  				if(arg0.getId()==casillas[x][y].getId()){
	  					messageClick = click(new Coord(x+1,y+1),LONGCLICK);
	  					if (messageClick.compareTo("")!=0)
	  						showMessage(messageClick);
	  				}
	  			}
	  		}
			return true;
		
		}
	
		
}