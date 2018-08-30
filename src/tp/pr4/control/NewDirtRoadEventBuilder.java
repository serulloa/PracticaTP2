package tp.pr4.control;

import tp.pr4.ini.IniSection;
import tp.pr4.model.Event;
import tp.pr4.model.NewDirtRoadEvent;

public class NewDirtRoadEventBuilder extends EventBuilder {
	
	public NewDirtRoadEventBuilder() {
		this.tag = "new_road";
		
		this.keys = new String[7];
		this.keys[0] = "time";
		this.keys[1] = "id";
		this.keys[2] = "src";
		this.keys[3] = "dest";
		this.keys[4] = "max_speed";
		this.keys[5] = "length";
		this.keys[6] = "type";
		
		this.defaultValues = new String[7];
		IniSection sec = new IniSection(this.tag);
		
		for(int i = 0; i < this.defaultValues.length; i++)
			this.defaultValues[i] = sec.getValue(this.keys[i]);
	}
	
	@Override
	public Event parse(IniSection section) {
		Event event = null;
		
		if(section.getTag().equals(this.tag)) {			
			for(int i=0 ; i<keys.length ; i++)
				this.defaultValues[i] = section.getValue(this.keys[i]);

			event = new NewDirtRoadEvent(Integer.parseInt(this.defaultValues[0]), this.defaultValues[1], 
					this.defaultValues[2], this.defaultValues[3], Integer.parseInt(this.defaultValues[4]),
					Integer.parseInt(this.defaultValues[5]));
		}
		
		return event;
	}

}
