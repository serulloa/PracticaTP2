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
		//TODO
	}
	
	@Override
	void advance(int time) {
		//TODO
	}
	
	protected void switchLights() {
		//TODO
	}
	
	protected IncomingRoad createIncomingRoadQueue(Road road) {
		//TODO
		return null;
	}
	
	@Override
	protected String getReportSectionTag() {
		//TODO
		return null;
	}
	
	@Override
	protected void fillReportDetails(IniSection iniSection) {
		//TODO
	}
	
	

}
