package tp.pr5.model;

import java.util.List;

public abstract class Event implements Comparable<Event> {
	
	//########################################################################
	// Atributos
	//########################################################################
	
	protected Integer time; // tiempo de ejecución del evento
	
	//########################################################################
	// Constructores
	//########################################################################
	
	Event(Event newEvent) {
		this.time = newEvent.time;
	}
	
	public Event(Integer time) {
		this.time = time;
	}
	
	//########################################################################
	// Métodos
	//########################################################################
	
	public int getScheduledTime() {
		return time;
	}
	
	public int compareTo(Event event) {
		int result = 1;
		
		if (this.time < event.getScheduledTime()) result = -1;
		else if (this.time == event.getScheduledTime()) result = 0;
		
		return result;
	}
	
	public boolean validateId(String id) {
		boolean ok = false;
		
		if (id != null && id.matches("[a-z0-9_]+")) ok = true;
		
		return ok;
	}
	
	protected Junction checkIfJunctionExists(RoadMap roadMap, String id) {
		//TODO
		return null;
	}
	
	protected Vehicle checkIfVehicleExists(RoadMap roadMap, String id) {
		//TODO
		return null;
	}
	
	protected Road checkIfRoadExists(RoadMap roadMap, String id) {
		//TODO
		return null;
	}
	
	protected SimulatedObject checkIfSimObjExists(RoadMap roadMap, String id) {
		//TODO
		return null;
	}
	
	protected List<Junction> parseListOfJunctions(RoadMap roadMap, String[] ids) {
		//TODO
		return null;
	}
	
	protected List<Road> parseListOfRoads(RoadMap roadMap, String[] ids) {
		//TODO
		return null;
	}
	
	protected List<SimulatedObject> parseListOfSimObj(RoadMap roadMap, String[] ids) {
		//TODO
		return null;
	}
	
	protected List<Vehicle> parseListOfVehicles(RoadMap roadMap, String[] ids) {
		//TODO
		return null;
	}
	
	//########################################################################
	// Métodos abstractos
	//########################################################################
	
	public abstract void execute(RoadMap roadMap, int time);
	
}
