package tp.pr6.model;

import java.util.List;

import tp.pr6.ini.IniSection;

public class Bike extends Vehicle {
	
	public Bike(String id, int speed, List<Junction> itinerary) {
		super(id, speed, itinerary);
	}
	
	@Override
	public void makeFaulty(int n) {
		if (currSpeed > (maxSpeed/2)) super.makeFaulty(n);
	}
	
	@Override
	public void fillReportDetails(IniSection iniSection) {
		super.fillReportDetails(iniSection);
		iniSection.setValue("type", "bike");
	}
	
}
