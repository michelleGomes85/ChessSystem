package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;
import util.Pieces;

/**
 * Representa uma peça de Torre no jogo de xadrez.
 */
public class Rook extends ChessPiece {

	/**
	 * Cria uma nova Torre com a cor e o tabuleiro especificados.
	 * 
	 * @param board o tabuleiro onde a Torre será colocada
	 * @param color a cor da Torre
	 */
	public Rook(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return Pieces.ROOK.getAcronym();
	}

	/**
	 * Retorna uma matriz de booleanos indicando os movimentos possíveis da Torre.
	 * 
	 * @return uma matriz de booleanos representando os movimentos possíveis da
	 *         Torre
	 */
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];

		checkDirection(matrix, -1, 0); // above
		checkDirection(matrix, 0, -1); // left
		checkDirection(matrix, 0, 1); // right
		checkDirection(matrix, 1, 0); // below

		return matrix;
	}

	/**
	 * Verifica os movimentos possíveis da Torre em uma determinada direção.
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

}// class Rook
