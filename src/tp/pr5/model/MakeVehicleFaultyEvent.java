package tp.pr5.model;

public class MakeVehicleFaultyEvent extends Event {
	
	//########################################################################
	// Atributos
	//########################################################################
	
	private String[] ids;
	private int duration;
	
	//########################################################################
	// Constructores
	//########################################################################

	public MakeVehicleFaultyEvent(int time, Integer duration, String [] ids) {
		super(time);
		this.duration = duration;
		this.ids = ids;
	}
	
	//########################################################################
	// Métodos
	//########################################################################

	@Override
	public void execute(RoadMap roadMap, int time) {
		if (this.time == time) {
			for (String id : ids) roadMap.makeFaulty(id, duration);
		}
	}
	
	@Override
	public String toString() {
		return "Faulty";
	}

}
