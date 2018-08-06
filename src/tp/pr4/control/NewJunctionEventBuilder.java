package tp.pr4.control;

import tp.pr4.ini.IniSection;
import tp.pr4.model.Event;
import tp.pr4.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends EventBuilder {
	
	//########################################################################
	// Constructores
	//########################################################################
	
	public NewJunctionEventBuilder() {
		this.tag = "new_junction";
		
		this.keys = new String[2];
		this.keys[0] = "time";
		this.keys[1] = "id";
		
		this.defaultValues = new String[2];
		IniSection sec = new IniSection(this.tag);	
		
		for(int i = 0; i < this.defaultValues.length; i++)
			this.defaultValues[i] = sec.getValue(this.keys[i]);
	}
	
	//########################################################################
	// Métodos
	//########################################################################
	
	@Override
	public Event parse(IniSection section) {
		Event event = null;
		
		if(section.getTag().equals(this.tag)) {
			this.defaultValues[0] = section.getValue(this.keys[0]);
			this.defaultValues[1] = section.getValue(this.keys[1]);	
			
			event = new NewJunctionEvent(Integer.parseInt(this.defaultValues[0]), this.defaultValues[1]);
		}
		
		return event;
	}
	
	public String toString() {
		return this.tag + this.keys + this.defaultValues;
	}

}
