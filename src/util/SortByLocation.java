package util;

import java.util.Comparator;

import model.Vehicle;

public class SortByLocation implements Comparator<Vehicle> {

	@Override
	public int compare(Vehicle o1, Vehicle o2) {
		return o1.getLocation()-o2.getLocation();
	}
	
}