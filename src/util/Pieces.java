package util;

public enum Pieces {

	BISHOP("B"),
	KING("K"),
	KNIGHT("N"),
	PAWN("P"),
	QUEEN("Q"),
	ROOK("R");
	
	private String acronym;

	private Pieces(String acronym) {
		this.acronym = acronym;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
	
	public static String[] pieces() {
		
		String[] pieces = new String[Pieces.values().length];
		
		int i = 0;
		for (Pieces p : Pieces.values())
			pieces[i++] = p.getAcronym();
		
		return pieces;
	}
	
}//enum Pieces
