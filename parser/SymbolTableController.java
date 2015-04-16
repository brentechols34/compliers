package parser;

import util.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by brentechols on 4/8/15.
 */

public class SymbolTableController {
    ArrayList<Token> tokens;
    ArrayList<String> entries;
    Stack<SymbolTable> tables;

    final int PROGRAM_HEADING = 2;
    final int VARIABLE_DECLARATION = 8;
    final int PROCEDURE_HEADING = 16;
    final int FUNCTION_HEADING = 17;
    final int VARIABLE_PARAMETER_SECTION = 27;
    final int VALUE_PARAMETER_SECTION = 26;
    final int IDENTIFIER_LIST = 112;
    final int IDENTIFIER_TAIL = 113;

    public SymbolTableController(ArrayList<Token> tokens) throws IOException {
        this.tokens = tokens;
        entries = new ArrayList<String>();
    }

    public void Apply(RuleApplication application) {
        switch(application.ruleIndex) {
            case PROGRAM_HEADING:
                break;
            case VARIABLE_DECLARATION:
                break;
            case PROCEDURE_HEADING:
                break;
            case FUNCTION_HEADING:
                break;
            case VALUE_PARAMETER_SECTION:
                break;
            case VARIABLE_PARAMETER_SECTION:
                break;
            case IDENTIFIER_LIST:
                break;
            case IDENTIFIER_TAIL:
                break;
        }
    }

    public void Undo(RuleApplication application) {
        switch(application.ruleIndex) {
            case PROGRAM_HEADING:
                break;
            case VARIABLE_DECLARATION:
                break;
            case PROCEDURE_HEADING:
                break;
            case FUNCTION_HEADING:
                break;
            case VALUE_PARAMETER_SECTION:
                break;
            case VARIABLE_PARAMETER_SECTION:
                break;
            case IDENTIFIER_LIST:
                break;
            case IDENTIFIER_TAIL:
                break;
        }
    }
}
