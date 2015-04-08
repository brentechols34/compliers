package parser;

import util.Token;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by brentechols on 4/8/15.
 */
public class SymbolTableController {
    ArrayList<Token> tokens;

    public SymbolTableController(ArrayList<Token> tokens) throws IOException {
        this.tokens = tokens;
    }

    public void Apply(RuleApplication application) {

    }

    public void Undo(RuleApplication application) {

    }
}
