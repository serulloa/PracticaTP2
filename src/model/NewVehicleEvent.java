package model;

public class NewVehicleEvent extends Event {
	
	//########################################################################
	// Constructores
	//########################################################################
	
	public NewVehicleEvent(Integer time, String id, Integer i, String [] x) {
		super(time);
	}
	
	//########################################################################
	// Métodos
	//########################################################################

	@Override
	public void execute(RoadMap roadMap, int time) {
		// TODO Auto-generated method stub

	}

}
