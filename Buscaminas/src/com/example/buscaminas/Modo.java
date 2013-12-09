package com.example.buscaminas;

public enum Modo {Facil(1), Intermedio(2),	Avanzado(3);
	int rows, cols;
	int op;

	Modo(int op){
		switch (op){
		case 1:
			rows = cols = 8;
			break;
		case 2:
			rows = cols = 16;
			break;
		case 3:
			rows = 16;
			cols = 30;
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