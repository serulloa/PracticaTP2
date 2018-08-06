package tp.pr5.control;

import tp.pr5.ini.IniSection;
import tp.pr5.model.Event;
import tp.pr5.model.NewRoadEvent;

public class NewRoadEventBuilder extends EventBuilder {

	//########################################################################
	// Constructores
	//########################################################################
	
	public NewRoadEventBuilder() {
		this.tag = "new_road";
		
		this.keys = new String[6];
		this.keys[0] = "time";
		this.keys[1] = "id";
		this.keys[2] = "src";
		this.keys[3] = "dest";
		this.keys[4] = "max_speed";
		this.keys[5] = "length";
		
		this.defaultValues = new String[6];
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
			for(int i=0 ; i<keys.length ; i++)
				this.defaultValues[i] = section.getValue(this.keys[i]);

			event = new NewRoadEvent(Integer.parseInt(this.defaultValues[0]), this.defaultValues[1], 
					this.defaultValues[2], this.defaultValues[3], Integer.parseInt(this.defaultValues[4]),
					Integer.parseInt(this.defaultValues[5]));
		}
		
		return event;
	}
	
	public String toString() {
		return this.tag + this.keys + this.defaultValues;
	}

}
