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
		String LL1_fname = "Resources/LL1.csv";
		String rules_fname = "Resources/rules.ssv";
		String names_fname = "Resources/names.txt";
		String tokens_fname = "Resources/tokens.txt";
		
		//first we need the names and tokens, which are the rows and columns of the LL1
		ArrayList<String> names = readTXT(names_fname);
		System.out.println("Names: " + names);
		ArrayList<String> tokens = readTXT(tokens_fname);
		System.out.println("Tokens: " + tokens);
		ruleNames = new HashSet<>();
		ruleNames.addAll(names); //put all the rule names into a hashset for O(1) contains checking
		
		for (String name : names) { //init all the sub hashmaps for the LL1
			LL1.put(name, new HashMap<>());
		}
		
		ArrayList<String[]> LL1_temp = readSV(LL1_fname, ","); //scan in the LL1
		
		for (int i = 0; i < LL1_temp.size(); i++) { //populates the LL1, hopefully
			HashMap<TokenType, Integer> mah_map = LL1.get(names.get(i)); 
			String[] mah_row = LL1_temp.get(i); //this is the row of the LL1 table for the i-th rule
			System.out.println(i + ": " + Arrays.toString(mah_row));
			for (int j = 0; j < mah_row.length; j++) {
				if (!mah_row[j].equals(".")) {
					mah_map.put(TokenType.valueOf(tokens.get(j)), Integer.parseInt(mah_row[j])); //wooooooooo
				}
			}
		}
		
		for (String s : LL1.keySet()) {
			HashMap<TokenType, Integer> ttmap = LL1.get(s);
			for (TokenType tt : ttmap.keySet()) {
				System.out.println(s + "," + tt.name() + "->" + ttmap.get(tt));
			}
		}
		
		
	}
	
	private ArrayList<String[]> readSV(String fname, String delimit) throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader(fname));
		ArrayList<String[]> lines = new ArrayList<>();
		String line;
		while ((line = bf.readLine())!=null) lines.add(line.split(delimit));
		bf.close();
		return lines;
	}
	
	private ArrayList<String> readTXT(String fname) throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader(fname));
		ArrayList<String> lines = new ArrayList<>();
		String line;
		while ((line = bf.readLine())!=null) lines.add(line);
		bf.close();
		return lines;
	}
}