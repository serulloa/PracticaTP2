package tp.pr5.control;

import tp.pr5.ini.IniSection;
import tp.pr5.model.Event;

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
