package parser;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;

import util.Token;
import util.TokenType;
    
public class Parse {
	HashMap<String,HashMap<TokenType,Integer>> LL1;
	HashSet<String> ruleNames;
	String[][] rules;
	ArrayList<Integer> parseTree;
	
	ArrayList<Token> tokens;
	private int token_index;
	
	public Parse(ArrayList<Token> tokens){
		LL1 = new HashMap<>();
		parseTree = new ArrayList<>();
		this.tokens = tokens;
		token_index = 0;
		populate();
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
		return tokens.get(token_index++);
	}
	
	public int[] make() {
		parse(0,0,getToken());
		int[] tr = new int[parseTree.size()];
		for (int i = 0; i < tr.length; i++) {
			tr[i] = parseTree.get(i);
		}
		return tr;
	}
	
	private int nextRule(int curRule, int curIndex, Token t) {
		return 0;
	}
	
	boolean isTerminal(int curRule, int curIndex){
		return ruleNames.contains(rules[curRule][curIndex]);
	}
	
	/**
	 * Let's have this function fill the rule_names hashset, as well as the LL1 hashmap
	 */
	public void populate() {
		String LL1_fname = "LL1.csv";
		String rule_fname = "rules.txt";
	}
}
