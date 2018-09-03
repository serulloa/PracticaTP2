package tp.pr6.model;

public class NewRoundRobinJunctionEvent extends NewJunctionEvent {
	
	protected int maxTimeSlice;
	protected int minTimeSlice;
	
	public NewRoundRobinJunctionEvent(int time, String id, int maxTimeSlice, int minTimeSlice) {
		super(time, id);
		this.maxTimeSlice = maxTimeSlice;
		this.minTimeSlice = minTimeSlice;
	}
	
	public void execute(RoadMap roadMap, int time) {
		if (this.time == time && validateId(junctionId)) roadMap.addJunction(new RoundRobinJunction(junctionId, maxTimeSlice, minTimeSlice));
	}

}
