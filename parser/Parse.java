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

    SymbolTableController symbolTable;

	ArrayList<Token> tokens;

	public Parse(ArrayList<Token> tokens) throws IOException{
		this.tokens = tokens;
        symbolTable = new SymbolTableController(tokens);
		populate();
	}
    // Accepts a set of state parameters, and attempts to expand a child by mutating the path given.
	public ParseReturn parse(String ruleName, int rule, int token, int child, ArrayList<RuleApplication> path) throws IllegalArgumentException {
        Token curToken = tokens.get(token);
        
        // Get the string this application is expanding to currently
        String expand = rules[rule][child];
        if (isTerminal(expand)) {
        	if (expand.equals(curToken.type.name())) {
                System.out.println("Hung:" + curToken.val + " on " + new RuleApplication(ruleName, rule, token, child, 0).toString());
                return ParseReturn.HUNG;
            } else if (expand.equals("lambda")) {
                return ParseReturn.LAMBDA;
            } else {
            	System.out.println(path);
            	System.out.println("Error token doesn't match expected @ Rule: " + new RuleApplication(ruleName, rule, token, child, 0).toString() + curToken.toString());
                return ParseReturn.ERROR;
            }
        }
        
        // Find what rule that expansion goes to
		int[] index = LLT.getRuleIndex(expand, curToken.type);
        if (index == null) {
        	System.out.println(path);
        	System.out.println("Error LL lookup failed @ Rule: " + new RuleApplication(ruleName, rule, token, child, 0).toString() + curToken.toString());
            return ParseReturn.ERROR;
        }
        
        // Add this expansion to the path
        path.add(new RuleApplication(expand, index[0], token, 0, 0));
        symbolTable.Apply(path.get(path.size() - 1));

        return ParseReturn.EXPAND;
	}
    enum ParseReturn {
        HUNG,
        LAMBDA,
        EXPAND,
        ERROR
    }
    public ArrayList<RuleApplication> parseMaster() {
        int tokenIndex = 0;
        ArrayList<RuleApplication> path = new ArrayList<RuleApplication>();
        path.add(new RuleApplication("SystemGoal", 0, 0, 0, 0));
        symbolTable.Apply(path.get(path.size() - 1));

        // Process through and generate paths until:
        //  - the path size reaches 0, indicating no valid tree exists
        //  - getNext returns null, indicating all members of the tree are done
        while(path.size() > 0 && this.getNext(path) != null) {
            RuleApplication next = this.getNext(path);
            ParseReturn r = parse(next.ruleName, next.ruleIndex, tokenIndex, next.childIndex, path);
            switch (r) {
                case HUNG:
                    next.childIndex++;
                    tokenIndex++;
                    break;
                case LAMBDA:
                    next.childIndex++;
                    break;
                case EXPAND:
                    next.childIndex++;
                    break;
                case ERROR:
                    tokenIndex = trimTree(path);
                    break;
            }
        }

        if (path.size() == 0) {
            System.out.println("No valid parse tree found!");
        }
        if (tokenIndex != tokens.size()) {
            System.out.println("Not all tokens processed!");
        }

        return path;
    }
    // Prunes out path elements who have exhausted all branching possibilities,
    //  adjusts the branchIndex, and resets the first candidate found.
    private int trimTree(ArrayList<RuleApplication> path) {
        // Prune all maximally branched paths at the end of our path
    	System.out.println("Pre Trim: " + path.toString());
        for (int i = path.size() - 1; i >= 0; i--) {
            RuleApplication app = path.get(i);
            int[] index = LLT.getRuleIndex(app.ruleName, tokens.get(app.tokenIndex).type);

            // If we have exhausted all branching at this path, remove it
            if (app.branchIndex + 1 >= index.length) {
                symbolTable.Undo(path.get(i));
                path.remove(i);
            } else {
            	break;
            }
        }

        // We deleted the whole path
        if (path.size() == 0) {
            return 0;
        }

        // Alter our last member so it will expand to a new branch
        RuleApplication last = path.get(path.size() - 1);
        System.out.println("Branch reset: " + path.toString());
        last.branchIndex++;
        last.childIndex = 0;
        last.ruleIndex = LLT.getRuleIndex(last.ruleName, tokens.get(last.tokenIndex).type)[last.branchIndex];

        return last.tokenIndex;
    }
    // Returns the next valid path to expand on in the tree, which is defined as the first
    //  element found without it's children filled in
    private RuleApplication getNext(ArrayList<RuleApplication> path) {
        for (int i = path.size() - 1; i >= 0; i--) {
            RuleApplication app = path.get(i);
            String[] children = rules[app.ruleIndex];
            if (children.length > app.childIndex) {
                return app;
            }
        }

        return null;
    }
	public RuleApplication[] make() throws IllegalArgumentException {
		ArrayList<RuleApplication> parseTree = parseMaster();
        RuleApplication[] tr = new RuleApplication[parseTree.size()];
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
		BufferedReader bf = new BufferedReader(new FileReader("compliers/Resources/LLTable.csv"));
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
				}
			}
		}

		bf = new BufferedReader(new FileReader("compliers/Resources/CleanGrammar2.txt"));
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
