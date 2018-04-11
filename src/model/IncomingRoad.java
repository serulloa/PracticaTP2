package model;

import java.util.Queue;

public class IncomingRoad {
	
	//########################################################################
	// Atributos
	//########################################################################
	
	protected Road road; 			// carretera 
	protected Queue<Vehicle> queue; 	// cola de vehículos 
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
		queue.poll().moveToNextRoad();
	}
	
	protected void addVehicle(Vehicle v) {
		queue.add(v);
	}
	
	protected String printQueue() {
		//TODO
		return null;
	}
	
	public String toString() {
		String line = "";
		
		line += road.getId() + ",";
		if (green) line += "green,";
		else line += "red,";
		
		line += "[";
		
		Vehicle [] aux = (Vehicle[]) queue.toArray();
		
		for (int i = 0; i < queue.size(); i++) {
			line += aux[i].getId();
			if(i < queue.size()-1) line += ",";
		}
		
		line += "]";
		
		return line;
	}

}
