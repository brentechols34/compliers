package parser;

import java.util.ArrayList;

import util.*;

public class TableEntry {
	
	String kind, mode;
	
	TokenType type;
	
	int size;
	
	ArrayList<String> parameters;
	
	
	
	//Encompasses All
	public TableEntry(TokenType type, String kind, String mode, int size, ArrayList<String> parameters){
		this.type = type;
		this.kind = kind;
		this.mode = mode;
		this.size = size;
		this.parameters = parameters;
	}
	
	//Variables
	public TableEntry(TokenType type, String kind, int size){
		this.type = type;
		this.kind = kind;
		this.size = size;
	}
	
	//Parameters
	public TableEntry(TokenType type, String kind, String mode, int size){
		this.type = type;
		this.kind = kind;
		this.mode = mode;
		this.size = size;
	}
	
	//Procedures/functions
	public TableEntry(String kind, ArrayList<String> parameters){
		this.parameters = parameters;
		this.kind = kind;
	}
	//Getters
	public TokenType getType() { return type; }
	public String getKind() { return kind; }
	public String getMode() { return mode; }
	public int size() { return size; }
	public ArrayList<String> getParameters() { return parameters; }
	
	public String toString(){
		return type.name() + " " + kind + " " + size + parameters.toString();
	}
}


