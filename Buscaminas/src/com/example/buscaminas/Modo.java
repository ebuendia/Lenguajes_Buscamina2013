package com.example.buscaminas;

public enum Modo {Facil(1), Intermedio(2),	Avanzado(3);
	int rows, cols, mines;
	int op;

	Modo(int op){
		switch (op){
		case 1:
			rows = cols = 8;
			mines = 10;
			break;
		case 2:
			rows = cols = 16;
			mines = 40;
			break;
		case 3:
			rows = 30;
			cols = 16;
			mines = 99;
			break;
		}
	}

	public int getColumns(){
		return cols;
	}

	public int getRows(){
		return rows;
	}
}