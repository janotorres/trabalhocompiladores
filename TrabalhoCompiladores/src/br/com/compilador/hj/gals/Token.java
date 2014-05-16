package br.com.compilador.hj.gals;

public class Token {
	
	private String classe;
	
	private String lexeme;
	
	private int position;

	public Token(String classe, String lexeme, int position) {
		this.classe = classe;
		this.lexeme = lexeme;
		this.position = position;
	}

	public final String getClasse() {
		return classe;
	}

	public final String getLexeme() {
		return lexeme;
	}

	public final int getPosition() {
		return position;
	}

	public String toString() {
		return classe + " ( " + lexeme + " ) @ " + position;
	};
}