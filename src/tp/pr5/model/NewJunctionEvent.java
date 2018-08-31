package tp.pr5.model;

public class NewJunctionEvent extends Event {
	
	//########################################################################
	// Atributos
	//########################################################################
	
	protected String junctionId;
	
	//########################################################################
	// Constructores
	//########################################################################
	
	public NewJunctionEvent(int time, String id) {
		super(time);
		this.junctionId = id;
	}
	
	//########################################################################
	// Métodos
	//########################################################################

	@Override
	public void execute(RoadMap roadMap, int time) {
		if (this.time == time && validateId(junctionId)) roadMap.addJunction(new Junction(junctionId));
	}
	
	@Override
	public String toString() {
		return "Junction";
	}

}
