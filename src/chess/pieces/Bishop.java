package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {
	
	public Bishop(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];

		checkDirection(matrix, -1, -1); // nw
		checkDirection(matrix, -1, 1); // ne
		checkDirection(matrix, 1, 1); // se
		checkDirection(matrix, 1, -1); // sw

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
}//class Bishop
