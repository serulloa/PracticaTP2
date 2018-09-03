package tp.pr6.control;

import tp.pr6.ini.IniSection;
import tp.pr6.model.Event;
import tp.pr6.model.NewRoundRobinJunctionEvent;

public class NewRoundRobinJunctionEventBuilder extends EventBuilder {
	
	public NewRoundRobinJunctionEventBuilder() {
		this.tag = "new_junction";
		
		this.keys = new String[5];
		this.keys[0] = "time";
		this.keys[1] = "id";
		this.keys[2] = "type";
		this.keys[3] = "max_time_slice";
		this.keys[4] = "min_time_slice";
		
		this.defaultValues = new String[5];
		IniSection sec = new IniSection(this.tag);	
		
		for(int i = 0; i < this.defaultValues.length; i++)
			this.defaultValues[i] = sec.getValue(this.keys[i]);
	}

	@Override
	public Event parse(IniSection section) {
		Event event = null;
		
		if(section.getTag().equals(this.tag) && section.getKeys().size() == keys.length) {
			this.defaultValues[0] = section.getValue(this.keys[0]);
			this.defaultValues[1] = section.getValue(this.keys[1]);
			this.defaultValues[2] = section.getValue(this.keys[2]);
			this.defaultValues[3] = section.getValue(this.keys[3]);
			this.defaultValues[4] = section.getValue(this.keys[4]);
			
			event = new NewRoundRobinJunctionEvent(Integer.parseInt(this.defaultValues[0]), this.defaultValues[1],
					Integer.parseInt(this.defaultValues[3]), Integer.parseInt(this.defaultValues[4]));
		}
		
		return event;
	}

}
