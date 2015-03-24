package parser;

import java.util.HashMap;

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
	public void addEntry(String lexeme, String type, String kind, String mode, int size, String[] parameters){
		TableEntry entry = new TableEntry(type,kind,mode,size,parameters);
		entries.put(lexeme, entry);
	}
	
	//Variables
	public void addVariable(String lexeme, String type, String kind, int size){
		TableEntry entry = new TableEntry(type,kind,size);
		entries.put(lexeme, entry);
	}

	//Parameters
	public void addParameter(String lexeme, String type, String kind, String mode, int size){
		TableEntry entry = new TableEntry(type,kind,mode,size);
		entries.put(lexeme, entry);
	}
	
	//Procedures/functions
	public void addProcedure(String lexeme, String kind, String[] parameters){
		TableEntry entry = new TableEntry(kind,parameters);
		entries.put(lexeme, entry);
	}
	
	public void addFunction(String lexeme, String kind, String[] parameters){
		TableEntry entry = new TableEntry(kind,parameters);
		entries.put(lexeme, entry);
	}
}
