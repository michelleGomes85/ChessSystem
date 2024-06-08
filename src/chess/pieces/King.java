package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;
import util.Pieces;

/**
 * Representa a peça Rei no jogo de xadrez.
 */
public class King extends ChessPiece {

	private ChessMatch chessMatch;

	/**
	 * Cria uma nova peça Rei com o tabuleiro, a cor e o jogo de xadrez
	 * especificados.
	 * 
	 * @param board      o tabuleiro em que a peça será colocada
	 * @param color      a cor da peça (preta ou branca)
	 * @param chessMatch o jogo de xadrez
	 */
	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return Pieces.KING.getAcronym();
	}

	/**
	 * Verifica se uma determinada posição pode ser movida pelo Rei.
	 * 
	 * @param position a posição a ser verificada
	 * @return true se o Rei pode mover-se para a posição, false caso contrário
	 */
	private boolean canMove(Position position) {
		ChessPiece piece = (ChessPiece) getBoard().piece(position);

		return piece == null || piece.getColor() != getColor();
	}

	/**
	 * Verifica se a torre na posição especificada pode fazer um roque.
	 * 
	 * @param position a posição da torre
	 * @return true se a torre pode fazer um roque, false caso contrário
	 */
	private boolean testRookCastling(Position position) {
		ChessPiece piece = (ChessPiece) getBoard().piece(position);

		return piece != null && piece instanceof Rook && piece.getColor() == getColor() && piece.getMoveCount() == 0;
	}

	/**
	 * Verifica e marca os movimentos possíveis do Rei.
	 * 
	 * @return uma matriz de booleanos com os movimentos possíveis
	 */
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

	/**
	 * Verifica e marca os movimentos possíveis do Rei em uma direção específica.
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

	/**
	 * Verifica se o roque pode ser realizado e marca o movimento na matriz de
	 * movimentos possíveis.
	 * 
	 * @param matrix              a matriz de booleanos que representa os movimentos
	 *                            possíveis
	 * @param rookColumnOffset    o deslocamento da coluna da torre
	 * @param intermediateOffsets os deslocamentos intermediários entre o Rei e a
	 *                            torre
	 */
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
				matrix[position.getRow()][position.getColumn() + intermediateOffsets[1]] = true;
		}
	}
}// class King
