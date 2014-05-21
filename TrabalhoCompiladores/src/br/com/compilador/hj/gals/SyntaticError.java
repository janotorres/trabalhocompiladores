package br.com.compilador.hj.gals;

public class SyntaticError extends AnalysisError {
	
	private static final long serialVersionUID = 1L;

	public SyntaticError(String msg, int position) {
		super(msg, position);
	}

	public SyntaticError(String msg) {
		super(msg);
	}
}
