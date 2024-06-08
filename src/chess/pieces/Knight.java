package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;
import util.Pieces;

/**
 * Representa a peça Cavalo no jogo de xadrez.
 */
public class Knight extends ChessPiece {

	/**
	 * Cria uma nova peça Cavalo com o tabuleiro e a cor especificados.
	 * 
	 * @param board o tabuleiro em que a peça será colocada
	 * @param color a cor da peça (preta ou branca)
	 */
	public Knight(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return Pieces.KNIGHT.getAcronym();
	}

	/**
	 * Verifica se uma determinada posição pode ser movida pelo Cavalo.
	 * 
	 * @param position a posição a ser verificada
	 * @return true se o Cavalo pode mover-se para a posição, false caso contrário
	 */
	private boolean canMove(Position position) {
		ChessPiece piece = (ChessPiece) getBoard().piece(position);

		return piece == null || piece.getColor() != getColor();
	}

	/**
	 * Verifica e marca os movimentos possíveis do Cavalo.
	 * 
	 * @return uma matriz de booleanos com os movimentos possíveis
	 */
	@Override
	public boolean[][] possibleMoves() {

		boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];

		checkDirection(matrix, -1, -2);
		checkDirection(matrix, -2, -1);
		checkDirection(matrix, -2, 1);
		checkDirection(matrix, -1, 2);
		checkDirection(matrix, 1, 2);
		checkDirection(matrix, 2, 1);
		checkDirection(matrix, 2, -1);
		checkDirection(matrix, 1, -2);

		return matrix;
	}

	/**
	 * Verifica e marca os movimentos possíveis do Cavalo em uma direção específica.
	 * 
	 * @param matrix          a matriz de booleanos que representa os movimentos
	 *                        possíveis
	 * @param rowIncrement    o incremento da linha na direção do movimento
	 * @param columnIncrement o incremento da coluna na direção do movimento
	 */
	private void checkDirection(boolean[][] matrix, int rowIncrement, int columnIncrement) {

		Position positionAux = new Position(position.getRow() + rowIncrement, position.getColumn() + columnIncrement);

		if (getBoard().positionExists(positionAux) && canMove(positionAux))
			matrix[positionAux.getRow()][positionAux.getColumn()] = true;
	}
}// class Knight
