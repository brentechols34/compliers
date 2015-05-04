package parser;

import util.Token;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;


/**
 * Created by brentechols on 4/8/15.
 */

public class SymbolTableController {
    ArrayList<Token> tokens;
    ArrayList<String> entries;
    ArrayDeque<SymbolTable> tables;
    String mode;
    int nesting_level;
    LabelProvider lp = new LabelProvider();

    final int PROGRAM_HEADING = 2;
    final int BLOCK = 3;
    final int VARIABLE_DECLARATION = 8;
    final int PROCEDURE_HEADING = 18;
    final int FUNCTION_HEADING = 19;
    final int VARIABLE_PARAMETER_SECTION = 27;
    final int VALUE_PARAMETER_SECTION = 26;
    final int IDENTIFIER_LIST = 112;
    final int IDENTIFIER_TAIL = 113;


    public SymbolTableController(ArrayList<Token> tokens,LabelProvider lp) throws IOException {
        this.tokens = tokens;
        entries = new ArrayList<String>();
        tables = new ArrayDeque<>();
        nesting_level = 0;
        this.lp = lp;
    }
    
    public void ExitRule(RuleApplication application) {
        switch(application.ruleIndex) {
            case BLOCK:
                nesting_level--;
                break;
        }
    }

    public void Apply(RuleApplication application) {
        switch(application.ruleIndex) {
            case FUNCTION_HEADING:
            case PROCEDURE_HEADING:
                if (application.childIndex == 1) {
                	tables.peekFirst().addProcFunc(tokens.get(application.tokenIndex).val, "procedure", null);
                }
            case PROGRAM_HEADING:
                if (application.childIndex == 1) {
                    tables.addFirst(new SymbolTable(tokens.get(application.tokenIndex).val,(lp.nextLabel()), nesting_level++));
                }
                break;
            case VARIABLE_DECLARATION:
                if (application.childIndex == 2) {
                    for (String name: entries) {
                        tables.peekFirst().addVariable(name, tokens.get(application.tokenIndex).type, "variable");
                    }
                    entries.clear();
                }
                break;
            case VALUE_PARAMETER_SECTION:
                if (application.childIndex == 2) {
                    for (String name: entries) {
                        tables.peekFirst().addParameter(name, tokens.get(application.tokenIndex).type, "parameter", "value");
                    }
                    entries.clear();
                }
                break;
            case VARIABLE_PARAMETER_SECTION:
                if (application.childIndex == 3) {
                    for (String name: entries) {
                        tables.peekFirst().addParameter(name, tokens.get(application.tokenIndex).type, "parameter", "reference");
                    }
                    entries.clear();
                }
                break;
            case IDENTIFIER_LIST:
                if (application.childIndex == 0) {
                    entries.add(tokens.get(application.tokenIndex).val);
                }
                break;
            case IDENTIFIER_TAIL:
                if (application.childIndex == 1) {
                    entries.add(tokens.get(application.tokenIndex).val);
                }
                break;
        }
    }

    public SymbolTable getTable(String name) {
        for (SymbolTable s : tables) {
            if (s.getEntry(name) != null) {
                return s;
            }
        }

        return null;
    }

    public SymbolTable getTable() {
        return tables.peekFirst();
    }
}
