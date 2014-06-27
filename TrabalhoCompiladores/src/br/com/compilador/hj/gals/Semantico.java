package br.com.compilador.hj.gals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;

import sun.font.LayoutPathImpl.EndType;

public class Semantico implements Constants {

	private StringBuilder codigo;

	private Classes tipo;

	private Stack<Classes> pilha;

	private Stack<String> pilhaRotulos;

	private int contadorRotulos;

	private Hashtable<String, Classes> tabelaSimbolos;

	private ArrayList<String> identificadores;

	private String operadorRelacional = "";

	private String program;
	
	private int contadorAtribuicao;

	public Semantico(String nomePrograma) {
		pilha = new Stack<Classes>();
		tabelaSimbolos = new Hashtable<String, Classes>();
		identificadores = new ArrayList<String>();
		codigo = new StringBuilder();
		program = nomePrograma;
		pilhaRotulos = new Stack<String>();
		contadorRotulos = 0;
		contadorAtribuicao = 0;
	}

	public String getCodigo() {
		return codigo.toString();
	}

	private enum Classes {
		INT64("int64", "ldc.i8", "Int64"), FLOAT64("float64", "ldc.i8",
				"Double"), STRING("string", "ldstr", null), BOOL("bool",
				"ldc.i4", "Boolean"), PROGRAM("program", null, null);

		private String codigoWrite;
		private String codigoPilha;
		private String codigoSystem;

		private Classes(String codigoWrite, String codigoPilha,
				String codigoSystem) {
			this.codigoWrite = codigoWrite;
			this.codigoPilha = codigoPilha;
			this.codigoSystem = codigoSystem;
		}

		public String getCodigoWrite() {
			return this.codigoWrite;
		}

		public String getCodigoSystem() {
			return this.codigoSystem;
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
			action19();
			break;
		case 20:
			action20(token.getLexeme());
			break;
		case 21:
			action21();
			break;
		case 22:
			action22(token.getLexeme());
			break;
		case 23:
			// fator positivo, não é necessária uma ação.
			break;
		case 24:
			action24(token.getLexeme());
			break;
		case 25:
			action25(token.getLexeme());
			break;
		case 26:
			action26();
			break;
		case 27:
			action27(token.getLine());
			break;
		case 28:
			action28(token.getLexeme());
			break;
		case 29:
			action29();
			break;
		case 30:
			action30(token.getClasse(), token.getLexeme());
			break;
		case 31:
			action31();
			break;
		case 32:
			action32();
			break;
		case 33:
			action33();
			break;
		case 34:
			action34();
			break;
		case 35:
			action35();
			break;
		}

		System.out.println("Ação #" + action + ", Token: " + token);
	}

	private void action35() {
		// TODO verificacao de tipos?
		String rotulo = pilhaRotulos.pop();
		codigo.append("brtrue " + rotulo + "\n");
		
	}

	private void action34() {
		// TODO verificacao de tipos?
		String rotulo = "r" + contadorRotulos++;
		codigo.append(rotulo + ": \n");
		pilhaRotulos.push(rotulo);
	}

	private void action33() {
		String rotulo = pilhaRotulos.pop();
		String rotuloBr = "r" + contadorRotulos++;
		pilhaRotulos.push(rotuloBr);

		// TODO verificacao de tipos?
		codigo.append("br " + rotuloBr + "\n");
		codigo.append(rotulo + ": \n");

	}

	private void action32() {
		// TODO verificacao de tipos?
		codigo.append(pilhaRotulos.pop() + ": \n");

	}

	private void action31() {
		// TODO verificacao de tipos?
		String rotulo = "r" + contadorRotulos++;
		pilhaRotulos.push(rotulo);
		codigo.append("brfalse " + rotulo + "\n");

	}

	private void action30(String lexeme, String classe) throws SemanticError {		
		List<String> identificadoresReconhecidos = Collections.list(tabelaSimbolos.keys());
		int sizeTabelaSimbolos = tabelaSimbolos.size();
		for (int i = sizeTabelaSimbolos -1; i != (sizeTabelaSimbolos - contadorAtribuicao); i--) {
			String id = identificadoresReconhecidos.get(i);

			switch (tipo) {
			case INT64:
				if (classe != "constante integer") {
					throw new SemanticError(
							"Tipo incompatíveis em comando de atribuição (constante integer, "
									+ classe + " )");
				}
				codigo.append(tipo.getCodigoPilha() + " " + lexeme + "\n");
				break;
			case FLOAT64:
				if (classe != "constante float") {
					throw new SemanticError(
							"Tipo incompatíveis em comando de atribuição (constante float, "
									+ classe + " )");
				}
				codigo.append(tipo.getCodigoPilha() + " " + lexeme + "\n");
				break;
			case STRING:
				if (classe != "constante string") {
					throw new SemanticError(
							"Tipo incompatíveis em comando de atribuição (constante string, "
									+ classe + " )");
				}
				codigo.append(tipo.getCodigoPilha() + " " + lexeme + "\n");
				break;
			case BOOL:
				if (!classe.equals("TRUE") && !classe.equals("FALSE")) {
					throw new SemanticError(
							"Tipo incompatíveis em comando de atribuição (constante lógica, "
									+ classe + " )");
				}
				codigo.append(tipo.getCodigoPilha() + " "
						+ (lexeme.equals("true") ? ".1" : ".0") + "\n");
				break;
			default:
				break;
			}

			codigo.append("stloc " + id + "\n");
		}
	}

