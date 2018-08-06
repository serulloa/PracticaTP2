package tp.pr4.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoadMap {

	//########################################################################
	// Atributos
	//########################################################################	
	
	private List<Vehicle> vehicles; 			// lista de vehículos
	private List<Road> roads; 					// lista de carreteras
	private List<Junction> junctions; 			// lista de cruces
	private Map<String,Vehicle> vehicleMap; 	// mapeado de vehículos
	private Map<String,Road> roadMap; 			// mapeado de carreteras
	private Map<String,Junction> junctionMap;	// mapeado de cruces

	//########################################################################
	// Constructores
	//########################################################################	
	
	RoadMap() {
		this.vehicles = new ArrayList<Vehicle>();
		this.roads = new ArrayList<Road>();
		this.junctions = new ArrayList<Junction>();
		this.vehicleMap = new HashMap<String,Vehicle>();
		this.roadMap = new HashMap<String,Road>();
		this.junctionMap = new HashMap<String,Junction>();
	}
	
	//########################################################################
	// Métodos
	//########################################################################
	
	void addVehicle(Vehicle vehicle) {
		vehicles.add(vehicle);
		vehicleMap.put(vehicle.getId(), vehicle);
		vehicle.moveToNextRoad();
	}
	
	void addRoad(Road road) {
		roads.add(road);
		roadMap.put(road.getId(), road);
	}
	
	void addJunction(Junction junction) {
		junctions.add(junction);
		junctionMap.put(junction.getId(), junction);
	}
	
	void clear() {
		vehicles.clear();
		roads.clear();
		junctions.clear();
		vehicleMap.clear();
		roadMap.clear();
		junctionMap.clear();
	}
	
	public String generateReport(int time) {
		String report = "";
		
		for (Vehicle v : vehicles)
			report += v.generateReport(time);
		
		for (Road r : roads)
			report += r.generateReport(time);
		
		for (Junction j : junctions)
			report += j.generateReport(time);
		
		return report;
	}
	
	public void makeFaulty(String vehicleId, int time) {
		if (vehicleMap.get(vehicleId) != null) vehicleMap.get(vehicleId).makeFaulty(time);
	}
	
	public boolean hasJunction(String id) {
		return junctionMap.get(id) != null;
	}
	
	public Junction getJunction(String id) {
		return junctionMap.get(id);
	}

	public List<Road> getRoads() {
		return roads;
	}

	public List<Junction> getJunctions() {
		return junctions;
	}
}
