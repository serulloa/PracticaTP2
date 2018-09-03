package tp.pr6.model;

public class NewRoadEvent extends Event {
	
	//########################################################################
	// Atributos
	//########################################################################
	
	protected String roadId;
	protected String toId;
	protected String fromId;
	protected int maxSpeed;
	protected int length;
	
	//########################################################################
	// Constructores
	//########################################################################
	
	public NewRoadEvent(int time, String id, String fromId, String toId, int maxSpeed, int length) {
		super(time);
		this.roadId = id;
		this.toId = toId;
		this.fromId = fromId;
		this.maxSpeed = maxSpeed;
		this.length = length;
	}
	
	//########################################################################
	// Métodos
	//########################################################################

	@Override
	public void execute(RoadMap roadMap, int time) {
		if (this.time == time && validateId(roadId) && roadMap.hasJunction(toId) && roadMap.hasJunction(fromId)) {
			Junction toJunction = roadMap.getJunction(toId);
			Junction fromJunction = roadMap.getJunction(fromId);
			Road road = new Road(roadId, length, maxSpeed, fromJunction, toJunction);
			
			roadMap.addRoad(road);
			fromJunction.addOutgoingRoad(road);
			toJunction.addIncomingRoad(road);
		}
	}
	
	@Override
	public String toString() {
		return "Road";
	}

}
