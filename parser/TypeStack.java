package parser;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Stack;

import util.CodeChunk;
import util.Token;
import util.TokenType;

public class TypeStack {

	private ArrayDeque<Token> stack;
    private HashMap<TokenType, String> lookup;

	public TypeStack() {
		stack = new ArrayDeque<>();

        lookup = new HashMap<TokenType, String>();
        lookup.put(TokenType.MP_PLUS,   "ADDS");
        lookup.put(TokenType.MP_MINUS,  "SUBS");
        lookup.put(TokenType.MP_TIMES,  "MULS");
        lookup.put(TokenType.MP_DIV,    "DIVS");
        lookup.put(TokenType.MP_FLOAT_DIVIDE, "DIVS");
        lookup.put(TokenType.MP_EQUAL,  "CMPEQS");
        lookup.put(TokenType.MP_LTHAN,  "CMPLTS");
        lookup.put(TokenType.MP_GTHAN,  "CMPGTS");
        lookup.put(TokenType.MP_LEQUAL, "CMPLES");
        lookup.put(TokenType.MP_GEQUAL, "CMPGES");
        lookup.put(TokenType.MP_NEQUAL, "CMPNES");
        lookup.put(TokenType.MP_OR, "ORS");
        lookup.put(TokenType.MP_AND, "ANDS");
        lookup.put(TokenType.MP_MOD, "MODS");
	}

	public void push(Token tt) {
		stack.push(tt);
	}

	public Token pop() {
		if (stack.isEmpty()) {
			System.out.println("Well fuck.");
			System.exit(0);
		}
		return stack.pop();
	}

	public CodeChunk castStuff(TokenType top, TokenType next) {
        CodeChunk cc = new CodeChunk();
        if (next == top) {
            return cc;
        } else {
            cc.append("CASTSF");
            cc.append("SUB SP #1 SP");
            cc.append("CASTSF");
            cc.append("ADD SP #1 SP");
            return cc;
        }
    }
	
	private boolean contains(TokenType[] arr, TokenType op) {
		for (TokenType a : arr) if (a==op) return true;
		return false;
	}
	
	public void errorStuff(Token top, Token next, Token op) {
		TokenType[] bool_ops = new TokenType[] {TokenType.MP_EQUAL,TokenType.MP_OR,TokenType.MP_AND};
		TokenType[] int_ops = new TokenType[] {TokenType.MP_PLUS, TokenType.MP_MINUS, 
											   TokenType.MP_TIMES, TokenType.MP_DIV, TokenType.MP_EQUAL,
											   TokenType.MP_LTHAN, TokenType.MP_GTHAN, TokenType.MP_LEQUAL,
											   TokenType.MP_GEQUAL, TokenType.MP_NEQUAL, TokenType.MP_MOD, TokenType.MP_FLOAT_DIVIDE};
		
		TokenType[] float_ops = new TokenType[] {TokenType.MP_PLUS, TokenType.MP_MINUS, 
				   TokenType.MP_TIMES, TokenType.MP_EQUAL,
				   TokenType.MP_LTHAN, TokenType.MP_GTHAN, TokenType.MP_LEQUAL,
				   TokenType.MP_GEQUAL, TokenType.MP_NEQUAL, TokenType.MP_FLOAT_DIVIDE};
		TokenType[] correctArr;
		if (top.type == next.type) {
			switch(top.type) {
			case MP_BOOLEAN: correctArr = bool_ops; break;
			case MP_INTEGER: correctArr = int_ops; break;
			case MP_FLOAT: correctArr = float_ops; break;
			default: return;
			}
			if (!contains(correctArr,op.type)) {
				System.out.println(op.type + " is not a valid operation for type " + top.type + " " + op.row + "," + op.col);
				System.exit(0);
			}
		} else {
			if (top.type == TokenType.MP_BOOLEAN || next.type == TokenType.MP_BOOLEAN) {
				System.out.println("Saw a " + top + " and " + next +". Cannot operate on these types. " + op.row + "," + op.col);
				System.exit(0);
			}
			//one of them is a float
			if (op.type == TokenType.MP_DIV) {
				System.out.println("Cannot use DIV on floats or fixed types. " + op.row +","+ op.col);
				System.exit(0);
			}
			//both are numeric types, and we just need to check the right side to determine afterwards to cast shit.
			//There is no error.
			
		}
	}

    public CodeChunk resolve(Token tt) {
		Token top = pop();
		Token next = pop();
		errorStuff(top, next, tt);
        CodeChunk cc = castStuff(top.type,next.type);

        if (lookup.get(tt.type).equals("ORS") || lookup.get(tt.type).equals("ANDS") || lookup.get(tt.type).equals("MODS")) {
            cc.append(lookup.get(tt.type));
            push(new Token(TokenType.MP_INTEGER, "", tt.col, tt.row));
        } else {
            boolean useInt = top.type == TokenType.MP_INTEGER && next.type == TokenType.MP_INTEGER;
            cc.append(lookup.get(tt.type) + (useInt? "" : "F"));
            push(new Token(useInt ? TokenType.MP_INTEGER : TokenType.MP_FLOAT, "", tt.col,tt.row));
        }

        return cc;
	}
}
