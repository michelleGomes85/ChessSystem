package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;
import util.Pieces;

/**
 * Representa a peça Peão no jogo de xadrez.
 */
public class Pawn extends ChessPiece {

	private ChessMatch chessMatch;

	/**
	 * Cria uma nova peça Peão com o tabuleiro, a cor e a partida de xadrez
	 * especificados.
	 * 
	 * @param board      o tabuleiro em que a peça será colocada
	 * @param color      a cor da peça (preta ou branca)
	 * @param chessMatch a partida de xadrez em que a peça será utilizada
	 */
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);

		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return Pieces.PAWN.getAcronym();
	}

	/**
	 * Verifica e marca os movimentos possíveis do Peão.
	 * 
	 * @return uma matriz de booleanos com os movimentos possíveis
	 */
	@Override
	public boolean[][] possibleMoves() {

		boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];

		if (getColor() == Color.WHITE) {
			checkPawnMoves(matrix, -1, -2);
			checkSpecialMovePassant(matrix, 3, -1);
		} else {
			checkPawnMoves(matrix, 1, 2);
			checkSpecialMovePassant(matrix, 4, 1);
		}

		return matrix;
	}

	/**
	 * Verifica e marca os movimentos possíveis do Peão.
	 * 
	 * @param matrix      a matriz de booleanos que representa os movimentos
	 *                    possíveis
	 * @param forwardStep o número de linhas a serem avançadas pelo Peão
	 * @param doubleStep  o número de linhas a serem avançadas pelo Peão em seu
	 *                    primeiro movimento
	 */
	private void checkPawnMoves(boolean[][] matrix, int forwardStep, int doubleStep) {

		Position currentPos = super.position;
		Position front = new Position(currentPos.getRow() + forwardStep, currentPos.getColumn());

		if (canMove(front))
			matrix[front.getRow()][front.getColumn()] = true;

		Position doubleFront = new Position(currentPos.getRow() + doubleStep, currentPos.getColumn());

		if (canMove(doubleFront) && canMove(front) && getMoveCount() == 0)
			matrix[doubleFront.getRow()][doubleFront.getColumn()] = true;

		checkCaptureMoves(matrix, currentPos, forwardStep, -1);
		checkCaptureMoves(matrix, currentPos, forwardStep, 1);
	}

	/**
	 * Verifica e marca o movimento especial "en passant" do Peão.
	 * 
	 * @param matrix a matriz de booleanos que representa os movimentos possíveis
	 * @param row    a linha em que o movimento especial "en passant" pode ocorrer
	 * @param pace   o número de linhas a serem avançadas pelo Peão
	 */
	private void checkSpecialMovePassant(boolean[][] matrix, int row, int pace) {

		if (position.getRow() == row) {

			Position left = new Position(position.getRow(), position.getColumn() - 1);

			if (getBoard().positionExists(left) && isThereOpponentPiece(left)
					&& getBoard().piece(left) == chessMatch.getEnPassantVulnerable())
				matrix[left.getRow() + pace][left.getColumn()] = true;

			Position right = new Position(position.getRow(), position.getColumn() + 1);

			if (getBoard().positionExists(right) && isThereOpponentPiece(right)
					&& getBoard().piece(right) == chessMatch.getEnPassantVulnerable())
				matrix[right.getRow() + pace][right.getColumn()] = true;
		}
	}

	/**
	 * Verifica e marca os movimentos de captura possíveis do Peão.
	 * 
	 * @param matrix      a matriz de booleanos que representa os movimentos
	 *                    possíveis
	 * @param currentPos  a posição atual do Peão
	 * @param forwardStep o número de linhas a serem avançadas pelo Peão
	 * @param sideStep    o número de colunas a serem avançadas pelo Peão na lateral
	 */
	private void checkCaptureMoves(boolean[][] matrix, Position currentPos, int forwardStep, int sideStep) {

		Position capture = new Position(currentPos.getRow() + forwardStep, currentPos.getColumn() + sideStep);

		if (canCapture(capture))
			matrix[capture.getRow()][capture.getColumn()] = true;
	}

	/**
	 * Verifica se uma determinada posição pode ser movida pelo Peão.
	 * 
	 * @param position a posição a ser verificada
	 * @return true se a posição pode ser movida pelo Peão, false caso contrário
	 */
	private boolean canMove(Position position) {
		return getBoard().positionExists(position) && !getBoard().thereIsAPiece(position);
	}

	/**
	 * Verifica se uma determinada posição pode ser capturada pelo Peão.
	 * 
	 * @param position a posição a ser verificada
	 * @return true se a posição pode ser capturada pelo Peão, false caso contrário
	 */
	private boolean canCapture(Position position) {
		return getBoard().positionExists(position) && isThereOpponentPiece(position);
	}
}// class Pawn