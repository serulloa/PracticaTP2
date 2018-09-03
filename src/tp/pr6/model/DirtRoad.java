package tp.pr6.model;

import tp.pr6.ini.IniSection;

public class DirtRoad extends Road {
	
	public DirtRoad(String id, int length, int maxSpeed, Junction srcJunc, Junction desJunc) {
		super(id, length, maxSpeed, srcJunc, desJunc);
	}
	
	@Override
	protected int reduceSpeedFactor(int speed) {
		int numObstacles = 0;
		
		for (Vehicle vehicle : getVehicles())
			if (vehicle.getFaultyTime() > 1) numObstacles++;
		
		return speed/(1 + numObstacles);
	}
	
	@Override
	protected int calculateBaseSpeed() {
		return maxSpeed;
	}
	
	@Override
	protected void fillReportDetails(IniSection iniSection) {
		super.fillReportDetails(iniSection);
		iniSection.setValue("type", "dirt");
	}

}
