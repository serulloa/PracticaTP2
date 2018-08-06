package tp.pr4.control;

import tp.pr4.ini.IniSection;
import tp.pr4.model.Event;
import tp.pr4.model.MakeVehicleFaultyEvent;

public class MakeVehicleFaultyEventBuilder extends EventBuilder {

	//########################################################################
	// Constructores
	//########################################################################
	
	public MakeVehicleFaultyEventBuilder() {
		this.tag = "make_vehicle_faulty";
		
		this.keys = new String[3];
		this.keys[0] = "time";
		this.keys[1] = "vehicles";
		this.keys[2] = "duration";
		
		this.defaultValues = new String[3];
		IniSection sec = new IniSection(this.tag);	
		
		for(int i = 0; i < this.defaultValues.length; i++)
			this.defaultValues[i] = sec.getValue(this.keys[i]);
	}
	
	//########################################################################
	// Métodos
	//########################################################################

	@Override
	public Event parse(IniSection section) {
		Event event = null;;
		
		if(section.getTag().equals(tag)) {
			String[] aux = section.getValue(this.keys[1]).split("[, ]+");
			String vehicles = aux[0] + " ";
			
			for(int i=1 ; i<aux.length; i++)
				vehicles = vehicles + aux[i] + " ";
			
			this.defaultValues[1] = vehicles;				
			this.defaultValues[2] = section.getValue(this.keys[2]);
			
			event = new MakeVehicleFaultyEvent(Integer.parseInt(this.defaultValues[0]), Integer.parseInt(this.defaultValues[2]), aux);
		}
		
		return event;
	}
	
	public String toString() {
		return this.tag + this.keys + this.defaultValues;
	}

}
