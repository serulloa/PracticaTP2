package model;

import java.util.List;
import java.util.Map;

import ini.IniSection;

public class Junction extends SimulatedObject {
	
	//########################################################################
	// Atributos
	//########################################################################
	
	protected List<IncomingRoad> roads; 		 // lista de carreteras entrantes 
	protected Map<Junction, Road> incomingRoads; // mapa de carreteras entrantes indicando cual es su cruce origen
	
	//########################################################################
	// Constructores
	//########################################################################

	public Junction(String id) {
		super(id);
	}
	
	//########################################################################
	// Métodos
	//########################################################################
	
	public Road roadTo(Junction junction) {
		//TODO
		return null;
	}
	
	public Road roadFrom(Junction junction) {
		//TODO
		return null;
	}
	
	public List<IncomingRoad> getRoadsInfo() {
		//TODO
		return null;
	}
	
	void addIncomingRoad(Road road) {
		//TODO
	}
	
	void addOutgoingRoad(Road road) {
		//TODO
	}
	
	void enter(Vehicle vehicle) {
		Road road = vehicle.getRoad();
		IncomingRoad incomingRoad = null;
		boolean foundIncomingRoad = false;
		
		for (int i = 0; i < roads.size() && !foundIncomingRoad; i++) {
			if(roads.get(i).getRoad().getId() == road.getId()) {
				foundIncomingRoad = true;
				incomingRoad = roads.get(i);
			}
		}
		
		incomingRoad.addVehicle(vehicle);
	}
	
	@Override
	void advance(int time) {
		boolean foundGreen = false;
		IncomingRoad incomingRoad = null;
		int index = 0;
		
		for (int i = 0; i < roads.size() && !foundGreen; i++) {
			if(roads.get(i).hasGreenLight()) {
				foundGreen = true;
				incomingRoad = roads.get(i);
				index = i;
			}
		}
		
		if(incomingRoad != null) {
			incomingRoad.advanceFirstVehicle();
			switchLights(index);
		}
	}
	
	protected void switchLights(int index) {
		roads.get(index).setGreen(false);
		
		if(index >= roads.size()-1)
			roads.get(0).setGreen(true);
		else
			roads.get(index+1).setGreen(true);
	}
	
	protected IncomingRoad createIncomingRoadQueue(Road road) {
		//TODO
		return null;
	}
	
	@Override
	protected String getReportSectionTag() {
		String report = "[junction_report]";
		
		report += "id = " + this.getId() + "\n";
		report += "time = " + "\n"; //TODO
		report += "queues = ";
		
		for (int i = 0; i < roads.size(); i++) {
			report += "(" + roads.get(i).toString() + ")";
			if(i < roads.size()-1) report += ",";
		}
		
		return report;
	}
	
	@Override
	protected void fillReportDetails(IniSection iniSection) {
		iniSection.setValue("roads", roads);
		iniSection.setValue("incomingRoads", incomingRoads);
	}
	
	

}
