package SemanticAnalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.ArrayList;
import parser.RuleApplication;
import parser.SymbolTable;
import parser.TableEntry;
import parser.SymbolTableController;
import util.*;

public class SemanticAnalyzer {

	PrintWriter pw;
	Stack<CodeChunk> code;
    SymbolTableController symbolTable;
	ArrayList<RuleApplication> rules;
    ArrayList<Token> tokens;

	public SemanticAnalyzer(String fname, ArrayList<RuleApplication> rules, ArrayList<Token> tokens, SymbolTableController symbolTable)
			throws FileNotFoundException {
		pw = new PrintWriter(new File(fname));
		code = new Stack<>();
		this.rules = rules;
        this.tokens = tokens;
        this.symbolTable = symbolTable;
	}

	// public CodeChunk convert(ArrayList<Token> tokens,SymbolTable table){
	// Token curToken = tokens.get(0);
	// CodeChunk cc;
	// switch(curToken.type){
	// case MP_INTEGER_LIT:
	// cc = new CodeChunk("PUSH #" + curToken.val);
	// cc.setType(TokenType.MP_INTEGER);
	// return cc;
	// case MP_PLUS:
	// cc = new CodeChunk(convert(tokens,table),convert(tokens,table),new
	// CodeChunk("ADDS"));
	// return cc;
	// case MP_MINUS:
	// cc = new CodeChunk(convert(tokens,table),convert(tokens,table),new
	// CodeChunk("SUBS"));
	// return cc;
	// case MP_IDENTIFIER:
	// TableEntry entry = table.getEntry(curToken.val);
	// cc = new CodeChunk("PUSH " + entry.getSize() + "(D" +
	// table.getNestingLevel() + ")");
	// return cc;
	// case MP_ASSIGN:
	// TableEntry idEntry = table.getEntry(tokens.get(1).val);
	// CodeChunk ccid = new CodeChunk("POP " + idEntry.getSize() + "(D" +
	// table.getNestingLevel() + ")");
	// tokens.remove(1);
	// cc = new CodeChunk(convert(tokens,table),ccid);
	// return cc;
	// default:
	// return null;
	// }
	// }

