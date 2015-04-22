package parser;

import util.Token;

/**
 * Created by brentechols on 3/22/15.
 */
public class RuleApplication {
    String ruleName;
    private int ruleIndex;
    int tokenIndex;
    int childIndex;
    int branchIndex;
    boolean isCompleted;

    public RuleApplication(String ruleName, int ruleIndex, int tokenIndex, int childIndex, int branchIndex) {
        this.ruleName = ruleName;
        this.setRuleIndex(ruleIndex);
        this.tokenIndex = tokenIndex;
        this.childIndex = childIndex;
        this.branchIndex = branchIndex;
    }

    @Override
    public String toString() {
        return "{" +
                "ruleName='" + ruleName + '\'' +
                ", ruleIndex=" + getRuleIndex() +
                ", tokenIndex=" + tokenIndex +
                ", childIndex=" + childIndex +
                ", branchIndex=" + branchIndex +
                '}';
    }

	public int getRuleIndex() {
		return ruleIndex;
	}

	public void setRuleIndex(int ruleIndex) {
		this.ruleIndex = ruleIndex;
	}
}
