package util;

public interface Messages {
	
	String STR_POSITION_TOSTRING = "%d, %d";
	String STR_CHESS_POSITION_TOSTRING = "%c%d";
	
	String MSG_ERROR_CREATED_BOARD = "Error creating board: There must be at least 1 row and 1 column";
	String MSG_ERROR_INVALID_POSITION = "Position not on the board";
	String MSG_ERROR_ALREADY_PIECE = "There is already a piece on position %s";
	String MSG_ERROR_NOT_CHECK = "You can't put yourself in check";
	String MSG_ERROR_NOT_PIECE_PROMOTED = "There is no piece to be promoted";
	String MSG_ERROR_NOT_PIECE_SOURCE = "There is no piece on source position";
	String MSG_ERROR_PIECE_NOT_YOURS = "The chosen piece is not yours";
	String MSG_ERROR_NOT_POSSIBLE_MOVES = "There is no possible moves for the chosen piece";
	String MSG_ERROR_CANT_MOVE_TOPOSITION = "The chosen piece can't move to target position";
	String MSG_ERROR_KING_COLOR = "There is no %s King on the board";
	String MSG_ERROR_INSTANCE_CHESS_PIECE = "Error instantiating ChessPosition. Valid values are from a1 to h8.";
	String MSG_ERROR_INVALID_VALUE = "Invalid value!";
	String MSG_ERROR_READING_POSITION = "Error reading ChessPosition. Valid values are from a1 to h8";
	
    String MSG_ENTER_PROMOTION_PIECE = "Enter piece for promotion (B/N/R/Q): ";
    String MSG_SOURCE_PROMPT = "Source: ";
    String MSG_TARGET_PROMPT = "Target: ";
    String MSG_TURN_PROMPT = "Turn: ";
    String MSG_WAITING_PLAYER = "Waiting player: ";
    
    String CHECK = "CHECK";
    String CHECK_MATE = "CHECKMATE!";
    String WINNER = "Winner: ";
    
    String TAB = "\t";
    String SPACE = " ";
    String TWO_DOTS = ": ";
    String COLUMN_HEADERS = "\t  a b c d e f g h";
    String WITHOUT_PIECE = "-";
    String CAPTURED_PIECES = "Captured Pieces";
    
    
    String ANSI_CLEAR_SCREEN = "\033[H\033[2J";
    
	String ANSI_RESET = "\u001B[0m";
	String ANSI_BLACK = "\u001B[30m";
	String ANSI_RED = "\u001B[31m";
	String ANSI_GREEN = "\u001B[32m";
	String ANSI_YELLOW = "\u001B[33m";
	String ANSI_BLUE = "\u001B[34m";
	String ANSI_PURPLE = "\u001B[35m";
	String ANSI_CYAN = "\u001B[36m";
	String ANSI_WHITE = "\u001B[37m";

	String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	String ANSI_RED_BACKGROUND = "\u001B[41m";
	String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
}//interface Messages
