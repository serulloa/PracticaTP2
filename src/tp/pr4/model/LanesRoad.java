package tp.pr4.model;

import tp.pr4.ini.IniSection;

public class LanesRoad extends Road {
	
	protected int numLanes;
	
	public LanesRoad(String id, int length, int maxSpeed, int numLanes, Junction srcJunc, Junction desJunc) {
		super(id, length, maxSpeed, srcJunc, desJunc);
		this.numLanes = numLanes;
	}
	
	@Override
	protected int reduceSpeedFactor(int speed) {
		int numObstacles = 0;
		int reductionFactor = 2;
		
		for (Vehicle vehicle : getVehicles())
			if (vehicle.getFaultyTime() > 1) numObstacles++;
		
		if (numLanes > numObstacles) reductionFactor = 1;
		
		return speed/reductionFactor;
	}
	
	@Override
	protected int calculateBaseSpeed() {
		return Math.min(maxSpeed, maxSpeed*numLanes/Math.max(getVehicles().size(), 1) + 1);
	}
	
	@Override
	protected void fillReportDetails(IniSection iniSection) {
		super.fillReportDetails(iniSection);
		iniSection.setValue("type", "lanes");
	}

}
