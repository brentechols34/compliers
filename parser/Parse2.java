package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import parser.Parse.ParseReturn;
import util.Token;
import util.TokenType;

public class Parse2 {
	LLTable LLT; //rule, tokentype
	HashSet<String> ruleNames;
	ArrayList<String> ruleNamesList;
	HashMap<String, ArrayList<Integer>> byName;
	String[][] rules;

	ArrayList<Token> tokens;

	public Parse2(ArrayList<Token> tokens) throws IOException{
		this.tokens = tokens;
		populate();
	}

	private class T {
		ArrayList<Integer> tree;
		int tokens_hung;
		public T(ArrayList<Integer> tree, int tokens_hung) {
			this.tree = tree;
			this.tokens_hung = tokens_hung;
		}
		public void append(T t) {
			tree.addAll(t.tree);
			tokens_hung += t.tokens_hung;
		}
		public void add(int rule) {
			tree.add(rule);
		}
		public void inc() {
			tokens_hung++;
		}
	}

	int depth;
	// Accepts a set of state parameters, and attempts to expand a child by mutating the path given.
	public T parse(String ruleName, int token_index) throws IllegalArgumentException {
		depth += 1;
		Token curToken = tokens.get(token_index);
		T t = new T(new ArrayList<>(), 0); //make new T
		if (isTerminal(ruleName)) {
			if (ruleName.equals(curToken.type.name())) {
				System.out.println("Hung:" + curToken);
				t.inc();
			} else {
				if (!ruleName.equals("lambda")) {
					return null;
				}
			}
			return t;
		} else {
			int[] indices = LLT.getRuleIndex(ruleName, curToken.type);
			if (indices == null) {
				return null;
			} else {
				int running_tokens = token_index;
				T running_children = new T(new ArrayList<>(), 0);
				boolean success = true;
				for (int i : indices) { //try each rule
					//System.out.println("Using " + i + " for rule " + ruleName  + ". Depth: " + depth);
					String[] rule = rules[i]; //try this production
					success = true;
					for (String s : rule) { //construct parse tree for each part of production
						T temp = parse(s,running_tokens);
						if (temp == null) {
							running_tokens = token_index;
							running_children = new T(new ArrayList<>(),0); 
							success = false;
							break;
						} else {
							running_tokens += temp.tokens_hung;
							running_children.append(temp);
						}
					}
					if (success) {
						t.add(i);
						t.append(running_children);
						return t;
					}
				}
				return null;
				//throw new IllegalArgumentException("Failed to construct sub-tree from " + ruleName + " with token " + curToken);
			}
		}

	}


	public ArrayList<Integer> parseMaster() {
		return parse("SystemGoal",0).tree;
	}

	public int[] make() throws IllegalArgumentException {
		ArrayList<Integer> parseTree = parseMaster();
		depth = 0;
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
					//System.out.println("Entry added: (" + ruleNamesList.get(i) + "," + TokenType.valueOf(tokens.get(j))+"): " + Arrays.toString(getIndices(LL.get(i).get(j))));
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