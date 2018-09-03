package tp.pr6.misc;

import java.util.Comparator;

import tp.pr6.model.Vehicle;

public class SortByLocation implements Comparator<Vehicle> {

	@Override
	public int compare(Vehicle o1, Vehicle o2) {
		return o2.getLocation()-o1.getLocation();
	}
	
}