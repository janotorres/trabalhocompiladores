package br.com.compilador.hj.gals;

public interface Constants extends ScannerConstants, ParserConstants {
	
	int EPSILON = 0;
	int DOLLAR = 1;

	int t_identificador = 2;
	int t_integer = 3;
	int t_float = 4;
	int t_string = 5;
	int t_TOKEN_6 = 6; // "+"
	int t_TOKEN_7 = 7; // "-"
	int t_TOKEN_8 = 8; // "*"
	int t_TOKEN_9 = 9; // "/"
	int t_TOKEN_10 = 10; // "="
	int t_TOKEN_11 = 11; // "&&"
	int t_TOKEN_12 = 12; // "||"
	int t_TOKEN_13 = 13; // "!"
	int t_TOKEN_14 = 14; // "=="
	int t_TOKEN_15 = 15; // "!="
	int t_TOKEN_16 = 16; // "<"
	int t_TOKEN_17 = 17; // "<="
	int t_TOKEN_18 = 18; // ">"
	int t_TOKEN_19 = 19; // ">="
	int t_TOKEN_20 = 20; // ","
	int t_TOKEN_21 = 21; // ";"
	int t_TOKEN_22 = 22; // "("
	int t_TOKEN_23 = 23; // ")"
	int t_boolean = 24;
	int t_do = 25;
	int t_else = 26;
	int t_end = 27;
	int t_false = 28;
	int t_pr_float = 29;
	int t_if = 30;
	int t_pr_integer = 31;
	int t_main = 32;
	int t_print = 33;
	int t_println = 34;
	int t_scan = 35;
	int t_pr_string = 36;
	int t_true = 37;
	int t_void = 38;
	int t_while = 39;
	int t_local = 40;
	int t_global = 41;
	int t_return = 42;
}
