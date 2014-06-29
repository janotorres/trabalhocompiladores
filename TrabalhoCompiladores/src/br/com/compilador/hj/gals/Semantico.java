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

	private int line;

	private String lexeme;

	public Semantico(String nomePrograma) {
		pilha = new Stack<Classes>();
		tabelaSimbolos = new Hashtable<String, Classes>();
		identificadores = new ArrayList<String>();
		codigo = new StringBuilder();
		program = nomePrograma;
		pilhaRotulos = new Stack<String>();
		contadorRotulos = 0;
		contadorAtribuicao = 0;
		line = 0;
		lexeme = "";
	}

	public String getCodigo() {
		return codigo.toString();
	}

	private enum Classes {
		INT64("int64", "ldc.i8", "Int64", "constante integer"), FLOAT64(
				"float64", "ldc.r8", "Double", "constante float"), STRING(
				"string", "ldstr", null, "constante string"), BOOL("bool",
				"ldc.i4", "Boolean", "constante lógica"), PROGRAM("program",
				null, null, null);

		private String codigoWrite;
		private String codigoPilha;
		private String codigoSystem;
		private String nomeTipo;

		private Classes(String codigoWrite, String codigoPilha,
				String codigoSystem, String nomeTipo) {
			this.codigoWrite = codigoWrite;
			this.codigoPilha = codigoPilha;
			this.codigoSystem = codigoSystem;
			this.nomeTipo = nomeTipo;
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

		public String getNomeTipo() {
			return this.nomeTipo;
		}

	}

	public void executeAction(int action, Token token) throws SemanticError {
		if (token != null) {
			this.line = token.getLine();
			this.lexeme = token.getLexeme();
		}

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
			action5();
			break;
		case 6:
			action6();
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
			action20();
			break;
		case 21:
			action21();
			break;
		case 22:
			action22();
			break;
		case 23:
			// fator positivo, não é necessária uma ação.
			break;
		case 24:
			action24();
			break;
		case 25:
			action25();
			break;
		case 26:
			action26();
			break;
		case 27:
			action27();
			break;
		case 28:
			action28();
			break;
		case 29:
			action29();
			break;
		case 30:
			action30(token.getClasse());
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

	private void action1() throws SemanticError {
		Classes termo1 = pilha.pop();
		Classes termo2 = pilha.pop();
		if (termo1.equals(Classes.INT64)) {
			if (termo2.equals(Classes.INT64)) {
				pilha.push(Classes.INT64);
			} else if (termo2.equals(Classes.FLOAT64)) {
				pilha.push(Classes.FLOAT64);
			} else {
				throw new SemanticError(getMessageLine()
						+ "tipos incompatíveis em comando de soma ("
						+ termo1.getNomeTipo() + ", " + termo2.getNomeTipo()
						+ ").");
			}
		} else if (termo1.equals(Classes.FLOAT64)) {
			if ((termo2.equals(Classes.INT64))
					|| (termo2.equals(Classes.FLOAT64))) {
				pilha.push(Classes.FLOAT64);
			} else {
				throw new SemanticError(getMessageLine()
						+ "tipos incompatíveis em comando de soma ("
						+ termo1.getNomeTipo() + ", " + termo2.getNomeTipo()
						+ ").");
			}
		} else {
			throw new SemanticError(getMessageLine()
					+ "tipos incompatíveis em comando de soma ("
					+ termo1.getNomeTipo() + ", " + termo2.getNomeTipo() + ").");
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
				throw new SemanticError(getMessageLine()
						+ "tipos incompatíveis em comando de subtração ("
						+ termo1.getNomeTipo() + ", " + termo2.getNomeTipo()
						+ ").");
			}
		} else if (termo1.equals(Classes.FLOAT64)) {
			if ((termo2.equals(Classes.INT64))
					|| (termo2.equals(Classes.FLOAT64))) {
				pilha.push(Classes.FLOAT64);
			} else {
				throw new SemanticError(getMessageLine()
						+ "tipos incompatíveis em comando de subtração ("
						+ termo1.getNomeTipo() + ", " + termo2.getNomeTipo()
						+ ").");
			}
		} else {
			throw new SemanticError(getMessageLine()
					+ "tipos incompatíveis em comando de subtração ("
					+ termo1.getNomeTipo() + ", " + termo2.getNomeTipo() + ").");
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
				throw new SemanticError(getMessageLine()
						+ "tipos incompatíveis em comando de multiplicação ("
						+ termo1.getNomeTipo() + ", " + termo2.getNomeTipo()
						+ ").");
			}
		} else if (termo1.equals(Classes.FLOAT64)) {
			if ((termo2.equals(Classes.INT64))
					|| (termo2.equals(Classes.FLOAT64))) {
				pilha.push(Classes.FLOAT64);
			} else {
				throw new SemanticError(getMessageLine()
						+ "tipos incompatíveis em comando de multiplicação ("
						+ termo1.getNomeTipo() + ", " + termo2.getNomeTipo()
						+ ").");
			}
		} else {
			throw new SemanticError(getMessageLine()
					+ "tipos incompatíveis em comando de multiplicação ("
					+ termo1.getNomeTipo() + ", " + termo2.getNomeTipo() + ").");
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
			throw new SemanticError(getMessageLine()
					+ "tipos incompatíveis em comando de divisão ("
					+ termo1.getNomeTipo() + ", " + termo2.getNomeTipo() + ").");
		}
	}

	private void action5() {
		pilha.push(Classes.INT64);
		codigo.append(Classes.INT64.getCodigoPilha() + " " + lexeme + "\n");
	}

	private void action6() {
		pilha.push(Classes.FLOAT64);
		codigo.append(Classes.FLOAT64.getCodigoPilha() + " "
				+ lexeme.replace(",", ".") + "\n");
	}

	private void action7() throws SemanticError {
		Classes tipoPilha = pilha.pop();
		pilha.push(tipoPilha);

		if (tipoPilha == Classes.INT64 || tipoPilha == Classes.FLOAT64) {
			codigo.append(Classes.INT64.getCodigoPilha() + " -1\n");
			codigo.append("mul\n");
		} else {
			throw new SemanticError(
					getMessageLine()
							+ "tipos incompatíveis em expressão aritmética. Esperada constante integer ou constante float, encontrada "
							+ tipoPilha.getNomeTipo());
		}
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
			throw new SemanticError(
					getMessageLine()
							+ "tipos incompátiveis em expressão lógica. Esperada constante lógica, encontrada "
							+ tipo.getNomeTipo());
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

		if (tipo1 != Classes.BOOL) {
			throw new SemanticError(
					getMessageLine()
							+ "tipos incompatíveis em expressão lógica. Esperada constante lógica, encontrada "
							+ tipo1.getNomeTipo());
		}
		if (tipo2 != Classes.BOOL) {
			throw new SemanticError(
					getMessageLine()
							+ "tipos incompatíveis em expressão lógica. Esperada constante lógica, encontrada "
							+ tipo2.getNomeTipo());
		}

		pilha.push(Classes.BOOL);
		codigo.append("or \n");
	}

	private void action19() throws SemanticError {
		Classes tipo1 = pilha.pop();
		Classes tipo2 = pilha.pop();

		if (tipo1 != Classes.BOOL) {
			throw new SemanticError(
					getMessageLine()
							+ "tipos incompatíveis em expressão lógica. Esperada constante lógica, encontrada "
							+ tipo1.getNomeTipo());
		}
		if (tipo2 != Classes.BOOL) {
			throw new SemanticError(
					getMessageLine()
							+ "tipos incompatíveis em expressão lógica. Esperada constante lógica, encontrada "
							+ tipo2.getNomeTipo());
		}
		pilha.push(Classes.BOOL);
		codigo.append("and \n");
	}

	private void action20() {
		operadorRelacional = lexeme;
	}

	private void action21() throws SemanticError {
		Classes tipo1 = pilha.pop();
		Classes tipo2 = pilha.pop();

		if (tipo1 != tipo2 || (tipo1 == Classes.BOOL || tipo2 == Classes.BOOL)) {
			throw new SemanticError(getMessageLine()
					+ "tipos incompatíveis em expressãor relacional ("
					+ tipo1.getNomeTipo() + "," + tipo2.getNomeTipo() + ")");
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

	private void action22() {
		pilha.push(Classes.STRING);
		codigo.append(Classes.STRING.getCodigoPilha() + " " + lexeme + "\n");
	}

	private void action24() {
		identificadores.clear();
		switch (lexeme) {
		case "integer":
			tipo = Classes.INT64;
			break;
		case "float":
			tipo = Classes.FLOAT64;
			break;
		case "boolean":
			tipo = Classes.BOOL;
			break;
		case "string":
			tipo = Classes.STRING;
			break;
		default:
			break;
		}
	}

	private void action25() {
		identificadores.add(lexeme);
	}

	private void action26() throws SemanticError {
		for (int i = 0; i < identificadores.size(); i++) {
			String id = identificadores.get(i);
			if (tabelaSimbolos.containsKey(id)) {
				throw new SemanticError(getMessageLine() + "identificador ("
						+ id + ") já declarado.");
			}
			tabelaSimbolos.put(id, tipo);
			codigo.append(".locals (" + tipo.getCodigoWrite() + " " + id
					+ ") \n");
			contadorAtribuicao++;
		}
		identificadores.clear();
	}

	private void action27() throws SemanticError {
		for (int i = 0; i < identificadores.size(); i++) {
			String id = identificadores.get(i);
			if (!tabelaSimbolos.containsKey(id)) {
				throw new SemanticError(getMessageLine() + "identificador ("
						+ id + ") não declarado");
			}
			tipo = tabelaSimbolos.get(id);
			if (tipo == Classes.BOOL) {
				throw new SemanticError(
						getMessageLine()
								+ "Não é permitida constante lógica em comando de entrada.");
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

	private void action28() throws SemanticError {
		String id = lexeme;
		if (!tabelaSimbolos.containsKey(id)) {
			throw new SemanticError(getMessageLine() + "identificador (" + id
					+ ") não declarado");
		}

		tipo = tabelaSimbolos.get(id);

		pilha.push(tipo);
		codigo.append("ldloc " + id + "\n");

	}

	private void action29() throws SemanticError {
		String id = identificadores.remove(identificadores.size() - 1);
		if (!tabelaSimbolos.containsKey(id)) {
			throw new SemanticError(getMessageLine() + "identificador (" + id
					+ ") não declarado");
		}

		tipo = tabelaSimbolos.get(id);

		Classes tipoPilha = pilha.pop();
		if (tipo != tipoPilha) {
			throw new SemanticError(getMessageLine()
					+ "tipos incompatíveis em comando de atribuição ("
					+ tipo.getNomeTipo() + "," + tipoPilha.getNomeTipo()
					+ ")");
		}
		codigo.append("stloc " + id + "\n");

	}

	private void action30(String classe) throws SemanticError {
		List<String> identificadoresReconhecidos = Collections
				.list(tabelaSimbolos.keys());
		int sizeTabelaSimbolos = tabelaSimbolos.size();
		for (int i = sizeTabelaSimbolos - 1; i > (sizeTabelaSimbolos - contadorAtribuicao) - 1; i--) {
			String id = identificadoresReconhecidos.get(i);

			String nomeTipo = tipo.getNomeTipo();
			if (nomeTipo.equals(Classes.BOOL.getNomeTipo())) {
				if (!classe.equals("TRUE") && !classe.equals("FALSE")) {
					throw new SemanticError(getMessageLine()
							+ "tipo incompatíveis em comando de atribuição ("
							+ nomeTipo + "," + classe + ")");
				}
			} else if (!classe.equals(nomeTipo)) {
				throw new SemanticError(
						getMessageLine()
								+ "tipo incompatíveis em comando de atribuição ("
								+ nomeTipo
								+ ","
								+ (classe.equals("TRUE")
										|| classe.equals("FALSE") ? Classes.BOOL
										.getNomeTipo() : classe) + ")");
			}
			codigo.append(tipo.getCodigoPilha() + " " + lexeme + "\n");
			codigo.append("stloc " + id + "\n");
		}
	}

	private void action31() throws SemanticError {
		Classes tipoPilha = pilha.pop();
		pilha.push(tipoPilha);
		
		if (tipoPilha != Classes.BOOL) {
			throw new SemanticError(getMessageLine() + "tipos incompatíveis no comando de seleção. Esperada " + Classes.BOOL.getNomeTipo() + ", encontrada " + tipoPilha.getNomeTipo());
		}		
		
		String rotulo = "r" + contadorRotulos++;
		pilhaRotulos.push(rotulo);
		codigo.append("brfalse " + rotulo + "\n");

	}

	private void action32() {
		codigo.append(pilhaRotulos.pop() + ": \n");
	}

	private void action33() {
		String rotulo = pilhaRotulos.pop();
		String rotuloBr = "r" + contadorRotulos++;
		pilhaRotulos.push(rotuloBr);

		codigo.append("br " + rotuloBr + "\n");
		codigo.append(rotulo + ": \n");

	}

	private void action34() {
		String rotulo = "r" + contadorRotulos++;
		codigo.append(rotulo + ": \n");
		pilhaRotulos.push(rotulo);
	}

	private void action35() throws SemanticError {
		Classes tipoPilha = pilha.pop();
		pilha.push(tipoPilha);
		
		if (tipoPilha != Classes.BOOL) {
			throw new SemanticError(getMessageLine() + "tipos incompatíveis no comando de repetição. Esperada " + Classes.BOOL.getNomeTipo() + ", encontrada " + tipoPilha.getNomeTipo());
		}
		String rotulo = pilhaRotulos.pop();
		codigo.append("brtrue " + rotulo + "\n");
	}

	private String getMessageLine() {
		return "Erro na linha " + line + " - ";
	}
}