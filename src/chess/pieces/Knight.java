package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

	public Knight(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "N";
	}

	private boolean canMove(Position position) {
		ChessPiece piece = (ChessPiece) getBoard().piece(position);

		return piece == null || piece.getColor() != getColor();
	}

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

	private void checkDirection(boolean[][] matrix, int rowIncrement, int columnIncrement) {
		
		Position positionAux = new Position(position.getRow() + rowIncrement, position.getColumn() + columnIncrement);

		if (getBoard().positionExists(positionAux) && canMove(positionAux))
			matrix[positionAux.getRow()][positionAux.getColumn()] = true;
	}
}
