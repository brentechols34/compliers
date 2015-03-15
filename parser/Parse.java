package parser;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;

import util.Token;
import util.TokenType;
    
public class Parse {
	HashMap<String,HashMap<TokenType,Integer>> LL1;
	HashSet<String>ruleNames;
	String[][] rules;
	ArrayList<Integer> parseTree;
	
	public Parse(){
		LL1 = new HashMap<>();
		parseTree = new ArrayList<>();
	}
	
	public void parse(int curRule, int curIndex, Token curToken){
		//If rule is terminal
		if(isTerminal(curRule,curIndex)){
			if(rules[curRule][curIndex].equals(curToken.val)){
				curToken = getToken();
			}
		} else {
			int nextRule = LL1.get(rules[curRule][curIndex]).get(curToken.type);
			parseTree.add(nextRule);
			parse(nextRule,0,curToken);
		} 
		if (curIndex < rules[curRule].length - 1){
			parse(curRule,++curIndex,curToken);
		}
	}
	
	private Token getToken(){
		return null;
	}
	
	private int nextRule(int curRule,int curIndex,Token t) {
		return 0;
	}
	
	boolean isTerminal(int curRule, int curIndex){
		return ruleNames.contains(rules[curRule][curIndex]);
	}
}
