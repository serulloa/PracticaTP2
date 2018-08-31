package tp.pr5.model;

import java.util.ArrayList;

public class NewVehicleEvent extends Event {
	
	//########################################################################
	// Atributos
	//########################################################################
	
	protected String vehicleId;
	protected int maxSpeed;
	protected String[] junctions;
	
	//########################################################################
	// Constructores
	//########################################################################
	
	public NewVehicleEvent(Integer time, String id, Integer maxSpeed, String [] junctions) {
		super(time);
		this.vehicleId = id;
		this.maxSpeed = maxSpeed;
		this.junctions = junctions;
	}
	
	//########################################################################
	// Métodos
	//########################################################################

	@Override
	public void execute(RoadMap roadMap, int time) {
		if (this.time == time && validateId(vehicleId)) {
			ArrayList<Junction> junctions = new ArrayList<Junction>();
			
			for (String id : this.junctions) {
				if (roadMap.hasJunction(id)) junctions.add(roadMap.getJunction(id));
				else throw new SimulatorError("The textfile has errors. The junction " + id + " does not exist");
			}
			
			roadMap.addVehicle(new Vehicle(vehicleId, maxSpeed, junctions));
		}
	}
	
	@Override
	public String toString() {
		return "Vehicle";
	}

}
