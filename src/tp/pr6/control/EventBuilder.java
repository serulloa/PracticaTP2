package tp.pr6.control;

import tp.pr6.ini.IniSection;
import tp.pr6.model.Event;

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
