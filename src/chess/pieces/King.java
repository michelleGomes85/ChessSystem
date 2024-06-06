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

		checkDirection(matrix, -1, 0);
		checkDirection(matrix, 1, 0); 
		checkDirection(matrix, 0, -1);
		checkDirection(matrix, 0, 1); 
		checkDirection(matrix, -1, -1);
		checkDirection(matrix, -1, 1); 
		checkDirection(matrix, 1, -1); 
		checkDirection(matrix, 1, 1); 

		return matrix;
	}

	private void checkDirection(boolean[][] matrix, int rowIncrement, int columnIncrement) {
		
		Position positionAux = new Position(position.getRow() + rowIncrement, position.getColumn() + columnIncrement);

		if (getBoard().positionExists(positionAux) && canMove(positionAux))
			matrix[positionAux.getRow()][positionAux.getColumn()] = true;
	}

}// class King
