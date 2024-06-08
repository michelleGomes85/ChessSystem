package boardgame;

/**
 * Representa uma peça abstrata em um tabuleiro de jogo.
 */
public abstract class Piece {

	protected Position position;
	private Board board;

	/**
	 * Constrói uma peça associada a um tabuleiro específico.
	 * 
	 * @param board o tabuleiro ao qual a peça pertence
	 */
	public Piece(Board board) {
		this.board = board;
		position = null;
	}

	protected Board getBoard() {
		return board;
	}

	/**
	 * Retorna uma matriz booleana representando os movimentos possíveis para a
	 * peça. As subclasses devem implementar este método para definir os movimentos
	 * específicos da peça.
	 * 
	 * @return uma matriz booleana dos movimentos possíveis
	 */
	public abstract boolean[][] possibleMoves();

	/**
	 * Verifica se a peça pode se mover para a posição especificada.
	 * 
	 * @param position a posição a ser verificada
	 * @return true se a peça pode se mover para a posição, false caso contrário
	 */
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}

	/**
	 * Verifica se há pelo menos um movimento possível para a peça.
	 * 
	 * @return true se há pelo menos um movimento possível, false caso contrário
	 */
	public boolean isThereAnyPossibleMove() {
		boolean[][] matrix = possibleMoves();

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++)
				if (matrix[i][j])
					return true;
		}

		return false;
	}

}// class Piece