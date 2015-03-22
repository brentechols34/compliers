package parser;

import util.Token;

/**
 * Created by brentechols on 3/22/15.
 */
public class RuleApplication {
    String ruleName;
    int ruleIndex;
    int tokenIndex;
    int childIndex;
    int branchIndex;

    public RuleApplication(String ruleName, int ruleIndex, int tokenIndex, int childIndex, int branchIndex) {
        this.ruleName = ruleName;
        this.ruleIndex = ruleIndex;
        this.tokenIndex = tokenIndex;
        this.childIndex = childIndex;
        this.branchIndex = branchIndex;
    }

    @Override
    public String toString() {
        return "RuleApplication{" +
                "ruleName='" + ruleName + '\'' +
                ", ruleIndex=" + ruleIndex +
                ", tokenIndex=" + tokenIndex +
                ", childIndex=" + childIndex +
                ", branchIndex=" + branchIndex +
                '}';
    }
}
