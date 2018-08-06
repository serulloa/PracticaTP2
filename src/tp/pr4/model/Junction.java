package tp.pr4.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tp.pr4.ini.IniSection;

public class Junction extends SimulatedObject {
	
	//########################################################################
	// Atributos
	//########################################################################
	
	protected List<IncomingRoad> incomingRoads;		// lista de carreteras entrantes 
	protected Map<Junction, Road> outcomingRoads;	// mapa de carreteras salientes indicando cual es su cruce destino
	
	//########################################################################
	// Constructores
	//########################################################################

	public Junction(String id) {
		super(id);
		this.incomingRoads = new ArrayList<IncomingRoad>();
		this.outcomingRoads = new HashMap<Junction, Road>();
	}
	
	//########################################################################
	// Métodos
	//########################################################################
	
	public Road roadTo(Junction junction) {
		return outcomingRoads.get(junction);
	}
	
	public Road roadFrom(Junction junction) {
		return junction.outcomingRoads.get(this);
	}
	
	public List<IncomingRoad> getRoadsInfo() {
		return incomingRoads;
	}
	
	void addIncomingRoad(Road road) {
		incomingRoads.add(new IncomingRoad(road));
	}
	
	void addOutgoingRoad(Road road) {
		outcomingRoads.put(road.desJunc, road);
	}
	
	void enter(Vehicle vehicle) {
		Road road = vehicle.getRoad();
		IncomingRoad incomingRoad = null;
		boolean foundIncomingRoad = false;
		
		for (int i = 0; i < incomingRoads.size() && !foundIncomingRoad; i++) {
			if(incomingRoads.get(i).getRoad().getId() == road.getId()) {
				foundIncomingRoad = true;
				incomingRoad = incomingRoads.get(i);
			}
		}
		
		if (incomingRoad != null) incomingRoad.addVehicle(vehicle);
	}
	
	@Override
	void advance(int time) {
		boolean foundGreen = false;
		IncomingRoad incomingRoad = null;
		int index = 0;
		
		for (int i = 0; i < incomingRoads.size() && !foundGreen; i++) {
			if(incomingRoads.get(i).hasGreenLight()) {
				foundGreen = true;
				incomingRoad = incomingRoads.get(i);
				index = i;
			}
		}
		
		if(incomingRoad != null) {
			incomingRoad.advanceFirstVehicle();
			switchLights(index);
		}
	}
	
	protected void switchLights(int index) {
		incomingRoads.get(index).setGreen(false);
		
		if(index >= incomingRoads.size()-1)
			incomingRoads.get(0).setGreen(true);
		else
			incomingRoads.get(index+1).setGreen(true);
	}
	
	protected IncomingRoad createIncomingRoadQueue(Road road) {
		//TODO
		return null;
	}
	
	@Override
	protected String getReportSectionTag() {
//		String report = "[junction_report]";
//		
//		report += "id = " + this.getId() + "\n";
//		report += "time = " + "\n"; //TODO
//		report += "queues = ";
//		
//		for (int i = 0; i < roads.size(); i++) {
//			report += "(" + roads.get(i).toString() + ")";
//			if(i < roads.size()-1) report += ",";
//		}
//		
//		return report;
		
		return "junction_report";
	}
	
	@Override
	protected void fillReportDetails(IniSection iniSection) {
		iniSection.setValue("roads", incomingRoads);
		iniSection.setValue("incomingRoads", incomingRoads);
	}
	
	

}
