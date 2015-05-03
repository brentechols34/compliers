package parser;

import util.Token;

/**
 * Created by brentechols on 3/22/15.
 */
public class RuleApplication {
    String ruleName;
    public int ruleIndex;
    public int tokenIndex;
    public int childIndex;
    public int branchIndex;
    public boolean isCompleted;
    public RuleApplication parent;

    public RuleApplication(String ruleName, int ruleIndex, int tokenIndex, int childIndex, int branchIndex, RuleApplication parent) {
        this.ruleName = ruleName;
        this.ruleIndex = ruleIndex;
        this.tokenIndex = tokenIndex;
        this.childIndex = childIndex;
        this.branchIndex = branchIndex;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "{" +
                "ruleName='" + ruleName + '\'' +
                ", ruleIndex=" + ruleIndex +
                ", tokenIndex=" + tokenIndex +
                ", childIndex=" + childIndex +
                ", branchIndex=" + branchIndex +
                '}';
    }
}
