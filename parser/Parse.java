package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;

import util.Token;
import util.TokenType;
    
public class Parse {
	HashMap<String,HashMap<TokenType,Integer>> LL1; //rule, tokentype
	HashSet<String> ruleNames;
	String[][] rules;
	ArrayList<Integer> parseTree;
	
	ArrayList<Token> tokens;
	private int token_index;
	
	public Parse(ArrayList<Token> tokens) throws IOException{
		LL1 = new HashMap<>();
		parseTree = new ArrayList<>();
		this.tokens = tokens;
		token_index = 0;
		populate();
	}
	
	public void parse(int curRule, int curIndex, Token curToken) throws IllegalArgumentException {
		//If rule is terminal
		if(isTerminal(curRule,curIndex)){
			if(rules[curRule][curIndex].equals(curToken.val)){
				curToken = getToken();
			} else {
				throw new IllegalArgumentException("Token " + curToken.toString() + " can not resolve at rule [" + curRule + "," + curIndex + "]");
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
	
	public int[] make() throws IllegalArgumentException {
		parse(0,0,getToken());
		int[] tr = new int[parseTree.size()];
		for (int i = 0; i < tr.length; i++) {
			tr[i] = parseTree.get(i);
		}
		return tr;
	}
	
	boolean isTerminal(int curRule, int curIndex){
		return ruleNames.contains(rules[curRule][curIndex]);
	}
	
	/**
	 * Let's have this function fill the rule_names hashset, the LL1 hashmap, and the rules array
	 * @throws IOException 
	 */
	public void populate() throws IOException {
		ArrayList<ArrayList<String>> LL = new ArrayList<>();
		BufferedReader bf = new BufferedReader(new FileReader("compliers\\Resources\\LLTable.csv"));
		String line;
		while ((line=bf.readLine())!=null) {
			LL.add(convLine(line));
		}
		bf.close();
		
		ArrayList<String> rules = new ArrayList<>();
		ArrayList<String> tokens = LL.get(0);
		LL.remove(0);
		for (ArrayList<String> row : LL) {
			rules.add(row.get(0));
			row.remove(0);
		}
		LLTable LLT = new LLTable();
		for (int i = 0; i < rules.size(); i++) {
			for (int j = 0; j < tokens.size(); j++) {
				if (!LL.get(i).get(j).equals(".")) {
					LLT.addEntry(rules.get(i), TokenType.valueOf(tokens.get(j)), getIndices(LL.get(i).get(j)));
					System.out.println(rules.get(i) + " " + TokenType.valueOf(tokens.get(j)) + Arrays.toString(getIndices(LL.get(i).get(j))));
				}
			}
		}
		
		
	}
	public int[] getIndices(String str) {
		String[] splt = str.split("\\|");
		System.out.println(Arrays.toString(splt));
		int[] splt_int = new int[splt.length];
		for (int i = 0; i < splt.length; i++) {
			splt_int[i] = Integer.parseInt(splt[i]);
		}
		return splt_int;
	}
	public ArrayList<String> convLine(String line) {
		String[] splt = line.split(",");
		ArrayList<String> arrl = new ArrayList<>(Arrays.asList(splt));
		return arrl;
	}
	

}