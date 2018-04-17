package control;

import ini.IniSection;
import model.Event;

public abstract class EventBuilder {
	
	//########################################################################
	// Atributos
	//########################################################################
	
	protected String tag; 
	protected String[] keys; 
	protected String[] defaultValues;
	
	//########################################################################
	// Métodos abstractos
	//########################################################################
	
	public abstract Event parse(IniSection section);
	
}
