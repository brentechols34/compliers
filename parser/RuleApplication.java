package parser;

import util.Token;

/**
 * Created by brentechols on 3/22/15.
 */
public class RuleApplication {
    int rIndex;
    Token t;
    RuleApplication (int _rIndex, Token _t) {
        this.rIndex = _rIndex;
        this.t = _t;
    }
}
