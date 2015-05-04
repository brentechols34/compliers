package parser;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Stack;

import util.CodeChunk;
import util.TokenType;

public class TypeStack {

	private ArrayDeque<TokenType> stack;
    private HashMap<TokenType, String> lookup;

	public TypeStack() {
		stack = new ArrayDeque<>();

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
        lookup.put(TokenType.MP_OR, "ORS");
        lookup.put(TokenType.MP_AND, "ANDS");
        lookup.put(TokenType.MP_MOD, "MODS");
	}

	public void push(TokenType tt) {
		if (tt == TokenType.MP_INTEGER) {
			stack.push(TokenType.MP_INTEGER_LIT);
		} else {
			stack.push(tt);
		}
	}

	public TokenType pop() {
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

    public CodeChunk resolve(TokenType tt) {
		TokenType top = pop();
		TokenType next = pop();
        CodeChunk cc = castStuff(top,next);


        if (lookup.get(tt).equals("ORS") || lookup.get(tt).equals("ANDS") || lookup.get(tt).equals("MODS")) {
            cc.append(lookup.get(tt));
            push(TokenType.MP_INTEGER);
        } else {
            boolean useInt = top == TokenType.MP_INTEGER_LIT && next == TokenType.MP_INTEGER_LIT;
            cc.append(lookup.get(tt) + (useInt? "" : "F"));
            push(useInt ? TokenType.MP_INTEGER_LIT : TokenType.MP_FLOAT_LIT);
        }

        if (lookup.get(tt)==null){ System.out.println("YO BITCHES " + tt.name()); System.exit(0);}


        return cc;
	}
}
