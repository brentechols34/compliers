package parser;

import java.util.ArrayList;
import java.util.HashMap;

import util.TokenType;

public class SymbolTable {
	
	String name, label;
	int nestingLevel;
	int offset;
	
	HashMap<String,TableEntry> entries;
	
	public SymbolTable(String name, String label, int nestingLevel){
		this.name = name;
		this.label = label;
		this.nestingLevel = nestingLevel;
		offset = 0;
		
		entries = new HashMap<String,TableEntry>();
	}
	
	//Encompasses All
	public void addEntry(String lexeme, TokenType type, String kind, String mode, ArrayList<String> parameters){
		TableEntry entry = new TableEntry(type,kind,mode,offset++,parameters);
		entries.put(lexeme, entry);
	}
	
	//Variables
	public void addVariable(String lexeme, TokenType type, String kind){
		TableEntry entry = new TableEntry(type,kind,offset++);
		entries.put(lexeme, entry);
	}

	//Parameters
	public void addParameter(String lexeme, TokenType type, String kind, String mode){
		TableEntry entry = new TableEntry(type,kind,mode,offset++);
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
