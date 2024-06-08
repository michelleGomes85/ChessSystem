package boardgame;

/**
 * Exceção específica para erros relacionados ao tabuleiro de jogo.
 */
public class BoardException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constrói uma nova exceção com a mensagem de detalhe especificada.
	 * 
	 * @param message a mensagem de detalhe
	 */
	public BoardException(String message) {
		super(message);
	}
}// class BoardException
