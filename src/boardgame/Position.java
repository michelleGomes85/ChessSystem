package boardgame;

import static util.Messages.STR_POSITION_TOSTRING;

/**
 * Representa uma posição em um tabuleiro de jogo, identificada por suas
 * coordenadas de linha e coluna.
 */
public class Position {

	private int row;
	private int colunm;

	/**
	 * Constrói uma posição com as coordenadas de linha e coluna especificadas.
	 * 
	 * @param row    a coordenada da linha
	 * @param column a coordenada da coluna
	 */
	public Position(int row, int colunm) {
		this.row = row;
		this.colunm = colunm;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return colunm;
	}

	public void setColunm(int colunm) {
		this.colunm = colunm;
	}

	@Override
	public String toString() {
		return String.format(STR_POSITION_TOSTRING, row, colunm);
	}

	/**
	 * Define novos valores para as coordenadas de linha e coluna.
	 * 
	 * @param row    a nova coordenada da linha
	 * @param column a nova coordenada da coluna
	 */
	public void setValues(int row, int column) {
		this.row = row;
		this.colunm = column;
	}

}// class Position
