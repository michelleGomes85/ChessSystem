package chess;

import boardgame.Position;

import static util.Messages.*;

/**
 * Representa uma posição no tabuleiro de xadrez.
 */
public class ChessPosition {

	private char column;
	private int row;

	/**
	 * Cria uma nova posição de xadrez com a coluna e a linha especificadas.
	 * 
	 * @param column a coluna da posição (de 'a' a 'h')
	 * @param row    a linha da posição (de 1 a 8)
	 * @throws ChessException se a coluna ou a linha estiver fora do intervalo
	 *                        permitido
	 */
	public ChessPosition(char column, int row) {

		if (column < 'a' || column > 'h' || row < 1 || row > 8)
			throw new ChessException(MSG_ERROR_INSTANCE_CHESS_PIECE);

		this.column = column;
		this.row = row;
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	@Override
	public String toString() {
		return String.format(STR_CHESS_POSITION_TOSTRING, column, row);
	}
	
	/**
	 * Converte a posição de xadrez para uma posição no tabuleiro.
	 * 
	 * @return a posição no tabuleiro correspondente à posição de xadrez
	 */
	protected Position toPosition() {
		return new Position(8 - row, column - 'a');
	}

	/**
	 * Converte uma posição no tabuleiro para uma posição de xadrez.
	 * 
	 * @param position a posição no tabuleiro a ser convertida
	 * @return a posição de xadrez correspondente à posição no tabuleiro
	 */
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char) ('a' + position.getColumn()), 8 - position.getRow());
	}

}// class ChessPosition
