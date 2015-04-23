package parser;

import java.util.Stack;

import util.TokenType;

public class TypeStack {
	
	private Stack<TokenType> stack;
	
	public TypeStack() {
		stack = new Stack<>();
	}
	
	public void push(TokenType tt) {
		stack.push(tt);
	}
	
	public TokenType pop() {
		return stack.pop();
	}
	
	
	public String resolve(TokenType tt) {
		TokenType top = pop();
		TokenType next = pop();
		switch (tt) {
		case MP_PLUS: 
			if (top == TokenType.MP_INTEGER_LIT && next == TokenType.MP_INTEGER_LIT) {
				return "ADDS";
			} else {
				return "ADDSF";
			}
		case MP_MINUS:
			if (top == TokenType.MP_INTEGER_LIT && next == TokenType.MP_INTEGER_LIT) {
				return "SUBS";
			} else {
				return "SUBSF";
			}
		case MP_TIMES:
			if (top == TokenType.MP_INTEGER_LIT && next == TokenType.MP_INTEGER_LIT) {
				return "MULS";
			} else {
				return "MULSF";
			}
		case MP_DIV:
			if (top == TokenType.MP_INTEGER_LIT && next == TokenType.MP_INTEGER_LIT) {
				return "DIVS";
			} else {
				return "DIVSF";
			}
			
		
		default: return null;
			
		}
	}
	

}
