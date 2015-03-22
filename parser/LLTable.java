package parser;

import java.util.HashMap;
import util.*;

public class LLTable {
	
	HashMap<String,HashMap<TokenType,int[]>> LL1;

	public LLTable() { 
		LL1 = new HashMap<>();
	}
	
	public void addEntry(String rule, TokenType token, int[] ruleIndex){
		if (LL1.get(rule)==null) {
			HashMap<TokenType,int[]> temp = new HashMap<>();
			temp.put(token,ruleIndex);
			LL1.put(rule,temp);
		} else {
			LL1.get(rule).put(token, ruleIndex);
		}
		
		
	}
	
	
	public int[] getRuleIndex(String rule, TokenType type){
		HashMap<TokenType,int[]> t = LL1.get(rule);
		return t.get(type);
	}
}