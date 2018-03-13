package model;

import java.util.List;

public class IncomingRoad {
	
	//########################################################################
	// Atributos
	//########################################################################
	
	protected Road road; 			// carretera 
	protected List<Vehicle> queue; 	// cola de vehículos 
	protected boolean green; 		// true si su semáforo está verde
	
	//########################################################################
	// Constructores
	//########################################################################
	
	public IncomingRoad(Road road) {
		this.road = road;
	}
	
	//########################################################################
	// Métodos
	//########################################################################
	
	public Road getRoad() {
		return road;
	}
	
	public boolean hasGreenLight() {
		return green;
	}
	
	protected void setGreen(boolean green) {
		this.green = green;
	}
	
	protected void advanceFirstVehicle() {
		queue.get(0).advance(0);
	}
	
	protected void addVehicle(Vehicle v) {
		queue.add(v);
	}
	
	protected String printQueue() {
		//TODO
		return null;
	}
	
	public String toString() {
		//TODO
		return null;
	}

}
