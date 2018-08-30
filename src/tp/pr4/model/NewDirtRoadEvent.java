package tp.pr4.model;

public class NewDirtRoadEvent extends NewRoadEvent {
	
	public NewDirtRoadEvent(int time, String id, String src, String dest, int maxSpeed, int length) {
		super(time, id, src, dest, maxSpeed, length);
	}
	
	@Override
	public void execute(RoadMap roadMap, int time) {
		if (this.time == time && validateId(roadId) && roadMap.hasJunction(toId) && roadMap.hasJunction(fromId)) {
			Junction toJunction = roadMap.getJunction(toId);
			Junction fromJunction = roadMap.getJunction(fromId);
			DirtRoad road = new DirtRoad(roadId, length, maxSpeed, fromJunction, toJunction);
			
			roadMap.addRoad(road);
			fromJunction.addOutgoingRoad(road);
			toJunction.addIncomingRoad(road);
		}
	}

}
