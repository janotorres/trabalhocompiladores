package br.com.compilador.hj.gals;

import java.util.Arrays;

public class Lexico implements Constants {

	private int position;

	private String input;

	private int linha = 1;

	private int linhaBlocoComentario = 1;

	private int lastPositionQuebraLinha;

	private int lastStateGlobal;

	public Lexico() {
		this("");
	}

	public Lexico(String input) {
		setInput(input);
	}

	public void setInput(String input) {
		this.input = input;
		setPosition(0);
	}

	public void setPosition(int pos) {
		position = pos;
	}

	public Token nextToken() throws LexicalError {
		if (!hasInput())
			return null;

		int start = position;
		int state = 0;

		int lastState = 0;
		int endState = -1;
		int end = -1;

		char simbolo = ' ';
		while (hasInput()) {
			lastState = state;
			simbolo = nextChar();
			state = nextState(simbolo, state);
			lastStateGlobal = state;
			if (state < 0)
				break;

			else {
				if (tokenForState(state) >= 0) {
					endState = state;
					end = position;
				}
			}
		}
		if (endState < 0
				|| (endState != state && tokenForState(lastState) == -2)) {
			if (lastState == 0)
				throw new LexicalError("Erro na linha " + linha + " - "
						+ simbolo + " " + SCANNER_ERROR_CUSTOMIZED[lastState],
						start);
			else

				throw new LexicalError(
						"Erro na linha "
								+ (lastState == 21 || lastState == 3 ? linhaBlocoComentario
										: linha) + " - "
								+ SCANNER_ERROR_CUSTOMIZED[lastState], start);
		}

		position = end;

		int token = tokenForState(endState);

		if (token == 0)
			return nextToken();
		else {
			String lexeme = input.substring(start, end);
			token = lookupToken(token, lexeme);
			String classe = null;
			if (token >= 24) {
				classe = SPECIAL_CASES_KEYS[Arrays.asList(SPECIAL_CASES_VALUES)
						.indexOf(token)];
			} else {
				classe = TOKEN_STATE_KEYS[Arrays.asList(TOKEN_STATE).indexOf(
						token)];
			}

			Token novoToken = new Token(token, classe, lexeme, start);
			novoToken.setLine(lastPositionQuebraLinha < position ? linha
					: linha - 1);
			return novoToken;
		}
	}

	private int nextState(char c, int state) {
		int start = SCANNER_TABLE_INDEXES[state];
		int end = SCANNER_TABLE_INDEXES[state + 1] - 1;

		while (start <= end) {
			int half = (start + end) / 2;

			if (SCANNER_TABLE[half][0] == c)
				return SCANNER_TABLE[half][1];
			else if (SCANNER_TABLE[half][0] < c)
				start = half + 1;
			else
				// (SCANNER_TABLE[half][0] > c)
				end = half - 1;
		}

		return -1;
	}

	private int tokenForState(int state) {
		if (state < 0 || state >= TOKEN_STATE.length)
			return -1;

		return TOKEN_STATE[state];
	}

	public int lookupToken(int base, String key) {
		int start = SPECIAL_CASES_INDEXES[base];
		int end = SPECIAL_CASES_INDEXES[base + 1] - 1;

		key = key.toUpperCase();

		while (start <= end) {
			int half = (start + end) / 2;
			int comp = SPECIAL_CASES_KEYS[half].compareTo(key);

			if (comp == 0)
				return SPECIAL_CASES_VALUES[half];
			else if (comp < 0)
				start = half + 1;
			else
				// (comp > 0)
				end = half - 1;
		}

		return base;
	}

	private boolean hasInput() {
		return position < input.length();
	}

	private char nextChar() {
		if (hasInput()) {
			char c = input.charAt(position++);
			if (c == '\n' && lastPositionQuebraLinha < position) {
				lastPositionQuebraLinha = position;
				linha++;
				if (lastStateGlobal != 21 && lastStateGlobal != 3)
					linhaBlocoComentario++;

			}
			return c;
		} else {
			return (char) -1;
		}
	}
}
