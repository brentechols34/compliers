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
	LLTable LLT; //rule, tokentype
	HashSet<String> ruleNames;
	ArrayList<String> ruleNamesList;
	HashMap<String, ArrayList<Integer>> byName;
	String[][] rules;
	ArrayList<Integer> parseTree;

	ArrayList<Token> tokens;
	private int token_index;

	public Parse(ArrayList<Token> tokens) throws IOException{
		parseTree = new ArrayList<>();
		this.tokens = tokens;
		token_index = 0;
		populate();
	}

	Token curToken;
	public void parse(String ruleName) throws IllegalArgumentException {
		if (!isTerminal(ruleName)) {

			int[] index = LLT.getRuleIndex(ruleName, curToken.type); //get rule indices
			System.out.println(ruleName + " " + curToken + " " + Arrays.toString(index));
			parseTree.add(index[0]); //add this rule to parse tree
			//System.out.println(index[0]);
			String[] right_side = rules[index[0]];
			for (String r : right_side) parse(r);
		} else {
			if (ruleName.equals(curToken.type.name())) {
				System.out.println("Hung:" + curToken.val);
				curToken = nextToken();
			} else {
				if (!ruleName.equals("lambda")) {
					throw new IllegalArgumentException("Expected type " + ruleName + ". Received " + curToken.type.name());
				}
			}
		}
	}

	private Token nextToken(){
		return tokens.get(token_index++);
	}

	public int[] make() throws IllegalArgumentException {
		curToken = nextToken();
		parse("SystemGoal");
		int[] tr = new int[parseTree.size()];
		for (int i = 0; i < tr.length; i++) {
			tr[i] = parseTree.get(i);
		}
		return tr;
	}

	boolean isTerminal(String ruleName){
		return !ruleNames.contains(ruleName);
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

		ruleNamesList = new ArrayList<>();

		ArrayList<String> tokens = LL.get(0);
		LL.remove(0);
		for (ArrayList<String> row : LL) {
			ruleNamesList.add(row.get(0));
			row.remove(0);
		}
		ruleNames = new HashSet<>(ruleNamesList);

		LLT = new LLTable();
		for (int i = 0; i < ruleNamesList.size(); i++) {
			for (int j = 0; j < tokens.size(); j++) {
				if (!LL.get(i).get(j).equals(".")) {
					LLT.addEntry(ruleNamesList.get(i), TokenType.valueOf(tokens.get(j)), getIndices(LL.get(i).get(j)));
					System.out.println("Entry added: (" + ruleNamesList.get(i) + "," + TokenType.valueOf(tokens.get(j))+"): " + Arrays.toString(getIndices(LL.get(i).get(j))));
				}
			}
		}


		bf = new BufferedReader(new FileReader("compliers\\Resources\\CleanGrammar2.txt"));
		int cnt = 0;
		ArrayList<String[]> rules_init = new ArrayList<>();
		byName = new HashMap<>();
		while ((line=bf.readLine())!=null) {
			String[] splat = line.split(":");
			String left = splat[0];
			String[] right_t = splat[1].split(" ");
			String[] right = Arrays.copyOfRange(right_t, 1, right_t.length);;
			rules_init.add(right);
			if (byName.get(left) == null) {
				ArrayList<Integer> arr = new ArrayList<>();
				arr.add(cnt);
				byName.put(left, arr);
			} else {
				byName.get(left).add(cnt);
			}
			cnt++;
		}
		bf.close();

		rules = rules_init.toArray(new String[0][0]);
		for (String s : ruleNames) {
			System.out.println(s);
			for (Integer d : byName.get(s)) {
				System.out.println("\t"+d);
			}
		}
	}

	public int[] getIndices(String str) {
		String[] splt = str.split("\\|");
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