package tp.pr6.model;

public class IncomingRoadWithTimeSlice extends IncomingRoad {
	
	private int timeSlice;
	private int usedTimeUnits;
	private boolean fullyUsed;
	private boolean used;
	
	public IncomingRoadWithTimeSlice(Road road) {
		super(road);
		this.timeSlice = 0;
		this.usedTimeUnits = 0;
		this.fullyUsed = false;
		this.used = false;
	}
	
	@Override
	protected void advanceFirstVehicle() {
		usedTimeUnits++;
		
		if (!queue.isEmpty()) {
			queue.get(0).moveToNextRoad();
			queue.remove(0);
			used = true;
			fullyUsed = true;
		}
		else fullyUsed = false;
	}
	
	public Road getRoad() {
		return road;
	}
	
	public int getTimeSlice() {
		return timeSlice;
	}
	
	protected void setTimeSlice(int timeSlice) {
		this.timeSlice = timeSlice;
	}
	
	public int getUsedTimeUnits() {
		return usedTimeUnits;
	}
	
	protected void setUsedTimeUnits(int usedTimeUnits) {
		this.usedTimeUnits = usedTimeUnits;
	}
	
	public boolean isFullyUsed() {
		return fullyUsed;
	}

	protected void setFullyUsed(boolean fullyUsed) {
		this.fullyUsed = fullyUsed;
	}

	public boolean isUsed() {
		return used;
	}

	protected void setUsed(boolean used) {
		this.used = used;
	}

	@Override
	public String toString() {
		String line = "";
		String timeRemaining = "";
		
		if (timeSlice - usedTimeUnits > 0) timeRemaining += timeSlice - usedTimeUnits;
		
		line += road.getId() + ",";
		if (green) line += "green:" + timeRemaining + ",";
		else line += "red,";
		
		line += printQueue();
		
		return line;
	}

}
