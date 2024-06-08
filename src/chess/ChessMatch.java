package chess;

import static util.Messages.MSG_ERROR_CANT_MOVE_TOPOSITION;
import static util.Messages.MSG_ERROR_KING_COLOR;
import static util.Messages.MSG_ERROR_NOT_CHECK;
import static util.Messages.MSG_ERROR_NOT_PIECE_PROMOTED;
import static util.Messages.MSG_ERROR_NOT_PIECE_SOURCE;
import static util.Messages.MSG_ERROR_NOT_POSSIBLE_MOVES;
import static util.Messages.MSG_ERROR_PIECE_NOT_YOURS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;
import util.Pieces;

/**
 * Representa uma partida de xadrez.
 */
public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;

	private List<Piece> piecesOnTheBoard;
	private List<Piece> capturedPieces;

	/**
	 * Constrói uma nova partida de xadrez e inicializa o tabuleiro e as peças.
	 */
	public ChessMatch() {

		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		check = false;

		piecesOnTheBoard = new ArrayList<>();
		capturedPieces = new ArrayList<>();

		initialSetup();
	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean isCheck() {
		return check;
	}

	public boolean isCheckMate() {
		return checkMate;
	}

	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}

	public ChessPiece getPromoted() {
		return promoted;
	}

	/**
	 * Retorna uma matriz de peças de xadrez representando o estado atual do
	 * tabuleiro.
	 * 
	 * @return uma matriz de peças de xadrez
	 */
	public ChessPiece[][] getPieces() {

		ChessPiece[][] matrix = new ChessPiece[board.getRows()][board.getColumns()];

		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++)
				matrix[i][j] = (ChessPiece) board.piece(i, j);
		}

		return matrix;
	}

	/**
	 * Retorna uma matriz de booleanos representando os movimentos possíveis para
	 * uma peça na posição de origem especificada.
	 * 
	 * @param sourcePosition a posição de origem
	 * @return uma matriz de booleanos dos movimentos possíveis
	 */
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {

		Position position = sourcePosition.toPosition();
		validadeSourcePosition(position);

		return board.piece(position).possibleMoves();
	}

	/**
	 * Executa um movimento de xadrez a partir de uma posição de origem para uma
	 * posição de destino.
	 * 
	 * @param sourcePosition a posição de origem
	 * @param targetPosition a posição de destino
	 * @return a peça capturada durante o movimento, se houver
	 */
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {

		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();

		validadeSourcePosition(source);
		validadeTargetPosition(source, target);

		Piece capturedPiece = makeMove(source, target);

		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException(MSG_ERROR_NOT_CHECK);
		}

		ChessPiece movedPiece = (ChessPiece) board.piece(target);

		// #specialmove promotion
		promoted = null;
		if (movedPiece instanceof Pawn) {
			if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0)
					|| (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
				promoted = (ChessPiece) board.piece(target);
				promoted = replacePromotedPiece("Q");
			}
		}

		check = (testCheck(opponent(currentPlayer))) ? true : false;

		if (testCheckMate(opponent(currentPlayer)))
			checkMate = true;
		else
			nextTurn();

		// #specialmove en passant
		if (movedPiece instanceof Pawn
				&& (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2))
			enPassantVulnerable = movedPiece;
		else
			enPassantVulnerable = null;

		return (ChessPiece) capturedPiece;
	}

	/**
	 * Substitui a peça promovida durante um movimento especial de promoção.
	 * 
	 * @param type o tipo de peça para a promoção (B, N, R ou Q)
	 * @return a nova peça promovida
	 */
	public ChessPiece replacePromotedPiece(String type) {

		if (promoted == null)
			throw new IllegalStateException(MSG_ERROR_NOT_PIECE_PROMOTED);

		List<String> validPieces = Arrays.asList(Pieces.pieces());
		if (!validPieces.contains(type))
			return promoted;

		Position pos = promoted.getChessPosition().toPosition();

		Piece piece = board.removePiece(pos);
		piecesOnTheBoard.remove(piece);

		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);

		return newPiece;
	}

	/**
	 * Cria uma nova peça do tipo especificado e cor especificada.
	 * 
	 * @param type  o tipo da peça (B, N, R ou Q)
	 * @param color a cor da peça
	 * @return a nova peça criada
	 */
	private ChessPiece newPiece(String type, Color color) {

		if (type.equals("B"))
			return new Bishop(board, color);
		if (type.equals("N"))
			return new Knight(board, color);
		if (type.equals("Q"))
			return new Queen(board, color);

		return new Rook(board, color);
	}

	/**
	 * Executa um movimento de xadrez a partir de uma posição de origem para uma
	 * posição de destino.
	 * 
	 * @param source a posição de origem
	 * @param target a posição de destino
	 * @return a peça capturada durante o movimento, se houver
	 */
	private Piece makeMove(Position source, Position target) {

		ChessPiece piece = (ChessPiece) board.removePiece(source);
		piece.increaseMoveCount();

		Piece capturedPiece = board.removePiece(target);
		board.placePiece(piece, target);

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		// Handle castling move
		handleCastling(source, target, piece, true);

		// Handle enPassant move
		capturedPiece = handleEnPassant(source, target, piece, capturedPiece, null);

		return capturedPiece;
	}

	/**
	 * Desfaz um movimento de xadrez a partir de uma posição de origem para uma
	 * posição de destino.
	 * 
	 * @param source        a posição de origem
	 * @param target        a posição de destino
	 * @param capturedPiece a peça capturada durante o movimento
	 */
	private void undoMove(Position source, Position target, Piece capturedPiece) {

		ChessPiece piece = (ChessPiece) board.removePiece(target);
		piece.decreaseMoveCount();

		board.placePiece(piece, source);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}

		// Handle castling move
		handleCastling(source, target, piece, false);

		// Handle enPassant move
		handleEnPassant(source, target, piece, capturedPiece, enPassantVulnerable);
	}

	/**
	 * Lida com um movimento especial de roque.
	 * 
	 * @param kingSource       a posição de origem do rei
	 * @param rookColumnOffset o deslocamento da coluna da torre
	 * @param rookTargetOffset o deslocamento da coluna de destino da torre
	 * @param isMakeMove       indica se o movimento deve ser executado ou desfeito
	 */
	private void handleCastling(Position source, Position target, ChessPiece piece, boolean isMakeMove) {

		int kingSideCastlingColumn = 2;
		int queenSideCastlingColumn = -2;

		if (piece instanceof King) {
			if (target.getColumn() == source.getColumn() + kingSideCastlingColumn)
				handleRookMove(source, 3, 1, isMakeMove);
			else if (target.getColumn() == source.getColumn() + queenSideCastlingColumn)
				handleRookMove(source, -4, -1, isMakeMove);
		}
	}

	/**
	 * Lida com um movimento especial de en passant.
	 * 
	 * @param source              a posição de origem
	 * @param target              a posição de destino
	 * @param piece               a peça que se move
	 * @param capturedPiece       a peça capturada durante o movimento
	 * @param enPassantVulnerable a peça vulnerável ao movimento en passant
	 * @return a peça capturada durante o movimento, se houver
	 */
	private Piece handleEnPassant(Position source, Position target, ChessPiece piece, Piece capturedPiece,
			ChessPiece enPassantVulnerable) {

		int rowPositionWhite = (enPassantVulnerable == null) ? (target.getRow() + 1) : 3;
		int rowPositionBlack = (enPassantVulnerable == null) ? (target.getRow() - 1) : 4;

		if (piece instanceof Pawn) {

			if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {

				Position pawnPosition;

				if (piece.getColor() == Color.WHITE)
					pawnPosition = new Position(rowPositionWhite, target.getColumn());
				else
					pawnPosition = new Position(rowPositionBlack, target.getColumn());

				if (enPassantVulnerable == null) {
					capturedPiece = board.removePiece(pawnPosition);
					piecesOnTheBoard.remove(capturedPiece);
					capturedPieces.add(capturedPiece);
				} else {
					ChessPiece pawn = (ChessPiece) board.removePiece(target);
					board.placePiece(pawn, pawnPosition);
				}
			}
		}

		return capturedPiece;
	}

	private void handleRookMove(Position kingSource, int rookColumnOffset, int rookTargetOffset, boolean isMakeMove) {

		Position sourceRook = new Position(kingSource.getRow(), kingSource.getColumn() + rookColumnOffset);
		Position targetRook = new Position(kingSource.getRow(), kingSource.getColumn() + rookTargetOffset);

		ChessPiece rook = (ChessPiece) board.removePiece(isMakeMove ? sourceRook : targetRook);
		board.placePiece(rook, isMakeMove ? targetRook : sourceRook);

		if (isMakeMove)
			rook.increaseMoveCount();
		else
			rook.decreaseMoveCount();
	}

	/**
	 * Valida se a posição de origem especificada é válida para o movimento de
	 * xadrez.
	 * 
	 * @param source a posição de origem
	 * @param target a posição de destino
	 */
	private void validadeSourcePosition(Position position) {

		if (!board.thereIsAPiece(position))
			throw new ChessException(MSG_ERROR_NOT_PIECE_SOURCE);

		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor())
			throw new ChessException(MSG_ERROR_PIECE_NOT_YOURS);

		if (!board.piece(position).isThereAnyPossibleMove())
			throw new ChessException(MSG_ERROR_NOT_POSSIBLE_MOVES);
	}

	/**
	 * Valida se a posição de destino especificada é válida para o movimento de
	 * xadrez.
	 * 
	 * @param source a posição de origem
	 * @param target a posição de destino
	 */
	private void validadeTargetPosition(Position source, Position target) {

		if (!board.piece(source).possibleMove(target))
			throw new ChessException(MSG_ERROR_CANT_MOVE_TOPOSITION);
	}

	/**
	 * Avança para o próximo turno, atualizando o jogador atual.
	 */
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	/**
	 * Retorna a cor do oponente do jogador especificado.
	 * 
	 * @param color a cor do jogador
	 * @return a cor do oponente
	 */
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	/**
	 * Retorna a peça do rei com a cor especificada.
	 * 
	 * @param color a cor do rei
	 * @return a peça do rei
	 */
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());

		for (Piece piece : list) {
			if (piece instanceof King)
				return (ChessPiece) piece;
		}

		throw new IllegalStateException(String.format(MSG_ERROR_KING_COLOR, color));
	}

	/**
	 * Testa se o jogador da cor especificada está em xeque.
	 * 
	 * @param color a cor do jogador
	 * @return true se o jogador estiver em xeque, false caso contrário
	 */
	private boolean testCheck(Color color) {

		Position kingPosition = king(color).getChessPosition().toPosition();

		List<Piece> opponentPieces = piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());

		for (Piece piece : opponentPieces) {
			boolean[][] matrix = piece.possibleMoves();
			if (matrix[kingPosition.getRow()][kingPosition.getColumn()])
				return true;
		}

		return false;
	}

	/**
	 * Testa se o jogador da cor especificada está em xeque-mate.
	 * 
	 * @param color a cor do jogador
	 * @return true se o jogador estiver em xeque-mate, false caso contrário
	 */
	private boolean testCheckMate(Color color) {

		if (!testCheck(color))
			return false;

		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());

		for (Piece piece : list) {
			boolean[][] matrix = piece.possibleMoves();

			for (int i = 0; i < board.getRows(); i++) {

				for (int j = 0; j < board.getColumns(); j++) {

					if (matrix[i][j]) {

						Position source = ((ChessPiece) piece).getChessPosition().toPosition();
						Position target = new Position(i, j);

						Piece capturedPiece = makeMove(source, target);

						boolean testCheck = testCheck(color);

						undoMove(source, target, capturedPiece);

						if (!testCheck)
							return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * Coloca uma nova peça no tabuleiro na posição especificada.
	 * 
	 * @param column a coluna da posição
	 * @param row    a linha da posição
	 * @param piece  a peça a ser colocada no tabuleiro
	 */
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	/**
	 * Configuração inicial do tabuleiro de xadrez, posicionando todas as peças.
	 */
	private void initialSetup() {

		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE, this));
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('g', 1, new Knight(board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

		placeNewPiece('a', 8, new Rook(board, Color.BLACK));
		placeNewPiece('b', 8, new Knight(board, Color.BLACK));
		placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('d', 8, new Queen(board, Color.BLACK));
		placeNewPiece('e', 8, new King(board, Color.BLACK, this));
		placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('g', 8, new Knight(board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
}// class ChessMatch
