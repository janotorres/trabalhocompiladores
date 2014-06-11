package br.com.compilador.hj.gals;

import java.util.Stack;

import sun.font.LayoutPathImpl.EndType;

public class Semantico implements Constants {
	
	private StringBuilder codigo;
	
	private Stack<Classes> pilha;
	
	private enum Classes{
		INT64("int64", "ldc.i8"),
		FLOAT64("float64", "ldc.i8"),
		STRING("string", "ldc.i8"),
		BOOL("bool", "ldc.i4");
		
		private String codigoWrite;
		private String codigoPilha;
		
		private Classes(String codigoWrite, String codigoPilha){
			this.codigoWrite = codigoWrite;
			this.codigoPilha = codigoPilha;
		}
		
		public String getCodigoWrite(){
			return this.codigoWrite;
		}
		
		public String getCodigoPilha(){
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
			break;
		case 4:
			break;
		case 5:
			action5(token.getLexeme());
			break;
		case 6:
			break;
		case 7:
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
			break;
		case 14:
			break;
		case 15:
			break;
		case 16:
			break;
		case 17:
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
	
	private void action12() {
		pilha.push(Classes.BOOL);
		codigo.append(Classes.BOOL.getCodigoPilha() + ".0");
	}

	private void action11() {
		pilha.push(Classes.BOOL);
		codigo.append(Classes.BOOL.getCodigoPilha() + ".1");		
	}

	private void action1() throws SemanticError{
		Classes termo1 = pilha.pop();
		Classes termo2 = pilha.pop();
		if (termo1.equals(Classes.INT64)){
			if (termo2.equals(Classes.INT64)){
				pilha.push(Classes.INT64);
			} else if (termo2.equals(Classes.FLOAT64)){
				pilha.push(Classes.FLOAT64);
			} else {
				//TODO
				throw new SemanticError("");
			}
		} else if (termo1.equals(Classes.FLOAT64)){
			if ((termo2.equals(Classes.INT64)) || (termo2.equals(Classes.FLOAT64))){
				pilha.push(Classes.FLOAT64);
			} else {
				//TODO
				throw new SemanticError("");
			}
		}
		codigo.append("add\n");
	}
	
	private void action2() throws SemanticError{
		Classes termo1 = pilha.pop();
		Classes termo2 = pilha.pop();
		if (termo1.equals(Classes.INT64)){
			if (termo2.equals(Classes.INT64)){
				pilha.push(Classes.INT64);
			} else if (termo2.equals(Classes.FLOAT64)){
				pilha.push(Classes.FLOAT64);
			} else {
				//TODO
				throw new SemanticError("");
			}
		} else if (termo1.equals(Classes.FLOAT64)){
			if ((termo2.equals(Classes.INT64)) || (termo2.equals(Classes.FLOAT64))){
				pilha.push(Classes.FLOAT64);
			} else {
				//TODO
				throw new SemanticError("");
			}
		}
		codigo.append("sub\n");
	}
	
	private void action5(String lexeme){
		pilha.push(Classes.INT64);
		codigo.append(Classes.INT64.getCodigoPilha() + " " + lexeme);
	}
}