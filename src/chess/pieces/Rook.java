package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];

		checkDirection(matrix, -1, 0); // above
		checkDirection(matrix, 0, -1); // left
		checkDirection(matrix, 0, 1); // right
		checkDirection(matrix, 1, 0); // below

		return matrix;
	}

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
