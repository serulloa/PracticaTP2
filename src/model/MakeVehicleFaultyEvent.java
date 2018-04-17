package model;

public class MakeVehicleFaultyEvent extends Event {
	
	//########################################################################
	// Constructores
	//########################################################################

	public MakeVehicleFaultyEvent(int i, Integer time, String [] ids) {
		super(time);
		// TODO Auto-generated constructor stub
	}
	
	//########################################################################
	// Métodos
	//########################################################################

	@Override
	public void execute(RoadMap roadMap, int time) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public String toString() {
		// TODO
		return null;
	}

}
