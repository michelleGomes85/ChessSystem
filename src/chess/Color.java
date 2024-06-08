package chess;

/**
 * Enumeração que representa as cores das peças de xadrez.
 */
public enum Color {
	
	BLACK("Black"),
	WHITE("White");
	
	private String title;
	
	private Color(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}//enum Color
