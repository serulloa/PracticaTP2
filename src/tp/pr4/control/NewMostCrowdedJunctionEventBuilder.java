package tp.pr4.control;

import tp.pr4.ini.IniSection;
import tp.pr4.model.Event;
import tp.pr4.model.NewMostCrowdedJunctionEvent;

public class NewMostCrowdedJunctionEventBuilder extends EventBuilder {
	
	public NewMostCrowdedJunctionEventBuilder() {
		this.tag = "new_junction";
		
		this.keys = new String[3];
		this.keys[0] = "time";
		this.keys[1] = "id";
		this.keys[2] = "type";
		
		this.defaultValues = new String[3];
		IniSection sec = new IniSection(this.tag);	
		
		for(int i = 0; i < this.defaultValues.length; i++)
			this.defaultValues[i] = sec.getValue(this.keys[i]);
	}

	@Override
	public Event parse(IniSection section) {
		Event event = null;
		
		if(section.getTag().equals(this.tag)) {
			this.defaultValues[0] = section.getValue(this.keys[0]);
			this.defaultValues[1] = section.getValue(this.keys[1]);
			this.defaultValues[0] = section.getValue(this.keys[2]);
			
			event = new NewMostCrowdedJunctionEvent(Integer.parseInt(this.defaultValues[0]), this.defaultValues[1]);
		}
		
		return event;
	}

}
