package tp.pr6.model;

public class MostCrowdedJunction extends JunctionWithTimeSlice {
	
	public MostCrowdedJunction(String id) {
		super(id);
	}
	
	@Override
	protected IncomingRoad createIncomingRoadQueue(Road road) {
		return new IncomingRoadWithTimeSlice(road);
	}
	
	@Override
	protected void switchLights() {
		int index = -1;
		boolean foundGreen = false;
		int maxQueueIndex = -1;
		
		for (int i = 0; i < incomingRoads.size(); i++) {
			if (incomingRoads.get(i).hasGreenLight()) {
				index = i;
				foundGreen = true;
			}
			else if (maxQueueIndex == -1 || 
					(incomingRoads.get(i).queue.size() > incomingRoads.get(maxQueueIndex).queue.size())) 
				maxQueueIndex = i;
		}
		
		if (!incomingRoads.isEmpty()) {
			if (foundGreen) {
				IncomingRoadWithTimeSlice currentRoad = (IncomingRoadWithTimeSlice) incomingRoads.get(index);
				IncomingRoadWithTimeSlice nextRoad;
				if (incomingRoads.size() > 1) nextRoad = (IncomingRoadWithTimeSlice) incomingRoads.get(maxQueueIndex);
				else nextRoad = (IncomingRoadWithTimeSlice) incomingRoads.get(index);
				
				turnLightOff(currentRoad);
				currentRoad.setUsedTimeUnits(0);
				currentRoad.setFullyUsed(true);
				currentRoad.setUsed(false);
				
				turnLightOn(nextRoad);
				nextRoad.setTimeSlice(Math.max(nextRoad.queue.size()/2, 1));
			}
			else {
				IncomingRoadWithTimeSlice nextRoad = (IncomingRoadWithTimeSlice) incomingRoads.get(maxQueueIndex);
				turnLightOn(nextRoad);
				nextRoad.setTimeSlice(Math.max(nextRoad.queue.size()/2, 1));
			}
		}
	}
	
	protected void turnLightOff(IncomingRoadWithTimeSlice road) {
		road.green = false;
	}
	
	protected void turnLightOn(IncomingRoadWithTimeSlice road) {
		road.green = true;
	}

}