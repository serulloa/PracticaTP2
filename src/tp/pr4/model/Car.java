package tp.pr4.model;

import java.util.List;
import java.util.Random;

import tp.pr4.ini.IniSection;

public class Car extends Vehicle {

	protected int lastFaultyKm;
	protected int resistance;
	protected int maxFaultDuration;
	protected double probability;
	protected Random random;
	
	public Car(String id, int maxSpeed, int maxFaultDuration, double probability, long seed, int resistance, List<Junction> itinerary) {
		super(id, maxSpeed, itinerary);
		this.maxFaultDuration = maxFaultDuration;
		this.probability = probability;
		this.random = new Random(seed);
		this.resistance = resistance;
		this.lastFaultyKm = 0;
	}
	
	@Override
	public void advance(int time) {
		if (faultyTime <= 0 && (kilometrage - lastFaultyKm) > resistance && random.nextDouble() < probability) {
			super.makeFaulty(random.nextInt(maxFaultDuration)+1);
			lastFaultyKm = kilometrage;
		}
		
		super.advance(time);
	}
	
	@Override
	protected void fillReportDetails(IniSection iniSection) {
		super.fillReportDetails(iniSection);
		iniSection.setValue("type", "car");
	}
	
}
