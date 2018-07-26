package model;

import java.util.ArrayList;

public class NewVehicleEvent extends Event {
	
	//########################################################################
	// Atributos
	//########################################################################
	
	private String vehicleId;
	private int maxSpeed;
	private String[] junctions;
	
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
		// TODO Auto-generated method stub
		if (this.time == time && validateId(vehicleId)) {
			ArrayList<Junction> junctions = new ArrayList<Junction>();
			
			for (String id : this.junctions) {
				if (roadMap.hasJunction(id)) junctions.add(roadMap.getJunction(id));
			}
			
			roadMap.addVehicle(new Vehicle(vehicleId, maxSpeed, junctions));
		}
	}
	
	@Override
	public String toString() {
		return "Vehicle";
	}

}
