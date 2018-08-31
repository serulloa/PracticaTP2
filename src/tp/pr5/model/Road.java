package tp.pr5.model;

import java.util.ArrayList;

import tp.pr5.ini.IniSection;
import tp.pr5.misc.SortByLocation;
import tp.pr5.misc.SortedArrayList;

public class Road extends SimulatedObject {
	
	//########################################################################
	// Atributos
	//########################################################################
	
	protected int length; 			// longitud de la carretera
	protected int maxSpeed; 		// límite máximo de velocidad
	protected Junction srcJunc; 	// cruce origen de la carretera
	protected Junction desJunc; 	// cruce destino de la carretera
	private ArrayList<Vehicle> vehicles; // lista de vehículos que circulan por la carretera
	
	//########################################################################
	// Constructores
	//########################################################################
	
	public Road(String id, int length, int maxSpeed, Junction srcJunc, Junction desJunc) {
		super(id);
		
		this.length = length;
		this.maxSpeed = maxSpeed;
		this.srcJunc = srcJunc;
		this.desJunc = desJunc;
		this.vehicles = new SortedArrayList<Vehicle>();
	}
	
	//########################################################################
	// Métodos
	//########################################################################
	
	public Junction getSource() {
		return srcJunc;
	}
	
	public Junction getDestination() {
		return desJunc;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getMaxSpeed() {
		return maxSpeed;
	}
	
	public ArrayList<Vehicle> getVehicles() {
		return vehicles;
	}
	
	/* (non-Javadoc)
	 * @see model.SimulatedObject#advance(int)
	 * 
	 * este método es invocado por el
	 * simulador para dar un paso en el estado de la carretera y, en
	 * particular, para decir a cada coche de esa carretera que avance.
	 */
	@Override
	void advance(int time) {
		int speed = calculateBaseSpeed();
		boolean faultyAhead = false;
		
		if(speed > maxSpeed) speed = maxSpeed;
		
		for (Vehicle v : vehicles) {
			if (v.getFaultyTime() > 0) {
				faultyAhead = true;
				v.setSpeed(0);
			}
			else {
				if (faultyAhead) speed = reduceSpeedFactor(speed);
				v.setSpeed(speed);
			}
			v.advance(time);
		}
		
		vehicles.sort(new SortByLocation());
	}
	
	/**
	 * Añade un vehículo a la lista de
	 * vehículos de la carretera. Recuerda ordenar los vehículos después de
	 * sumar a la lista. Este método lo invocan los vehículos cuando
	 * necesitan entrar en la carretera.
	 * 
	 * @param vehicle Vehiculo que va a entrar en la carretera.
	 */
	void enter(Vehicle vehicle) {		
//		boolean added = false;
		
//		for (int i = 0; i < vehicles.size() && !added; i++) {
//			if(vehicle.getLocation() > vehicles.get(i).getLocation()) {
//				vehicles.add(i, vehicle);
//				added = true;
//			}
//		}
		
		vehicles.add(vehicle);
	}
	
	/**
	 * Elimina un vehículo de la
	 * carretera. Este método también lo invocan los vehículos cuando tienen
	 * que abandonar una carretera.
	 * 
	 * @param vehicle Vehiculo que va a salir de la carretera.
	 */
	public void exit(Vehicle vehicle) {
		vehicles.remove(vehicle);
	}
	
	@Override
	protected String getReportSectionTag() {
		return "road_report";
	}
	
	/* (non-Javadoc)
	 * @see model.SimulatedObject#fillReportDetails(ini.IniSection)
	 * 
	 * Rellena los
	 * valores de una sección ini con los atributos de la carretera para
	 * mostrar un informe de su estado en uno de los pasos de simulación.
	 */
	@Override
	protected void fillReportDetails(IniSection iniSection) {
		String state = "";
		
		
		if (!vehicles.isEmpty()) {
			for (Vehicle vehicle : vehicles)
				state += "(" + vehicle.getId() + "," + vehicle.getLocation() + "),";
			
			state = state.substring(0, state.length() - 1);
		}
		
		iniSection.setValue("state", state);
	}
	
	protected int calculateBaseSpeed() {
		int count = 1;
		if(vehicles.size() > count) count = vehicles.size();
		
		int baseSpeed = (maxSpeed / count) + 1;
		return baseSpeed;
	}
	
	protected int reduceSpeedFactor(int speed) {
		return speed/2;
	}

}
