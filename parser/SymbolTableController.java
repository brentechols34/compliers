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
    Stack<SymbolTable> symbolStack;

    public SymbolTableController(ArrayList<Token> tokens) throws IOException {
        this.tokens = tokens;
        symbolStack = new Stack<>();
    }

    public void Apply(RuleApplication application) {
        
    }

    public void Undo(RuleApplication application) {

    }
}
