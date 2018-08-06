package tp.pr4.control;

import tp.pr4.ini.IniSection;
import tp.pr4.model.Event;

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
