package boardgame;

import static util.Messages.MSG_ERROR_ALREADY_PIECE;
import static util.Messages.MSG_ERROR_CREATED_BOARD;
import static util.Messages.MSG_ERROR_INVALID_POSITION;

/**
 * Representa um tabuleiro de jogo com uma grade de peças.
 */
public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces;

	/**
	 * Constrói um Tabuleiro com o número especificado de linhas e colunas.
	 * 
	 * @param rows    o número de linhas no tabuleiro
	 * @param columns o número de colunas no tabuleiro
	 * @throws BoardException se o número de linhas ou colunas for menor que 1
	 */
	public Board(int rows, int columns) {

		if (rows < 1 || columns < 1)
			throw new BoardException(MSG_ERROR_CREATED_BOARD);

		this.rows = rows;
		this.columns = columns;

		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	/**
	 * Retorna a peça na linha e coluna especificadas.
	 * 
	 * @param row    o índice da linha
	 * @param column o índice da coluna
	 * @return a peça na posição especificada
	 * @throws BoardException se a posição for inválida
	 */
	public Piece piece(int row, int column) {
		if (!positionExists(row, column))
			throw new BoardException(MSG_ERROR_INVALID_POSITION);

		return pieces[row][column];
	}

	/**
	 * Retorna a peça na posição especificada.
	 * 
	 * @param position a posição no tabuleiro
	 * @return a peça na posição especificada
	 * @throws BoardException se a posição for inválida
	 */
	public Piece piece(Position position) {

		if (!positionExists(position))
			throw new BoardException(MSG_ERROR_INVALID_POSITION);

		return pieces[position.getRow()][position.getColumn()];
	}

	/**
	 * Coloca uma peça na posição especificada.
	 * 
	 * @param piece    a peça a ser colocada
	 * @param position a posição onde a peça será colocada
	 * @throws BoardException se já houver uma peça na posição especificada
	 */
	public void placePiece(Piece piece, Position position) {

		if (thereIsAPiece(position))
			throw new BoardException(String.format(MSG_ERROR_ALREADY_PIECE, position));

		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}

	/**
	 * Remove a peça na posição especificada.
	 * 
	 * @param position a posição de onde a peça será removida
	 * @return a peça removida, ou null se não houver peça na posição
	 * @throws BoardException se a posição for inválida
	 */
	public Piece removePiece(Position position) {

		if (!positionExists(position))
			throw new BoardException(MSG_ERROR_INVALID_POSITION);

		if (piece(position) == null)
			return null;

		Piece pieceAux = piece(position);

		pieceAux.position = null;

		pieces[position.getRow()][position.getColumn()] = null;

		return pieceAux;
	}

	/**
	 * Verifica se a posição especificada está dentro dos limites do tabuleiro.
	 * 
	 * @param row    o índice da linha
	 * @param column o índice da coluna
	 * @return true se a posição existir no tabuleiro, false caso contrário
	 */
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}

	/**
	 * Verifica se a posição especificada está dentro dos limites do tabuleiro.
	 * 
	 * @param position a posição a ser verificada
	 * @return true se a posição existir no tabuleiro, false caso contrário
	 */
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}

	/**
	 * Verifica se há uma peça na posição especificada.
	 * 
	 * @param position a posição a ser verificada
	 * @return true se houver uma peça na posição, false caso contrário
	 * @throws BoardException se a posição for inválida
	 */
	public boolean thereIsAPiece(Position position) {

		if (!positionExists(position))
			throw new BoardException(MSG_ERROR_INVALID_POSITION);

		return piece(position) != null;
	}

}// class Board