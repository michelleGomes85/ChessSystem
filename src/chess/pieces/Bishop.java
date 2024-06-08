package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;
import util.Pieces;

/**
 * Representa a peça Bispo no jogo de xadrez.
 */
public class Bishop extends ChessPiece {

	/**
	 * Cria uma nova peça Bispo com o tabuleiro e a cor especificados.
	 * 
	 * @param board o tabuleiro em que a peça será colocada
	 * @param color a cor da peça (preta ou branca)
	 */
	public Bishop(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return Pieces.BISHOP.getAcronym();
	}

	/**
	 * Retorna uma matriz de booleanos indicando os movimentos possíveis para o
	 * Bispo.
	 * 
	 * @return uma matriz de booleanos com os movimentos possíveis
	 */
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];

		checkDirection(matrix, -1, -1); // nw
		checkDirection(matrix, -1, 1); // ne
		checkDirection(matrix, 1, 1); // se
		checkDirection(matrix, 1, -1); // sw

		return matrix;
	}

	/**
	 * Verifica e marca os movimentos possíveis do Bispo em uma direção específica.
	 * 
	 * @param matrix          a matriz de booleanos que representa os movimentos
	 *                        possíveis
	 * @param rowIncrement    o incremento da linha na direção do movimento
	 * @param columnIncrement o incremento da coluna na direção do movimento
	 */
	private void checkDirection(boolean[][] matrix, int rowIncrement, int columnIncrement) {

		Position positionAux = new Position(position.getRow() + rowIncrement, position.getColumn() + columnIncrement);

		while (getBoard().positionExists(positionAux) && !getBoard().thereIsAPiece(positionAux)) {
			matrix[positionAux.getRow()][positionAux.getColumn()] = true;
			positionAux.setValues(positionAux.getRow() + rowIncrement, positionAux.getColumn() + columnIncrement);
		}

		if (getBoard().positionExists(positionAux) && isThereOpponentPiece(positionAux))
			matrix[positionAux.getRow()][positionAux.getColumn()] = true;
	}
}// class Bishop
