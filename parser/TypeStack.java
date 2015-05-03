package parser;

import java.util.HashMap;
import java.util.Stack;

import util.TokenType;

public class TypeStack {
	
	private Stack<TokenType> stack;
	
	public TypeStack() {
		stack = new Stack<>();

        lookup = new HashMap<TokenType, String>();
        lookup.put(TokenType.MP_PLUS,   "ADDS");
        lookup.put(TokenType.MP_MINUS,  "SUBS");
        lookup.put(TokenType.MP_TIMES,  "MULS");
        lookup.put(TokenType.MP_DIV,    "DIVS");
        lookup.put(TokenType.MP_EQUAL,  "CMPEQS");
        lookup.put(TokenType.MP_LTHAN,  "CMPLTS");
        lookup.put(TokenType.MP_GTHAN,  "CMPGTS");
        lookup.put(TokenType.MP_LEQUAL, "CMPLES");
        lookup.put(TokenType.MP_GEQUAL, "CMPGES");
        lookup.put(TokenType.MP_NEQUAL, "CMPNES");
	}
	
	public void push(TokenType tt) {
		stack.push(tt);
	}
	
	public TokenType pop() {
		return stack.pop();
	}

    HashMap<TokenType, String> lookup;
	
	public String resolve(TokenType tt) {
		TokenType top = pop();
		TokenType next = pop();

        boolean useInt = top == TokenType.MP_INTEGER_LIT && next == TokenType.MP_INTEGER_LIT;
        return lookup.get(tt) + (useInt? "" : "F");
	}
	

}
