package SemanticAnalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Stack;
import java.util.ArrayList;

import parser.*;
import util.*;
import util.CodeChunk;

public class SemanticAnalyzer {

    PrintWriter pw;
    Stack<CodeChunk> code;
    SymbolTableController symbolTable;
    ArrayList<RuleApplication> rules;
    ArrayList<Token> tokens;
    ArrayList<CodeChunk> savedChunk;
    TypeStack typeStack;
    HashMap<RuleApplication, Boolean> optionalSignLookup;
    HashMap<RuleApplication, ArrayList<Object>> ruleLookup = new HashMap<RuleApplication, ArrayList<Object>>();
    LabelProvider lp = new LabelProvider();
    Stack<String> labelStack;

    public SemanticAnalyzer(String fname, ArrayList<RuleApplication> rules, ArrayList<Token> tokens, SymbolTableController symbolTable,
            LabelProvider lp)
            throws FileNotFoundException {
        pw = new PrintWriter(new File(fname));
        code = new Stack<>();
        this.rules = rules;
        this.tokens = tokens;
        this.symbolTable = symbolTable;
        this.savedChunk = new ArrayList<>();
        this.typeStack = new TypeStack();
        this.optionalSignLookup = new HashMap<RuleApplication, Boolean>();
        this.ruleLookup = new HashMap<RuleApplication, ArrayList<Object>>();

        this.lp = lp;
        labelStack = new Stack<>();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public CodeChunk Apply(RuleApplication rule, int tokenIndex) {
        CodeChunk cc = new CodeChunk();
        Token token = tokens.get(tokenIndex);
        SymbolTable table = this.symbolTable.getTable(token.val);
        TableEntry entry = null;
        if (table != null) {
            entry = table.getEntry(token.val);
        }

        switch (rule.ruleIndex) {
            case 28:
                SymbolTable topTable = this.symbolTable.getTable();
                int size = topTable.localSize();
                cc.append("MOV SP D" + topTable.getNestingLevel());
                cc.append("ADD SP #" + size + " SP");

                return cc;
            case 81: {

                if (rule.childIndex == 0) {
                    switch (rule.ruleIndex) {
                        case 84:
                            optionalSignLookup.put(rule, new Boolean(true));
                            break;
                        case 85:
                            optionalSignLookup.put(rule, new Boolean(false));
                            break;
                        case 86:
                            optionalSignLookup.put(rule, null);
                            break;
                    }
                }
                if (rule.childIndex == 2) {
                    Boolean isAdding = optionalSignLookup.get(rule);
                    if (isAdding != null && !isAdding.booleanValue()) {
                        return new CodeChunk("NEGS");
                    }
                }
                return null;
            }
            case 55: {//If Statement
                if (rule.childIndex == 2) {
                    cc.append("BRFS " + lp.peekLabel(0) + ":");
                    labelStack.push(lp.nextLabel());
                    return cc;
                }
                if (rule.childIndex == 4) {
                    String label = labelStack.pop();
                    cc.append("BR " + lp.peekLabel(0) + ":");
                    labelStack.push(lp.nextLabel());
                    cc.append(label);
                    return cc;
                }
            }
            case 58: {//Repeat
            	if (rule.childIndex == 1) {
            		cc.append(lp.peekLabel(0)+":");
            		labelStack.push(lp.nextLabel());
            		return cc;
            	}
            	return null;
            }
            case 59:
                if (rule.childIndex == 2) {
                    String endLabel = lp.nextLabel();
                    labelStack.push(endLabel);
                    cc.append(lp.nextLabel() + ":");
                    labelStack.push(lp.peekLabel(-1));
                    cc.append("BRFS " + endLabel);
                    return cc;
                }
            case 60:
                if (!ruleLookup.containsKey(rule)) {
                    ruleLookup.put(rule, new ArrayList<Object>());
                }
                
                ArrayList args = ruleLookup.get(rule);
                if (rule.childIndex == 1) {
                    args.add(token.val); // Control Variable
                    //System.out.println(token.val);
                }
                if (rule.childIndex == 4) {
                    args.add(token.val); // Step Value
                    //System.out.println(token.val + " ####");
                }
                if (rule.childIndex == 5) {
                    String controlVariableLexeme = (String) args.get(0);
                    SymbolTable localTable = this.symbolTable.getTable(controlVariableLexeme);
                    TableEntry localEntry = localTable.getEntry(controlVariableLexeme);

                    cc.append("POP " + localEntry.getSize() + "(D" + localTable.getNestingLevel() + ")");
                    return cc;
                }
                if (rule.childIndex == 6) {
                    labelStack.push(lp.nextLabel());
                    //System.out.println("PUSHED:" + labelStack.peek() + " " + cc);
                    cc.append(labelStack.peek() + ":");

                    //Find the control variable
                    String controlVariableLexeme = (String) args.get(0);
                    SymbolTable localTable = this.symbolTable.getTable(controlVariableLexeme);
                    TableEntry localEntry = localTable.getEntry(controlVariableLexeme);

                    labelStack.push(lp.nextLabel());
                    cc.append("BNE " + localEntry.getSize() + "(D" + localTable.getNestingLevel() + ")" + " -1(SP) " + labelStack.peek());

                    return cc;
                }
                return null;
            default:
                return null;
        }
    }

    public CodeChunk ExitRule(RuleApplication rule, int tokenIndex) {
        CodeChunk cc = new CodeChunk();
        Token token = tokens.get(tokenIndex);
        SymbolTable table = this.symbolTable.getTable(token.val);
        TableEntry entry = null;
        if (table != null) {
            entry = table.getEntry(token.val);
        }
        switch (rule.ruleIndex) {
            case 0:
            	return new CodeChunk("HLT");
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
                switch (entry.getType()) {
                    case MP_BOOLEAN:
                    case MP_INTEGER:
                        return new CodeChunk("RD " + entry.getSize() + "(D" + table.getNestingLevel() + ")");
                    case MP_FLOAT:
                        return new CodeChunk("RDF " + entry.getSize() + "(D" + table.getNestingLevel() + ")");
                    case MP_STRING:
                        return new CodeChunk("RDS " + entry.getSize() + "(D" + table.getNestingLevel() + ")");
                }
            case 48:
                break;
            case 49:
                return new CodeChunk("WRTLN #");
            case 50:
                break;
            case 51:
                break;
            case 52:
                return new CodeChunk("WRTS");
            case 53:
                //System.out.println(entry.getSize() + table.getNestingLevel());
            	cc = new CodeChunk();
            	if ((entry.getType() == TokenType.MP_INTEGER) != (typeStack.pop() == TokenType.MP_INTEGER_LIT)) {
            		if (entry.getType() == TokenType.MP_STRING) {
            			System.out.println("CAN'T CAST STRINGS");
            		}
            		if (entry.getType() == TokenType.MP_INTEGER) {
            			cc.append("CASTSI");
            		}
            		else {
            			cc.append("CASTSF");
            		}
            	}
                cc.append("POP " + entry.getSize() + "(D" + table.getNestingLevel() + ")");
                return cc;
            case 54:
                break;
            case 55:
                cc.append(labelStack.pop());

                return cc;
            case 56:
                break;
            case 57: 
                break;
            case 58: {//Repeat
            	//When we are done, we need to add a branch to the beginning if the top of the stack is 1
            	cc = new CodeChunk("BRTS " + labelStack.pop());
            	return cc;
            }
            case 59:
                //System.out.println(labelStack.peek());
                cc.append("BR " + labelStack.pop());
                cc.append(labelStack.pop() + ":");
                return cc;
            case 60:
                ArrayList args = ruleLookup.get(rule);
                String exitLabel = labelStack.pop();
                String entryLabel = labelStack.pop();

                String adjust = ((String) args.get(1)).toLowerCase().equals( "to") ? "ADD" : "SUB";
                String controlVariableLexeme = (String) args.get(0);
                SymbolTable localTable = this.symbolTable.getTable(controlVariableLexeme);
                TableEntry localEntry = localTable.getEntry(controlVariableLexeme);

                String register = localEntry.getSize() + "(D" + localTable.getNestingLevel() + ")";
                cc.append(adjust + " " + register + " #1 " + register);

                cc.append("BR " + entryLabel);
                cc.append(exitLabel + ":");
                cc.append("SUBS SP #1 SP");

                return cc;
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
                return new CodeChunk(typeStack.resolve(token.type));
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
                return new CodeChunk(typeStack.resolve(token.type));
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
                return new CodeChunk(typeStack.resolve(token.type));
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
                typeStack.push(tokens.get(rule.tokenIndex).type);
                return cc;
            case 99:
                cc = new CodeChunk("PUSH #" + tokens.get(rule.tokenIndex).val);
                typeStack.push(tokens.get(rule.tokenIndex).type);
                return cc;
            case 100:
                cc = new CodeChunk("PUSH #" + tokens.get(rule.tokenIndex).val);
                typeStack.push(tokens.get(rule.tokenIndex).type);
                return cc;
            case 101:
                cc = new CodeChunk("PUSH #1");
                typeStack.push(tokens.get(rule.tokenIndex).type);
                return cc;
            case 102:
                cc = new CodeChunk("PUSH #0");
                typeStack.push(tokens.get(rule.tokenIndex).type);
                return cc;
            case 103:
                return new CodeChunk("NOTS");
            case 104:
                // Do nothing!
                break;
            case 105:
                break;
            case 106:
                break;
            case 107:
                int previousRule = this.rules.get(this.rules.size() - 2).ruleIndex;
                if (previousRule == 53 || previousRule == 47) {
                    return null;
                }
                cc = new CodeChunk("PUSH " + entry.getSize() + "(D" + table.getNestingLevel() + ")");
                typeStack.push(entry.getType());
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
                return null;
        }
        return null;
    }

    public int Undo(RuleApplication rule) {
        switch (rule.ruleIndex) {
            case 28:
            case 59:
            case 81:
                return 1;
            case 55:
            case 60:
                return 2;
        }

        return 0;
    }

    public int UndoExit(RuleApplication rule) {
        CodeChunk cc = new CodeChunk();
        Token token = tokens.get(rule.tokenIndex);
        SymbolTable table = this.symbolTable.getTable(token.val);
        TableEntry entry = null;
        if (table != null) {
            entry = table.getEntry(token.val);
        }
        switch (rule.ruleIndex) {
            case 47:
            case 49:
            case 52:
            case 53:
            case 55:
            case 59:
            case 73:
            case 82:
            case 91:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 107:
                return 1;
        }

        return 0;
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
}
