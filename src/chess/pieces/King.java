package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		ChessPiece piece = (ChessPiece) getBoard().piece(position);

		return piece == null || piece.getColor() != getColor();
	}

	private boolean testRookCastling(Position position) {
		ChessPiece piece = (ChessPiece) getBoard().piece(position);

		return piece != null && piece instanceof Rook && piece.getColor() == getColor() && piece.getMoveCount() == 0;
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

		// Special move: castling
		if (getMoveCount() == 0 && !chessMatch.isCheck()) {
			// Kingside castling
			checkCastlingMove(matrix, 3, new int[] { 1, 2 });

			// Queenside castling
			checkCastlingMove(matrix, -4, new int[] { -1, -2, -3 });
		}

		return matrix;
	}

	private void checkDirection(boolean[][] matrix, int rowIncrement, int columnIncrement) {

		Position positionAux = new Position(position.getRow() + rowIncrement, position.getColumn() + columnIncrement);

		if (getBoard().positionExists(positionAux) && canMove(positionAux))
			matrix[positionAux.getRow()][positionAux.getColumn()] = true;
	}

	private void checkCastlingMove(boolean[][] matrix, int rookColumnOffset, int[] intermediateOffsets) {

		Position rookPosition = new Position(position.getRow(), position.getColumn() + rookColumnOffset);

		if (testRookCastling(rookPosition)) {

			boolean pathClear = true;

			for (int offset : intermediateOffsets) {

				Position pos = new Position(position.getRow(), position.getColumn() + offset);
				if (getBoard().piece(pos) != null) {
					pathClear = false;
					break;
				}
			}

			if (pathClear)
				matrix[position.getRow()][position.getColumn()
						+ intermediateOffsets[1]] = true;
		}
	}

}// class King
