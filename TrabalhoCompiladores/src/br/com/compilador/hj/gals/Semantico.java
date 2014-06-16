package br.com.compilador.hj.gals;

import java.util.Stack;

import sun.font.LayoutPathImpl.EndType;

public class Semantico implements Constants {

	private StringBuilder codigo;

	private Stack<Classes> pilha;

	private enum Classes {
		INT64("int64", "ldc.i8"), FLOAT64("float64", "ldc.i8"), STRING(
				"string", "ldc.i8"), BOOL("bool", "ldc.i4");

		private String codigoWrite;
		private String codigoPilha;

		private Classes(String codigoWrite, String codigoPilha) {
			this.codigoWrite = codigoWrite;
			this.codigoPilha = codigoPilha;
		}

		public String getCodigoWrite() {
			return this.codigoWrite;
		}

		public String getCodigoPilha() {
			return this.codigoPilha;
		}

	}

	public void executeAction(int action, Token token) throws SemanticError {
		switch (action) {
		case 1:
			action1();
			break;
		case 2:
			action2();
			break;
		case 3:
			action3();
			break;
		case 4:
			action4();
			break;
		case 5:
			action5(token.getLexeme());
			break;
		case 6:
			action6(token.getLexeme());
			break;
		case 7:
			action7();
			break;
		case 8:
			break;
		case 9:
			break;
		case 10:
			break;
		case 11:
			action11();
			break;
		case 12:
			action12();
			break;
		case 13:
			action13();
			break;
		case 14:
			action14();
			break;
		case 15:
			action15();
			break;
		case 16:
			action16();
			break;
		case 17:
			action17();
			break;
		case 18:
			action18();
			break;
		case 19:
			break;
		case 20:
			break;
		case 21:
			break;
		case 22:
			break;
		case 23:
			// fator positivo, não é necessária uma ação.
			break;
		case 24:
			break;
		case 25:
			break;
		case 26:
			break;
		case 27:
			break;
		case 28:
			break;
		case 29:
			break;
		case 30:
			break;
		case 31:
			break;
		case 32:
			break;
		case 33:
			break;
		case 34:
			break;
		case 35:
			break;
		case 36:
			break;
		case 37:
			break;
		case 38:
			break;
		case 39:
			break;
		case 40:
			break;

		}

		System.out.println("Ação #" + action + ", Token: " + token);
	}

	private void action1() throws SemanticError {
		Classes termo1 = pilha.pop();
		Classes termo2 = pilha.pop();
		if (termo1.equals(Classes.INT64)) {
			if (termo2.equals(Classes.INT64)) {
				pilha.push(Classes.INT64);
			} else if (termo2.equals(Classes.FLOAT64)) {
				pilha.push(Classes.FLOAT64);
			} else {
				// TODO
				throw new SemanticError("");
			}
		} else if (termo1.equals(Classes.FLOAT64)) {
			if ((termo2.equals(Classes.INT64))
					|| (termo2.equals(Classes.FLOAT64))) {
				pilha.push(Classes.FLOAT64);
			} else {
				// TODO
				throw new SemanticError("");
			}
		}
		codigo.append("add\n");
	}

	private void action2() throws SemanticError {
		Classes termo1 = pilha.pop();
		Classes termo2 = pilha.pop();
		if (termo1.equals(Classes.INT64)) {
			if (termo2.equals(Classes.INT64)) {
				pilha.push(Classes.INT64);
			} else if (termo2.equals(Classes.FLOAT64)) {
				pilha.push(Classes.FLOAT64);
			} else {
				// TODO
				throw new SemanticError("");
			}
		} else if (termo1.equals(Classes.FLOAT64)) {
			if ((termo2.equals(Classes.INT64))
					|| (termo2.equals(Classes.FLOAT64))) {
				pilha.push(Classes.FLOAT64);
			} else {
				// TODO
				throw new SemanticError("");
			}
		}
		codigo.append("sub\n");
	}

	private void action3() throws SemanticError {
		Classes termo1 = pilha.pop();
		Classes termo2 = pilha.pop();
		if (termo1.equals(Classes.INT64)) {
			if (termo2.equals(Classes.INT64)) {
				pilha.push(Classes.INT64);
			} else if (termo2.equals(Classes.FLOAT64)) {
				pilha.push(Classes.FLOAT64);
			} else {
				// TODO
				throw new SemanticError("");
			}
		} else if (termo1.equals(Classes.FLOAT64)) {
			if ((termo2.equals(Classes.INT64))
					|| (termo2.equals(Classes.FLOAT64))) {
				pilha.push(Classes.FLOAT64);
			} else {
				// TODO
				throw new SemanticError("");
			}
		}
		codigo.append("mul\n");
	}

	private void action4() throws SemanticError {
		Classes termo1 = pilha.pop();
		Classes termo2 = pilha.pop();
		if (termo1 == termo2) {
			pilha.push(termo1);
			codigo.append("div\n");
		} else {
			// TODO
			throw new SemanticError("");
		}
	}

	private void action5(String lexeme) {
		pilha.push(Classes.INT64);
		codigo.append(Classes.INT64.getCodigoPilha() + " " + lexeme + "\n");
	}

	private void action6(String lexeme) {
		pilha.push(Classes.FLOAT64);
		codigo.append(Classes.FLOAT64.getCodigoPilha() + " " + lexeme + "\n");
	}

	private void action7() {
		// TODO fazer verificação de tipos
		codigo.append(Classes.INT64.getCodigoPilha() + " -1\n");
		codigo.append("mul\n");
	}

	private void action11() {
		pilha.push(Classes.BOOL);
		codigo.append(Classes.BOOL.getCodigoPilha() + ".1\n");
	}

	private void action12() {
		pilha.push(Classes.BOOL);
		codigo.append(Classes.BOOL.getCodigoPilha() + ".0\n");
	}

	private void action13() throws SemanticError {
		pilha.push(Classes.BOOL);
		codigo.append(Classes.BOOL.getCodigoPilha() + ".0\n");

		Classes tipo = pilha.pop();
		if (tipo == Classes.BOOL) {
			pilha.push(Classes.BOOL);
			codigo.append(Classes.BOOL.getCodigoPilha() + ".1\n");
			codigo.append("xor\n");
		} else {
			throw new SemanticError("");
		}
	}

	private void action14() {
		Classes tipo = pilha.pop();
		codigo.append("call void [mscorlib]System.Console::Write("
				+ tipo.getCodigoWrite() + ")");
	}

	private void action15() {
		codigo.append(".assembly extern mscorlib {} \n"
				+ ".assembly teste_01{} \n" + ".module teste_01.exe \n"
				+ ".class public teste_01{ \n"
				+ ".method static public void principal() \n"
				+ "{ .entrypoint \n");
	}

	private void action16() {
		codigo.append("ret \n" + " } \n" + "}");
	}

	private void action17() {
		codigo.append("\n");
	}
	
	private void action18() {
		codigo.append("\n");
	}
}