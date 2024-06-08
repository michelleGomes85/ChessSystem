package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;
import util.Pieces;

/**
 * Representa uma peça de Rainha no jogo de xadrez.
 */
public class Queen extends ChessPiece {

	/**
	 * Cria uma nova Rainha com a cor e o tabuleiro especificados.
	 * 
	 * @param board o tabuleiro onde a Rainha será colocada
	 * @param color a cor da Rainha
	 */
	public Queen(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return Pieces.QUEEN.getAcronym();
	}

	/**
	 * Retorna uma matriz de booleanos indicando os movimentos possíveis da Rainha.
	 * 
	 * @return uma matriz de booleanos representando os movimentos possíveis da
	 *         Rainha
	 */
	@Override
	public boolean[][] possibleMoves() {

		boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];

		checkDirection(matrix, -1, 0); // above
		checkDirection(matrix, 0, -1); // left
		checkDirection(matrix, 0, 1); // right
		checkDirection(matrix, 1, 0); // below

		checkDirection(matrix, -1, -1); // nw
		checkDirection(matrix, -1, 1); // ne
		checkDirection(matrix, 1, 1); // se
		checkDirection(matrix, 1, -1); // sw

		return matrix;
	}

	/**
	 * Verifica os movimentos possíveis da Rainha em uma determinada direção.
	 * 
	 * @param matrix          a matriz de booleanos que representa os movimentos
	 *                        possíveis
	 * @param rowIncrement    o incremento na linha
	 * @param columnIncrement o incremento na coluna
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
}// class Queen