	private void action22(String lexeme) {
		pilha.push(Classes.STRING);
		codigo.append(Classes.STRING.getCodigoPilha() + " " + lexeme +  "\n");
	}

	private void action21() throws SemanticError {

		Classes tipo1 = pilha.pop();
		Classes tipo2 = pilha.pop();

		if (tipo1 != tipo2) {
			throw new SemanticError("");
		} else {
			pilha.push(Classes.BOOL);
		}

		switch (operadorRelacional) {
		case "==":
			codigo.append("ceq \n");
			break;
		case ">":
			codigo.append("cgt \n");
			break;
		case "<":
			codigo.append("clt \n");
			break;
		case ">=":
			codigo.append("cgt \n");
			codigo.append("ldc.i4 0 \n");
			codigo.append("ceq \n");
			break;
		case "<=":
			codigo.append("clt \n");
			codigo.append("ldc.i4 0 \n");
			codigo.append("ceq \n");
			break;
		case "!=":
			codigo.append("ceq \n");
			codigo.append("ldc.i4 0 \n");
			codigo.append("ceq \n");
			break;
		default:
			break;
		}

	}

	private void action20(String lexeme) {
		operadorRelacional = lexeme;
	}

	private void action29() throws SemanticError {
		String id = identificadores.remove(identificadores.size() - 1);
		if (!tabelaSimbolos.containsKey(id)) {
			throw new SemanticError("");
		}

		tipo = tabelaSimbolos.get(id);

		if (tipo == Classes.PROGRAM) {
			throw new SemanticError("");
		}

		Classes tipoPilha = pilha.pop();
		if (tipo != tipoPilha) {
			throw new SemanticError("");
		}
		codigo.append("stloc " + id + "\n");

	}

	private void action28(String lexeme) throws SemanticError {
		String id = lexeme;
		if (!tabelaSimbolos.containsKey(id)) {
			throw new SemanticError("");
		}

		tipo = tabelaSimbolos.get(id);

		if (tipo == Classes.PROGRAM) {
			throw new SemanticError("");
		}

		pilha.push(tipo);
		codigo.append("ldloc " + id + "\n");

	}

	private void action27(int line) throws SemanticError {
		for (int i = 0; i < identificadores.size(); i++) {
			String id = identificadores.get(i);
			if (!tabelaSimbolos.containsKey(id)) {
				throw new SemanticError("Erro na linha " + line
						+ "- identificador (" + id + ") não declarado");
			}
			tipo = tabelaSimbolos.get(id);

			if (tipo == Classes.PROGRAM) {
				throw new SemanticError("");
			}

			codigo.append("call string [mscorlib]System.Console::ReadLine() \n");
			if (tipo != Classes.STRING) {
				codigo.append("call " + tipo.getCodigoWrite()
						+ " [mscorlib]System." + tipo.getCodigoSystem()
						+ "::Parse(string) \n");
			}
			codigo.append("stloc " + id + "\n");
		}

		identificadores.clear();

	}

	private void action26() throws SemanticError {
		for (int i = 0; i < identificadores.size(); i++) {
			String id = identificadores.get(i);
			if (tabelaSimbolos.containsKey(id)) {
				throw new SemanticError("");
			}
			tabelaSimbolos.put(id, tipo);
			// TODO verificar se codigo está correto, espeçamento...
			codigo.append(".locals (" + tipo.getCodigoWrite() + " " + id
					+ ") \n");
			contadorAtribuicao++;
		}

		identificadores.clear();

	}

	private void action25(String lexeme) {
		identificadores.add(lexeme);
	}

	private void action24(String lexeme) {
		identificadores.clear();
		switch (lexeme) {
		case "integer":
			tipo = Classes.INT64;
			break;
		case "float":
			tipo = Classes.FLOAT64;
			break;
		// TODO precisa para bool e string?
		case "bool":
			tipo = Classes.BOOL;
			break;
		case "string":
			tipo = Classes.STRING;
			break;
		default:
			break;
		}
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
				+ tipo.getCodigoWrite() + ") \n");
	}

	private void action15() {
		codigo.append(".assembly extern mscorlib {} \n" + ".assembly "
				+ program + "{} \n" + ".module " + program + ".exe \n\n"
				+ ".class public " + program + "{ \n"
				+ ".method static public void _principal() \n"
				+ "{ .entrypoint \n");
	}

	private void action16() {
		codigo.append("ret \n" + " } \n" + "}");
	}

	private void action17() {
		codigo.append("ldstr \"\\n\" \n");
		codigo.append("call void [mscorlib]System.Console::Write(string) \n");
	}

	private void action18() throws SemanticError {
		Classes tipo1 = pilha.pop();
		Classes tipo2 = pilha.pop();

		if (tipo1 == Classes.BOOL && tipo2 == Classes.BOOL) {
			pilha.push(Classes.BOOL);
			codigo.append("or \n");
		} else {
			throw new SemanticError("Tipos incompatíveis para expressão lógica");
		}
	}

	private void action19() throws SemanticError {
		Classes tipo1 = pilha.pop();
		Classes tipo2 = pilha.pop();

		if (tipo1 == Classes.BOOL && tipo2 == Classes.BOOL) {
			pilha.push(Classes.BOOL);
			codigo.append("and \n");
		} else {
			throw new SemanticError("Tipos incompatíveis para expressão lógica");
		}
	}
}