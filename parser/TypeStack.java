package parser;

import java.util.ArrayDeque;
import java.util.Stack;

import util.CodeChunk;
import util.TokenType;

public class TypeStack {

	private ArrayDeque<TokenType> stack;

	public TypeStack() {
		stack = new ArrayDeque<>();
	}

	public void push(TokenType tt) {
		if (tt == TokenType.MP_INTEGER) {
			stack.push(TokenType.MP_INTEGER_LIT);
		} else {
			System.out.println(tt);
			stack.push(tt);
		}
	}

	public TokenType pop() {
		return stack.pop();
	}


	public CodeChunk resolve(TokenType tt) {
		TokenType top = pop();
		TokenType next = pop();
		CodeChunk cc = castStuff(top,next);
		switch (tt) {
		case MP_PLUS: 
			if (top == TokenType.MP_INTEGER_LIT && next == TokenType.MP_INTEGER_LIT) {
				cc.append("ADDS");
			} else {
				cc.append("ADDSF");
			}
			break;
		case MP_MINUS:
			if (top == TokenType.MP_INTEGER_LIT && next == TokenType.MP_INTEGER_LIT) {
				cc.append("SUBS");
			} else {
				cc.append("SUBSF");
			}
			break;
		case MP_TIMES:
			if (top == TokenType.MP_INTEGER_LIT && next == TokenType.MP_INTEGER_LIT) {
				cc.append("MULS");
			} else {
				cc.append("MULSF");
			}
			break;
		case MP_DIV:
			if (top == TokenType.MP_INTEGER_LIT && next == TokenType.MP_INTEGER_LIT) {
				cc.append("DIVS");
			} else {
				cc.append("DIVSF");
			}
			break;
		default: return null;
		}
		if (top != TokenType.MP_INTEGER_LIT || next != TokenType.MP_INTEGER_LIT) {
			push(TokenType.MP_FLOAT_LIT);
		} else {
			push(TokenType.MP_INTEGER_LIT);
		}
		return cc;
	}

	public CodeChunk castStuff(TokenType top, TokenType next) {
		CodeChunk cc = new CodeChunk();
		if (next == top) {
			return cc;
		} else {
			cc.append("CASTF");
			cc.append("SUB SP #1 SP");
			cc.append("CASTF");
			cc.append("ADD SP #1 SP");
			return cc;
		}

	}


}
