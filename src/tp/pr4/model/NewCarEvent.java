package tp.pr4.model;

import java.util.ArrayList;

public class NewCarEvent extends NewVehicleEvent {
	
	private int resistance;
	private double faultProbability;
	private int maxFaultDuration;
	private long seed;
	
	public NewCarEvent(int time, String id, Integer maxSpeed, String[] itinerary, Integer resistance, Double faultProbability, Integer maxFaultDuration, Long seed) {
		super(time, id, maxSpeed, itinerary);
		this.resistance = resistance;
		this.faultProbability = faultProbability;
		this.maxFaultDuration = maxFaultDuration;
		this.seed = seed;
	}

	@Override
	public void execute(RoadMap roadMap, int time) {
		if (this.time == time && validateId(vehicleId)) {
			ArrayList<Junction> junctions = new ArrayList<Junction>();
			
			for (String id : this.junctions) {
				if (roadMap.hasJunction(id)) junctions.add(roadMap.getJunction(id));
				else throw new SimulatorError("The textfile has errors. The junction " + id + " does not exist.");
			}
			
			roadMap.addVehicle(new Car(vehicleId, maxSpeed, maxFaultDuration, faultProbability, seed, resistance, junctions));
		}
	}

}
