package model;

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
		
	}
	
	//########################################################################
	// Métodos
	//########################################################################
	
	void addVehicle(Vehicle vehicle) {
		vehicles.add(vehicle);
		vehicleMap.put(vehicle.getId(), vehicle);
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
	
}
