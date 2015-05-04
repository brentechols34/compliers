package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import scanner.Scanner;
import scanner.ScannerException;
import util.*;
import parser.Parse;
import parser.RuleApplication;
public class Controller {

	Scanner sc;
	Parse pr;

	ArrayList<Token> tokens;
	RuleApplication[] parseTree;

	public Controller(String fname) throws FileNotFoundException {
		sc = new Scanner(fname);
	}

	public void tokenize() throws IOException { //generate tokens
		tokens = new ArrayList<>();
		Token t;
		try {
			do {

				t = sc.nextToken();
				if (t == null) break;
				if (t.type == TokenType.MP_RUN_COMMENT) {
					System.out.println("Run on comment." + t);
					System.exit(0);
				}
				if (t.type == TokenType.MP_RUN_STRING) {
					System.out.println("Unclosed String Literal. " + t);
					System.exit(0);
				}
				if (t.type != TokenType.MP_COMMENT) tokens.add(t);

			} while (true);
		} catch (ScannerException e) {
			System.out.println(e);
		}
		tokens.add(new Token(TokenType.MP_EOF,"",-1,-1));
	}

	public String parsify() throws IOException, IllegalArgumentException { //generate parse tree (TODO: Symbol table)
		if (tokens == null) {
			System.out.println("No: Tokenize first.");
			return null;
		}
		pr = new Parse(tokens);
		parseTree = pr.make();
		//System.out.println(Arrays.toString(parseTree));
		String s = "";
		for (CodeChunk cc : pr.ccs) {
			s+=cc+"\n";
		}
		return s;
	}

	public static void main(String[] args) throws IOException, IllegalArgumentException, ScannerException {
		if (args.length < 2) {
			System.out.println("Requires file to compile and output file name.");
			System.exit(0);
		}
		Controller c = new Controller(args[0]);
		c.tokenize();
		System.out.println("Token Stream: " + c.tokens);
		String code = c.parsify();
		PrintWriter pw = new PrintWriter(new File(args[1]));
		pw.print(code);
		pw.close();
	}


}
