package tp.pr4.model;

public class RoundRobinJunction extends JunctionWithTimeSlice {
	
	protected int maxTimeSlice;
	protected int minTimeSlice;
	
	public RoundRobinJunction(String id, int maxTimeSlice, int minTimeSlice) {
		super(id);
		this.maxTimeSlice = maxTimeSlice;
		this.minTimeSlice = minTimeSlice;
	}
	
	@Override
	protected IncomingRoad createIncomingRoadQueue(Road road) {
		IncomingRoadWithTimeSlice incomingRoad = new IncomingRoadWithTimeSlice(road);
		incomingRoad.setTimeSlice(maxTimeSlice);
		return incomingRoad;
	}
	
	@Override
	protected void switchLights() {
		int index = -1;
		boolean foundGreen = false;
		
		for (int i = 0; i < incomingRoads.size() && !foundGreen; i++) {
			if (incomingRoads.get(i).hasGreenLight()) {
				index = i;
				foundGreen = true;
			}
		}
		
		if (!incomingRoads.isEmpty()) {
			if (foundGreen) {
				IncomingRoadWithTimeSlice road = (IncomingRoadWithTimeSlice) incomingRoads.get(index);
				
				if (road.getUsedTimeUnits() >= road.getTimeSlice()) {
					turnLightOff(road);
					
					if (road.isFullyUsed() && road.isUsed()) road.setTimeSlice(Math.min(road.getTimeSlice()+1, maxTimeSlice));
					if (!road.isUsed()) road.setTimeSlice(Math.max(road.getTimeSlice()-1, minTimeSlice));
					
					road.setUsedTimeUnits(0);
					road.setFullyUsed(true);
					road.setUsed(false);
					
					road = (IncomingRoadWithTimeSlice) incomingRoads.get((index+1)%incomingRoads.size());
					turnLightOn(road);
				}
			}
			else turnLightOn((IncomingRoadWithTimeSlice) incomingRoads.get(0));
		}
	}
	
	protected void turnLightOff(IncomingRoadWithTimeSlice road) {
		road.green = false;
	}
	
	protected void turnLightOn(IncomingRoadWithTimeSlice road) {
		road.green = true;
	}

}
