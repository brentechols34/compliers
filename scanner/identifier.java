package scanner;

import util.TokenType;
public class identifier implements DFAS { 
	public identifier() {
		currentState = "a";
	}
	private String currentState;

	public boolean rejected(char c) {
		switch(currentState) {
		case "a": currentState = a(c); break;
		case "c": currentState = c(c); break;
		case "b": currentState = b(c); break;
		case "d": currentState = d(c); break;
		}
		if (currentState == null) return true;
		return false;
	}
	public static String a(char c) {
		switch(c) {
		case 'A': return "c";
		case 'B': return "c";
		case 'C': return "c";
		case 'D': return "c";
		case 'E': return "c";
		case 'F': return "c";
		case 'G': return "c";
		case 'H': return "c";
		case 'I': return "c";
		case 'J': return "c";
		case 'K': return "c";
		case 'L': return "c";
		case 'M': return "c";
		case 'N': return "c";
		case 'O': return "c";
		case 'P': return "c";
		case 'Q': return "c";
		case 'R': return "c";
		case 'S': return "c";
		case 'T': return "c";
		case 'U': return "c";
		case 'V': return "c";
		case 'W': return "c";
		case 'X': return "c";
		case 'Y': return "c";
		case 'Z': return "c";
		case '_': return "b";
		case 'a': return "c";
		case 'b': return "c";
		case 'c': return "c";
		case 'd': return "c";
		case 'e': return "c";
		case 'f': return "c";
		case 'g': return "c";
		case 'h': return "c";
		case 'i': return "c";
		case 'j': return "c";
		case 'k': return "c";
		case 'l': return "c";
		case 'm': return "c";
		case 'n': return "c";
		case 'o': return "c";
		case 'p': return "c";
		case 'q': return "c";
		case 'r': return "c";
		case 's': return "c";
		case 't': return "c";
		case 'u': return "c";
		case 'v': return "c";
		case 'w': return "c";
		case 'x': return "c";
		case 'y': return "c";
		case 'z': return "c";
		default: return null;
		}
	}

	public static String c(char c) {
		switch(c) {
		case '0': return "c";
		case '1': return "c";
		case '2': return "c";
		case '3': return "c";
		case '4': return "c";
		case '5': return "c";
		case '6': return "c";
		case '7': return "c";
		case '8': return "c";
		case '9': return "c";
		case 'A': return "c";
		case 'B': return "c";
		case 'C': return "c";
		case 'D': return "c";
		case 'E': return "c";
		case 'F': return "c";
		case 'G': return "c";
		case 'H': return "c";
		case 'I': return "c";
		case 'J': return "c";
		case 'K': return "c";
		case 'L': return "c";
		case 'M': return "c";
		case 'N': return "c";
		case 'O': return "c";
		case 'P': return "c";
		case 'Q': return "c";
		case 'R': return "c";
		case 'S': return "c";
		case 'T': return "c";
		case 'U': return "c";
		case 'V': return "c";
		case 'W': return "c";
		case 'X': return "c";
		case 'Y': return "c";
		case 'Z': return "c";
		case '_': return "d";
		case 'a': return "c";
		case 'b': return "c";
		case 'c': return "c";
		case 'd': return "c";
		case 'e': return "c";
		case 'f': return "c";
		case 'g': return "c";
		case 'h': return "c";
		case 'i': return "c";
		case 'j': return "c";
		case 'k': return "c";
		case 'l': return "c";
		case 'm': return "c";
		case 'n': return "c";
		case 'o': return "c";
		case 'p': return "c";
		case 'q': return "c";
		case 'r': return "c";
		case 's': return "c";
		case 't': return "c";
		case 'u': return "c";
		case 'v': return "c";
		case 'w': return "c";
		case 'x': return "c";
		case 'y': return "c";
		case 'z': return "c";
		default: return null;
		}
	}

