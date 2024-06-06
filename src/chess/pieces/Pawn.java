package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
	}

	@Override
	public boolean[][] possibleMoves() {

		boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];

		if (getColor() == Color.WHITE)
			checkWhitePawnMoves(matrix);
		else
			checkBlackPawnMoves(matrix);

		return matrix;
	}

	private void checkWhitePawnMoves(boolean[][] matrix) {
		
		Position position = new Position(super.position.getRow(), super.position.getColumn());

		Position front = new Position(position.getRow() - 1, position.getColumn());
		
		if (canMove(front))
			matrix[front.getRow()][front.getColumn()] = true;

		Position doubleStep = new Position(position.getRow() - 2, position.getColumn());
		
		if (canMove(doubleStep) && canMove(front) && getMoveCount() == 0)
			matrix[doubleStep.getRow()][doubleStep.getColumn()] = true;

		Position leftCapture = new Position(position.getRow() - 1, position.getColumn() - 1);
		
		if (canCapture(leftCapture))
			matrix[leftCapture.getRow()][leftCapture.getColumn()] = true;

		Position rightCapture = new Position(position.getRow() - 1, position.getColumn() + 1);
		
		if (canCapture(rightCapture))
			matrix[rightCapture.getRow()][rightCapture.getColumn()] = true;
	}

	private void checkBlackPawnMoves(boolean[][] matrix) {

		Position position = new Position(super.position.getRow(), super.position.getColumn());

		Position front = new Position(position.getRow() + 1, position.getColumn());
		if (canMove(front))
			matrix[front.getRow()][front.getColumn()] = true;

		Position doubleStep = new Position(position.getRow() + 2, position.getColumn());
		
		if (canMove(doubleStep) && canMove(front) && getMoveCount() == 0)
			matrix[doubleStep.getRow()][doubleStep.getColumn()] = true;

		Position leftCapture = new Position(position.getRow() + 1, position.getColumn() - 1);
		
		if (canCapture(leftCapture))
			matrix[leftCapture.getRow()][leftCapture.getColumn()] = true;

		Position rightCapture = new Position(position.getRow() + 1, position.getColumn() + 1);
		
		if (canCapture(rightCapture))
			matrix[rightCapture.getRow()][rightCapture.getColumn()] = true;
	}

	private boolean canMove(Position position) {
		return getBoard().positionExists(position) && !getBoard().thereIsAPiece(position);
	}

	private boolean canCapture(Position position) {
		return getBoard().positionExists(position) && isThereOpponentPiece(position);
	}

	@Override
	public String toString() {
		return "P";
	}

}// class Pawn
