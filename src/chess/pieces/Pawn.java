package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	private ChessMatch chessMatch;
	
    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "P";
    }

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
	
    private void checkSpecialMovePassant(boolean[][] matrix, int row, int pace) {
    	
    	if (position.getRow() == row) {
    		
        	Position left = new Position(position.getRow(), position.getColumn() - 1);
        	
        	if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable())
        		matrix[left.getRow() + pace][left.getColumn()] = true;
        	
        	Position right = new Position(position.getRow(), position.getColumn() + 1);
        	
        	if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable())
        		matrix[right.getRow() + pace][right.getColumn()] = true;
        }
	}

    private void checkCaptureMoves(boolean[][] matrix, Position currentPos, int forwardStep, int sideStep) {
        
    	Position capture = new Position(currentPos.getRow() + forwardStep, currentPos.getColumn() + sideStep);
        
        if (canCapture(capture))
            matrix[capture.getRow()][capture.getColumn()] = true;
    }

    private boolean canMove(Position position) {
        return getBoard().positionExists(position) && !getBoard().thereIsAPiece(position);
    }

    private boolean canCapture(Position position) {
        return getBoard().positionExists(position) && isThereOpponentPiece(position);
    }
}//class Pawn