	public static String b(char c) {
		switch(c) {
		case '0': return "c";
		case '1': return "c";
		case '2': return "c";
		case '3': return "c";
		case '4': return "c";
		case '5': return "c";
		case '6': return "c";
		case '7': return "c";
		case '8': return "c";
		case '9': return "c";
		case 'A': return "c";
		case 'B': return "c";
		case 'C': return "c";
		case 'D': return "c";
		case 'E': return "c";
		case 'F': return "c";
		case 'G': return "c";
		case 'H': return "c";
		case 'I': return "c";
		case 'J': return "c";
		case 'K': return "c";
		case 'L': return "c";
		case 'M': return "c";
		case 'N': return "c";
		case 'O': return "c";
		case 'P': return "c";
		case 'Q': return "c";
		case 'R': return "c";
		case 'S': return "c";
		case 'T': return "c";
		case 'U': return "c";
		case 'V': return "c";
		case 'W': return "c";
		case 'X': return "c";
		case 'Y': return "c";
		case 'Z': return "c";
		case 'a': return "c";
		case 'b': return "c";
		case 'c': return "c";
		case 'd': return "c";
		case 'e': return "c";
		case 'f': return "c";
		case 'g': return "c";
		case 'h': return "c";
		case 'i': return "c";
		case 'j': return "c";
		case 'k': return "c";
		case 'l': return "c";
		case 'm': return "c";
		case 'n': return "c";
		case 'o': return "c";
		case 'p': return "c";
		case 'q': return "c";
		case 'r': return "c";
		case 's': return "c";
		case 't': return "c";
		case 'u': return "c";
		case 'v': return "c";
		case 'w': return "c";
		case 'x': return "c";
		case 'y': return "c";
		case 'z': return "c";
		default: return null;
		}
	}

	public static String d(char c) {
		switch(c) {
		case '0': return "c";
		case '1': return "c";
		case '2': return "c";
		case '3': return "c";
		case '4': return "c";
		case '5': return "c";
		case '6': return "c";
		case '7': return "c";
		case '8': return "c";
		case '9': return "c";
		case 'A': return "c";
		case 'B': return "c";
		case 'C': return "c";
		case 'D': return "c";
		case 'E': return "c";
		case 'F': return "c";
		case 'G': return "c";
		case 'H': return "c";
		case 'I': return "c";
		case 'J': return "c";
		case 'K': return "c";
		case 'L': return "c";
		case 'M': return "c";
		case 'N': return "c";
		case 'O': return "c";
		case 'P': return "c";
		case 'Q': return "c";
		case 'R': return "c";
		case 'S': return "c";
		case 'T': return "c";
		case 'U': return "c";
		case 'V': return "c";
		case 'W': return "c";
		case 'X': return "c";
		case 'Y': return "c";
		case 'Z': return "c";
		case 'a': return "c";
		case 'b': return "c";
		case 'c': return "c";
		case 'd': return "c";
		case 'e': return "c";
		case 'f': return "c";
		case 'g': return "c";
		case 'h': return "c";
		case 'i': return "c";
		case 'j': return "c";
		case 'k': return "c";
		case 'l': return "c";
		case 'm': return "c";
		case 'n': return "c";
		case 'o': return "c";
		case 'p': return "c";
		case 'q': return "c";
		case 'r': return "c";
		case 's': return "c";
		case 't': return "c";
		case 'u': return "c";
		case 'v': return "c";
		case 'w': return "c";
		case 'x': return "c";
		case 'y': return "c";
		case 'z': return "c";
		default: return null;
		}
	}

	public boolean isAccepting() {
		return currentState != null && currentState.equals("c");
	}

	public TokenType getType(String lexeme) {
		lexeme = lexeme.toLowerCase();
		switch(lexeme) { // check if it is reserved word
		case "and": return TokenType.MP_AND;
		case "begin": return TokenType.MP_BEGIN;
		case "Boolean": return TokenType.MP_BOOLEAN;
		case "div": return TokenType.MP_DIV;
		case "do": return TokenType.MP_DO;
		case "downto": return TokenType.MP_DOWNTO;
		case "else": return TokenType.MP_ELSE;
		case "end": return TokenType.MP_END;
		case "false": return TokenType.MP_FALSE;
		case "fixed": return TokenType.MP_FLOAT;
		case "float": return TokenType.MP_FLOAT;
		case "for": return TokenType.MP_FOR;
		case "function": return TokenType.MP_FUNCTION;
		case "if": return TokenType.MP_IF;
		case "integer": return TokenType.MP_INTEGER;
		case "mod": return TokenType.MP_MOD;
		case "not": return TokenType.MP_NOT;
		case "or": return TokenType.MP_OR;
		case "procedure": return TokenType.MP_PROCEDURE;
		case "program": return TokenType.MP_PROGRAM;
		case "read": return TokenType.MP_READ;
		case "repeat": return TokenType.MP_REPEAT;
		case "string": return TokenType.MP_STRING;
		case "then": return TokenType.MP_THEN;
		case "true": return TokenType.MP_TRUE;
		case "to": return TokenType.MP_TO;
		case "type": return TokenType.MP_TYPE;
		case "until": return TokenType.MP_UNTIL;
		case "var": return TokenType.MP_VAR;
		case "while": return TokenType.MP_WHILE;
		case "write": return TokenType.MP_WRITE;
		case "writeln": return TokenType.MP_WRITELN;
		default: return TokenType.MP_IDENTIFIER;
		}
	}

}