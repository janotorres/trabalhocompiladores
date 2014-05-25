package br.com.compilador.hj.gals;

public class Token {
	
	private String classe;
	
	private int id;
	
	private String lexeme;
	
	private int position;
	
	private int line;

	public Token(int id, String classe, String lexeme, int position) {
		this.classe = classe;
		this.lexeme = lexeme;
		this.position = position;
		this.id = id;
	}
	
	public final String getClasse() {
		return classe;
	}
	
	public final int getId() {
		return id;
	}

	public final String getLexeme() {
		return lexeme;
	}

	public final int getPosition() {
		return position;
	}
	
	public final int getLine() {
		return line;
	}

	public void setLine(int lineNumber) {
		line = lineNumber;
	}
	
	public String toString() {
		return classe + " ( " + lexeme + " ) @ " + position;
	};
}