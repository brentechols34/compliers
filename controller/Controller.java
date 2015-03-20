package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import scanner.Scanner;
import util.*;
import parser.Parse;
public class Controller {

	Scanner sc;
	Parse pr;
	
	ArrayList<Token> tokens;
	int[] parseTree;
	
	public Controller(String fname) throws FileNotFoundException {
		sc = new Scanner(fname);
	}
	
	public void tokenize() throws IOException { //generate tokens
		tokens = new ArrayList<>();
		Token t;
		do {
			t = sc.nextToken();
			if (t == null) break;
			tokens.add(t);
		} while (true);
	}
	
	public void parsify() throws IOException, IllegalArgumentException { //generate parse tree (TODO: Symbol table)
		if (tokens == null) {
			System.out.println("No: Tokenize first.");
			return;
		}
		pr = new Parse(tokens);
		parseTree = pr.make();
	}
	
	public static void main(String[] args) throws IOException, IllegalArgumentException {
		Controller c = new Controller("test.txt");
		c.tokenize();
		System.out.println("Token Stream: " + c.tokens);
		c.parsify();
	}
	
	
}
