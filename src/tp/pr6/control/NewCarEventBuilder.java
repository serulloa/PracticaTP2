package tp.pr6.control;

import tp.pr6.ini.IniSection;
import tp.pr6.model.Event;
import tp.pr6.model.NewCarEvent;

public class NewCarEventBuilder extends EventBuilder {
	
	public NewCarEventBuilder() {
		this.tag = "new_vehicle";
		
		this.keys = new String[9];
		this.keys[0] = "time";
		this.keys[1] = "id";
		this.keys[2] = "itinerary";
		this.keys[3] = "max_speed";
		this.keys[4] = "type";
		this.keys[5] = "resistance";
		this.keys[6] = "fault_probability";
		this.keys[7] = "max_fault_duration";
		this.keys[8] = "seed";
		
		this.defaultValues = new String[9];
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
			this.defaultValues[5] = section.getValue(this.keys[5]);
			this.defaultValues[6] = section.getValue(this.keys[6]);
			this.defaultValues[7] = section.getValue(this.keys[7]);
			this.defaultValues[8] = section.getValue(this.keys[8]);
			
			event = new NewCarEvent(Integer.parseInt(this.defaultValues[0]), this.defaultValues[1], 
					Integer.parseInt(this.defaultValues[3]), aux, Integer.parseInt(this.defaultValues[5]),
					Double.parseDouble(this.defaultValues[6]), Integer.parseInt(this.defaultValues[7]),
					Long.parseLong(this.defaultValues[8]));
		}
		
		return event;
	}

}
