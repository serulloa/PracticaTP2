package tp.pr5.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tp.pr5.ini.IniSection;

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
		incomingRoads.add(createIncomingRoadQueue(road));
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
		
		for (int i = 0; i < incomingRoads.size() && !foundGreen; i++) {
			if(incomingRoads.get(i).hasGreenLight()) {
				incomingRoads.get(i).advanceFirstVehicle();
				foundGreen = true;
			}
		}
		
		switchLights();
	}
	
	protected void switchLights() {
		int index = -1;
		boolean foundGreen = false;
		
		for (int i = 0; i < incomingRoads.size() && !foundGreen; i++) {
			if (incomingRoads.get(i).hasGreenLight()) {
				index = i;
				foundGreen = true;
				incomingRoads.get(i).green = false;
			}
		}
		
		if (!incomingRoads.isEmpty()) incomingRoads.get((index+1)%incomingRoads.size()).green = true;
	}
	
	protected IncomingRoad createIncomingRoadQueue(Road road) {
		return new IncomingRoad(road);
	}
	
	@Override
	protected String getReportSectionTag() {		
		return "junction_report";
	}
	
	@Override
	protected void fillReportDetails(IniSection iniSection) {
		String queues = "";
		
		if (!incomingRoads.isEmpty()) {
			for (IncomingRoad incomingRoad : incomingRoads)
				queues += "(" + incomingRoad.toString() + "),";
			
			queues = queues.substring(0, queues.length() - 1);
		}
		
		iniSection.setValue("queues", queues);
	}
	
	

}
