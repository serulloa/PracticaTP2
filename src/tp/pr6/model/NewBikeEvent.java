package tp.pr6.model;

import java.util.ArrayList;

public class NewBikeEvent extends NewVehicleEvent {

	public NewBikeEvent(int time, String id, Integer maxSpeed, String[] itinerary) {
		super(time, id, maxSpeed, itinerary);
	}
	
	public void execute(RoadMap roadMap, int time) {
		if (this.time == time && validateId(vehicleId)) {
			ArrayList<Junction> junctions = new ArrayList<Junction>();
			
			for (String id : this.junctions) {
				if (roadMap.hasJunction(id)) junctions.add(roadMap.getJunction(id));
				else throw new SimulatorError("The textfile has errors. The junction " + id + " does not exist.");
			}
			
			roadMap.addVehicle(new Bike(vehicleId, maxSpeed, junctions));
		}
	}
	
}
