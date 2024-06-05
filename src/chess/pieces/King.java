package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	public King(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		ChessPiece piece = (ChessPiece) getBoard().piece(position);

		return piece == null || piece.getColor() != getColor();
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];

		checkDirection(matrix, -1, 0); // above
		checkDirection(matrix, 1, 0); // below
		checkDirection(matrix, 0, -1); // left
		checkDirection(matrix, 0, 1); // right
		checkDirection(matrix, -1, -1); // northwest
		checkDirection(matrix, -1, 1); // northeast
		checkDirection(matrix, 1, -1); // southwest
		checkDirection(matrix, 1, 1); // southeast

		return matrix;
	}

	private void checkDirection(boolean[][] matrix, int rowIncrement, int columnIncrement) {
		Position positionAux = new Position(position.getRow() + rowIncrement, position.getColunm() + columnIncrement);

		if (getBoard().positionExists(positionAux) && canMove(positionAux))
			matrix[positionAux.getRow()][positionAux.getColunm()] = true;
	}

}// class King
