package tp.pr4.control;

import tp.pr4.ini.IniSection;
import tp.pr4.model.Event;
import tp.pr4.model.NewBikeEvent;

public class NewBikeEventBuilder extends EventBuilder {
	
	public NewBikeEventBuilder() {
		this.tag = "new_vehicle";
		
		this.keys = new String[5];
		this.keys[0] = "time";
		this.keys[1] = "id";
		this.keys[2] = "itinerary";
		this.keys[3] = "max_speed";
		this.keys[4] = "type";
		
		this.defaultValues = new String[5];
		IniSection sec = new IniSection(this.tag);
		
		for(int i = 0; i < this.defaultValues.length; i++)
			this.defaultValues[i] = sec.getValue(this.keys[i]);
	}
	
	@Override
	public Event parse(IniSection section) {
		Event event = null;
		
		if(section.getTag().equals(this.tag) && section.getKeys().size() == keys.length){
			this.defaultValues[0] = section.getValue(this.keys[0]);
			this.defaultValues[1] = section.getValue(this.keys[1]);

			String[] aux = section.getValue(this.keys[2]).split("[, ]+");
			String junctions = aux[0] + " ";
			
			for(int i=1 ; i<aux.length; i++)
				junctions = junctions + aux[i] + " ";
			
			this.defaultValues[2] = junctions;
			this.defaultValues[3] = section.getValue(this.keys[3]);
			this.defaultValues[4] = section.getValue(this.keys[4]);
			
			event = new NewBikeEvent(Integer.parseInt(this.defaultValues[0]), this.defaultValues[1], 
					Integer.parseInt(this.defaultValues[3]), aux);
		}
		
		return event;
	}

}
