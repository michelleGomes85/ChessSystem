package application;

import static util.Messages.MSG_ENTER_PROMOTION_PIECE;
import static util.Messages.MSG_ERROR_INVALID_VALUE;
import static util.Messages.MSG_SOURCE_PROMPT;
import static util.Messages.MSG_TARGET_PROMPT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import util.Pieces;

/**
 * Classe principal que controla a execução do jogo de xadrez.
 */
public class Program {

	private static Scanner scanner = new Scanner(System.in);
	private static ChessMatch chessMatch = new ChessMatch();
	private static List<ChessPiece> captured = new ArrayList<>();

	/**
	 * Método principal que inicia o jogo.
	 * 
	 * @param args argumentos da linha de comando (não utilizados)
	 */
	public static void main(String[] args) {
		runGame();
	}

	/**
	 * Método que controla a execução do jogo até que haja um cheque-mate.
	 */
	private static void runGame() {

		while (!chessMatch.isCheckMate()) {

			try {
				performTurn();
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				scanner.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				scanner.nextLine();
			}
		}

		endGame();
	}

	/**
	 * Método que realiza um turno no jogo de xadrez. Este método cuida da interação
	 * com o usuário, leitura de movimentos e promoção de peças.
	 */
	private static void performTurn() {

		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
		System.out.println();

		System.out.print(MSG_SOURCE_PROMPT);
		ChessPosition source = UI.readChessPosition(scanner);

		boolean[][] possibleMoves = chessMatch.possibleMoves(source);
		UI.clearScreen();
		UI.printBoard(chessMatch.getPieces(), possibleMoves);

		System.out.println();

		System.out.print(MSG_TARGET_PROMPT);
		ChessPosition target = UI.readChessPosition(scanner);

		ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
		if (capturedPiece != null)
			captured.add(capturedPiece);

		if (chessMatch.getPromoted() != null)
			handlePromotion();
	}

	/**
	 * Método que lida com a promoção de peões. Solicita ao usuário o tipo de peça
	 * para a promoção e realiza a substituição.
	 */
	private static void handlePromotion() {

		String type;
		List<String> validPieces = Arrays.asList(Pieces.pieces());

		do {
	        System.out.print(MSG_ENTER_PROMOTION_PIECE);
	        type = scanner.nextLine().toUpperCase();
	        if (!validPieces.contains(type)) {
	            System.out.println(MSG_ERROR_INVALID_VALUE);
	        }
	    } while (!validPieces.contains(type));

		chessMatch.replacePromotedPiece(type);
	}

	/**
	 * Método que exibe o estado final do jogo após o término.
	 */
	private static void endGame() {
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
	}

}// class Program