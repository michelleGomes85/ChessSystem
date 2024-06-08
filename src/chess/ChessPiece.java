package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

	private Color color;
	private int moveCount;

	/**
	 * Cria uma nova peça de xadrez com o tabuleiro e a cor especificados.
	 * 
	 * @param board o tabuleiro em que a peça será colocada
	 * @param color a cor da peça
	 */
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public int getMoveCount() {
		return moveCount;
	}

	/**
	 * Incrementa a contagem de movimentos da peça.
	 */
	public void increaseMoveCount() {
		moveCount++;
	}

	/**
	 * Decrementa a contagem de movimentos da peça.
	 */
	public void decreaseMoveCount() {
		moveCount--;
	}

	/**
	 * Retorna a posição da peça no formato de posição de xadrez.
	 * 
	 * @return a posição de xadrez da peça
	 */
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}

	/**
	 * Verifica se há uma peça adversária na posição especificada.
	 * 
	 * @param position a posição a ser verificada
	 * @return true se houver uma peça adversária na posição, false caso contrário
	 */
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece piece = (ChessPiece) getBoard().piece(position);

		return piece != null && piece.getColor() != color;
	}

}// class ChessPiece
