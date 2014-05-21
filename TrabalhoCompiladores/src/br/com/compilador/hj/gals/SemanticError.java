package br.com.compilador.hj.gals;

public class SemanticError extends AnalysisError {
	
	private static final long serialVersionUID = 1L;

	public SemanticError(String msg, int position) {
		super(msg, position);
	}

	public SemanticError(String msg) {
		super(msg);
	}
}
