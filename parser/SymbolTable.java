package parser;

import java.util.ArrayList;
import java.util.HashMap;

import util.TokenType;

public class SymbolTable {
	
	String name, label;
	int nestingLevel;
	
	HashMap<String,TableEntry> entries;
	
	public SymbolTable(String name, String label, int nestingLevel){
		this.name = name;
		this.label = label;
		this.nestingLevel = nestingLevel;
		
		entries = new HashMap<String,TableEntry>();
	}
	
	//Encompasses All
	public void addEntry(String lexeme, TokenType type, String kind, String mode, int size, ArrayList<String> parameters){
		TableEntry entry = new TableEntry(type,kind,mode,size,parameters);
		entries.put(lexeme, entry);
	}
	
	//Variables
	public void addVariable(String lexeme, TokenType type, String kind, int size){
		TableEntry entry = new TableEntry(type,kind,size);
		entries.put(lexeme, entry);
	}

	//Parameters
	public void addParameter(String lexeme, TokenType type, String kind, String mode, int size){
		TableEntry entry = new TableEntry(type,kind,mode,size);
		entries.put(lexeme, entry);
	}
	
	//Procedures/functions
	public void addProcFunc(String lexeme, String kind, ArrayList<String> parameters){
		TableEntry entry = new TableEntry(kind,parameters);
		entries.put(lexeme, entry);
	}
	
	public int size(){
		return entries.size()
;	}
}
