package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
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
	
	public void tokenize() throws IOException, ScannerException { //generate tokens
		tokens = new ArrayList<>();
		Token t;
		do {
			t = sc.nextToken();
			if (t == null) break;
			tokens.add(t);
		} while (true);
		tokens.add(new Token(TokenType.MP_EOF,"",-1,-1));
	}
	
	public void parsify() throws IOException, IllegalArgumentException { //generate parse tree (TODO: Symbol table)
		if (tokens == null) {
			System.out.println("No: Tokenize first.");
			return;
		}
		pr = new Parse(tokens);
		parseTree = pr.make();
		System.out.println(Arrays.toString(parseTree));
	}
	
	public static void main(String[] args) throws IOException, IllegalArgumentException, ScannerException {
		Controller c = new Controller("compliers/Resources/test1.mp");
		c.tokenize();
		System.out.println("Token Stream: " + c.tokens);
		c.parsify();
	}
	
	
}
