package tp.pr4.model;

public class NewLanesRoadEvent extends NewRoadEvent {
	
	protected int numLanes;
	
	public NewLanesRoadEvent(int time, String id, String fromId, String toId, int maxSpeed, int length, int numLanes) {
		super(time, id, fromId, toId, maxSpeed, length);
		this.numLanes = numLanes;
	}
	
	@Override
	public void execute(RoadMap roadMap, int time) {
		if (this.time == time && validateId(roadId) && roadMap.hasJunction(toId) && roadMap.hasJunction(fromId)) {
			Junction toJunction = roadMap.getJunction(toId);
			Junction fromJunction = roadMap.getJunction(fromId);
			LanesRoad road = new LanesRoad(roadId, length, maxSpeed, numLanes, fromJunction, toJunction);
			
			roadMap.addRoad(road);
			fromJunction.addOutgoingRoad(road);
			toJunction.addIncomingRoad(road);
		}
	}

}
