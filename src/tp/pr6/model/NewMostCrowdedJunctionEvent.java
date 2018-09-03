package tp.pr6.model;

public class NewMostCrowdedJunctionEvent extends NewJunctionEvent {
	
	public NewMostCrowdedJunctionEvent(int time, String id) {
		super(time, id);
	}
	
	@Override
	public void execute(RoadMap roadMap, int time) {
		if (this.time == time && validateId(junctionId)) roadMap.addJunction(new MostCrowdedJunction(junctionId));
	}

}