	public CodeChunk convert(RuleApplication rule) {
		CodeChunk cc = new CodeChunk();
		Token token = tokens.get(rule.tokenIndex);
		TableEntry entry = this.symbolTable.getTable(token.val).getEntry(token.val);
		switch (rule.ruleIndex) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
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
			break;
		case 12:
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
		case 18:
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
		case 41:
			break;
		case 42:
			break;
		case 43:
			break;
		case 44:
			break;
		case 45:
			break;
		case 46:
			break;
		case 47:
			cc = new CodeChunk("RD" + entry.getSize() + "(D" + table.getNestingLevel() + ")");
			return cc;
		case 48:
			break;
		case 49:
			break;
		case 50:
			break;
		case 51:
			break;
		case 52:
			break;
		case 53:
			
			break;
		case 54:
			break;
		case 55:
			break;
		case 56:
			break;
		case 57:
			break;
		case 58:
			break;
		case 59:
			break;
		case 60:
			break;
		case 61:
			break;
		case 62:
			break;
		case 63:
			break;
		case 64:
			break;
		case 65:
			break;
		case 66:
			break;
		case 67:
			break;
		case 68:
			break;
		case 69:
			break;
		case 70:
			break;
		case 71:
			break;
		case 72:
			break;
		case 73:
			break;
		case 74:
			break;
		case 75:
			break;
		case 76:
			break;
		case 77:
			break;
		case 78:
			break;
		case 79:
			break;
		case 80:
			break;
		case 81:
			break;
		case 82:
			break;
		case 83:
			break;
		case 84:
			break;
		case 85:
			break;
		case 86:
			break;
		case 87:
			cc = new CodeChunk("ADDS");
			return cc;
		case 88:
			cc = new CodeChunk("SUBS");
			return cc;
		case 89:
			cc = new CodeChunk("ORS");
			return cc;
		case 90:
			break;
		case 91:
			break;
		case 92:
			break;
		case 93:
			break;
		case 94:
			break;
		case 95:
			break;
		case 96:
			break;
		case 97:
			break;
		case 98:
			cc = new CodeChunk("PUSH #" + tokens.get(rule.tokenIndex).val);
			return cc;
		case 99:
			cc = new CodeChunk("PUSH #" + tokens.get(rule.tokenIndex).val);
			return cc;
		case 100:
			cc = new CodeChunk("PUSH #" + tokens.get(rule.tokenIndex).val);
			return cc;
		case 101:
			cc = new CodeChunk("PUSH #1");
			return cc;
		case 102:
			cc = new CodeChunk("PUSH #0");
			return cc;
		case 103:
			break;
		case 104:
			break;
		case 105:
			break;
		case 106:
			break;
		case 107:
			if(this.rules.get(this.rules.size() - 2).ruleIndex == 53){
				return null;
			}
			cc = new CodeChunk("PUSH " + entry.getSize() + "(D" + table.getNestingLevel() + ")");
			return cc;
		case 108:
			break;
		case 109:	
			break;
		case 110:
			break;
		case 111:
			break;
		case 112:
			break;
		case 113:
			break;
		case 114:
			break;
		default:
			return cc;
		}
		return cc;
	}

    public CodeChunk ExitRule(RuleApplication rule) {
        CodeChunk cc = new CodeChunk();
		Token token = tokens.get(rule.tokenIndex);
		SymbolTable table = this.symbolTable.getTable(token.val);
		TableEntry entry = table.getEntry(token.val);
        switch (rule.ruleIndex) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
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
                break;
            case 12:
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
            case 18:
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
            case 41:
                break;
            case 42:
                break;
            case 43:
                break;
            case 44:
                break;
            case 45:
                break;
            case 46:
                break;
            case 47:
                break;
            case 48:
                break;
            case 49:
                break;
            case 50:
                break;
            case 51:
                break;
            case 52:
                break;
            case 53:
            	cc = new CodeChunk("POP " + entry.getSize() + "(D" + table.getNestingLevel() + ")");
            	return cc;
                break;
            case 54:
                break;
            case 55:
                break;
            case 56:
                break;
            case 57:
                break;
            case 58:
                break;
            case 59:
                break;
            case 60:
                break;
            case 61:
                break;
            case 62:
                break;
            case 63:
                break;
            case 64:
                break;
            case 65:
                break;
            case 66:
                break;
            case 67:
                break;
            case 68:
                break;
            case 69:
                break;
            case 70:
                break;
            case 71:
                break;
            case 72:
                break;
            case 73:
                break;
            case 74:
                break;
            case 75:
                break;
            case 76:
                break;
            case 77:
                break;
            case 78:
                break;
            case 79:
                break;
            case 80:
                break;
            case 81:
                break;
            case 82:
                break;
            case 83:
                break;
            case 84:
                break;
            case 85:
                break;
            case 86:
                break;
            case 87:
                break;
            case 88:
                break;
            case 89:
                break;
            case 90:
                break;
            case 91:
                break;
            case 92:
                break;
            case 93:
                break;
            case 94:
                break;
            case 95:
                break;
            case 96:
                break;
            case 97:
                break;
            case 98:
                break;
            case 99:
                break;
            case 100:
                break;
            case 101:
                break;
            case 102:
                break;
            case 103:
                break;
            case 104:
                break;
            case 105:
                break;
            case 106:
                break;
            case 107:
                break;
            case 108:
                break;
            case 109:
                break;
            case 110:
                break;
            case 111:
                break;
            case 112:
                break;
            case 113:
                break;
            case 114:
                break;
            default:
                return cc;
        }
        return cc;
    }

    public void Undo(RuleApplication rule) {
    }

	public void printToFile() {
		Stack<CodeChunk> reversed = new Stack<>();
		// reverse code stack
		while (!code.isEmpty()) {
			reversed.push(code.pop());
		}
		// print to file
		while (!reversed.isEmpty()) {
			ArrayList<String> toPrint = reversed.pop().uCode;
			for (String s : toPrint) {
				pw.write(s);
			}
		}
	}

	private class CodeChunk {
		ArrayList<String> uCode;
		private TokenType type;

		public CodeChunk() {
			uCode = new ArrayList<>();
		}

		public CodeChunk(String s) {
			uCode = new ArrayList<>();
			uCode.add(s);
		}

		public CodeChunk(CodeChunk... cc) {
			uCode = new ArrayList<>();
			for (CodeChunk c : cc) {
				uCode.addAll(c.uCode);
			}
		}

		public void append(String s) {
			uCode.add(s);
		}

		public void append(CodeChunk c) {
			uCode.addAll(c.uCode);
		}

		public TokenType getType() {
			return type;
		}

		public void setType(TokenType type) {
			this.type = type;
		}
	}
}
