package parser;

import java.util.HashMap;
import util.*;

public class LLTable {
	
	HashMap<String,HashMap<TokenType,int[]>> LL1 = new HashMap<>();
	
	public void addEntry(String rule, TokenType token, int[] ruleIndex){
		HashMap<TokenType,int[]> temp = new HashMap<>();
		temp.put(token,ruleIndex);
		LL1.put(rule,temp);
	}
	
	public int[] getRuleIndex(String rule, TokenType type){
		return LL1.get(rule).get(type);
	}
}