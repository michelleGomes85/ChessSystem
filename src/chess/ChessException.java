package chess;

import boardgame.BoardException;

/**
 * Exceção específica para erros relacionados ao jogo de xadrez.
 */
public class ChessException extends BoardException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constrói uma nova exceção com a mensagem de detalhe especificada.
	 * 
	 * @param message a mensagem de detalhe
	 */
	public ChessException(String message) {
		super(message);
	}
}// class ChessException
