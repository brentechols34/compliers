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
    
public class AwfulParser {
	HashSet<String> ruleNames;
	String[][] rules;
	HashMap<String, ArrayList<Integer>> rules_map;
	ArrayList<Integer> parseTree;
	
	ArrayList<Token> tokens;
	private int token_index;
	
	public AwfulParser(ArrayList<Token> tokens) throws IOException{
		parseTree = new ArrayList<>();
		this.tokens = tokens;
		token_index = 0;
		populate();
	}
	
	public ArrayList<Integer> parse(int cr, Token ct) {
		ArrayList<Integer> mytree = new ArrayList<>();
		mytree.add(cr); //assume this works and add me as root
		//we need to try to build the sub parse trees for each section of this rule
		String[] mahRule = rules[cr];
		for (int i = 1; i < mahRule.length; i++) {
			if (isTerminal(cr,i)) {
				//hang this token, move to next
				//TODO
				//All the rules use annoying different syntax than the token names
			} else { //if it is not terminal, try to apply every rule after this one
				ArrayList<Integer> possibles = rules_map.get(mahRule[i].replaceAll("<|>", ""));
				boolean success = false;
				for (Integer in : possibles) {
					ArrayList<Integer> attempt = parse(in,ct);
					if (attempt != null) {
						mytree.addAll(attempt);
						success = true;
						break;
					}
					if (!success) return null; //couldn't hang this token, return null
				}
			}
		}
		
		return null;
	}
	
	private Token getToken(){
		return tokens.get(token_index++);
	}
	
	public int[] make() {
		parse(0,getToken());
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
		
		ArrayList<String[]> rules_temp = readSV(rules_fname, " ");
		
		rules_map = new HashMap<>();
		for (String name : names) {
			rules_map.put(name, new ArrayList<>());
		}
		rules = new String[rules_temp.size()][];
		for (int i = 0; i < rules_temp.size(); i++) {
			String[] rule = rules_temp.get(i);
			String[] right_side = new String[rule.length-1];
			for (int j = 1; j < rule.length; j++) {
				right_side[j-1] = rule[j];
			}
			rules[i] = right_side;
		    rules_map.get(rule[0].replaceAll("<|>","")).add(i);
		    
		}
		
		for (String s : rules_map.keySet()) {
			String st = s + ": ";
			for (Integer arr : rules_map.get(s)) {
				st += arr +"\n";
			}
			System.out.println(st);
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