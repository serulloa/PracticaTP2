package tp.pr4.model;

import java.util.List;

import tp.pr4.misc.SortedArrayList;

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
		this.queue = new SortedArrayList<Vehicle>();
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
		if (!queue.isEmpty()) {
			queue.get(0).moveToNextRoad();
			queue.remove(0);
		}
	}
	
	protected void addVehicle(Vehicle v) {
		queue.add(v);
	}
	
	protected String printQueue() {
		String line = "";
		
		line += "[";
		
		Vehicle [] aux = (Vehicle[]) queue.toArray();
		
		for (int i = 0; i < queue.size(); i++) {
			line += aux[i].getId();
			if(i < queue.size()-1) line += ",";
		}
		
		line += "]";
		
		return line;
	}
	
	public String toString() {
		String line = "";
		
		line += road.getId() + ",";
		if (green) line += "green,";
		else line += "red,";
		
		line += printQueue();
		
		return line;
	}

}
