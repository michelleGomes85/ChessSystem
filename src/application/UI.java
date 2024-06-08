package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import static util.Messages.*;

/**
 * Classe utilitária para manipulação da interface do usuário no jogo de xadrez.
 */
public class UI {

	/**
	 * Limpa a tela do console.
	 */
	public static void clearScreen() {
		System.out.print(ANSI_CLEAR_SCREEN);
		System.out.flush();
	}

	/**
	 * Lê uma posição de xadrez da entrada do usuário.
	 * 
	 * @param scanner O objeto Scanner usado para ler a entrada.
	 * @return A posição de xadrez lida.
	 * @throws InputMismatchException Se a entrada não for válida.
	 */
	public static ChessPosition readChessPosition(Scanner scanner) {

		try {
			String str = scanner.nextLine();
			char column = str.charAt(0);
			int row = Integer.parseInt(str.substring(1));

			return new ChessPosition(column, row);
		} catch (RuntimeException e) {
			throw new InputMismatchException(MSG_ERROR_READING_POSITION);
		}
	}

	/**
	 * Imprime o estado atual da partida de xadrez, incluindo o tabuleiro, as peças capturadas e o estado do jogo.
	 * 
	 * @param chessMatch O objeto ChessMatch que representa a partida de xadrez.
	 * @param captured A lista de peças capturadas.
	 */
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {

		printBoard(chessMatch.getPieces());

		System.out.println();
		printCapturedPieces(captured);
		System.out.println();

		System.out.println(MSG_TURN_PROMPT + chessMatch.getTurn());

		if (!chessMatch.isCheckMate()) {
			System.out.println(MSG_WAITING_PLAYER + chessMatch.getCurrentPlayer());

			if (chessMatch.isCheck())
				System.out.println(CHECK);
		} else {
			System.out.println(CHECK_MATE);
			System.out.println(WINNER + chessMatch.getCurrentPlayer());
		}

	}

	/**
	 * Imprime o tabuleiro de xadrez com as peças na tela.
	 * 
	 * @param pieces A matriz de peças no tabuleiro.
	 */
	public static void printBoard(ChessPiece[][] pieces) {

		System.out.println();

		for (int i = 0; i < pieces.length; i++) {

			System.out.print(TAB + (8 - i) + SPACE);
			for (int j = 0; j < pieces.length; j++)
				printPiece(pieces[i][j], false);

			System.out.println();
		}

		System.out.println(COLUMN_HEADERS);
	}

	/**
	 * Imprime o tabuleiro de xadrez com as peças na tela, destacando os movimentos possíveis.
	 * 
	 * @param pieces A matriz de peças no tabuleiro.
	 * @param possibleMoves A matriz de movimentos possíveis.
	 */
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {

		System.out.println();

		for (int i = 0; i < pieces.length; i++) {

			System.out.print(TAB + (8 - i) + SPACE);
			for (int j = 0; j < pieces.length; j++)
				printPiece(pieces[i][j], possibleMoves[i][j]);

			System.out.println();
		}

		System.out.println(COLUMN_HEADERS);
	}

	/**
	 * Imprime uma peça de xadrez na tela, com a cor de fundo opcional.
	 * 
	 * @param piece A peça a ser impressa.
	 * @param background Se deve ou não destacar o fundo.
	 */
	private static void printPiece(ChessPiece piece, boolean background) {

		if (background)
			System.out.print(ANSI_BLUE_BACKGROUND);

		if (piece == null)
			System.out.print(WITHOUT_PIECE + ANSI_RESET);
		else
			System.out.print((piece.getColor() == Color.WHITE ? ANSI_WHITE : ANSI_YELLOW) + piece + ANSI_RESET);

		System.out.print(SPACE);
	}

    /**
     * Imprime as peças capturadas na tela.
     * 
     * @param captured A lista de peças capturadas.
     */
	private static void printCapturedPieces(List<ChessPiece> captured) {
		List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE)
				.collect(Collectors.toList());
		List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK)
				.collect(Collectors.toList());

		System.out.println(CAPTURED_PIECES);
		printColoredPieces(Color.WHITE.getTitle(), white, ANSI_WHITE);
		printColoredPieces(Color.BLACK.getTitle(), black, ANSI_YELLOW);
	}

    /**
     * Imprime as peças capturadas de uma determinada cor na tela.
     * 
     * @param colorName O nome da cor das peças.
     * @param pieces A lista de peças capturadas.
     * @param colorCode O código de cor ANSI para as peças.
     */
	private static void printColoredPieces(String colorName, List<ChessPiece> pieces, String colorCode) {
		System.out.print(colorName + TWO_DOTS);
		System.out.print(colorCode);
		System.out.print(Arrays.toString(pieces.toArray()));
		System.out.println(ANSI_RESET);
	}

}// class UI