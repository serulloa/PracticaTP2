package tp.pr4.model;

import java.util.ArrayList;

import tp.pr4.ini.IniSection;
import tp.pr4.misc.SortByLocation;

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
		int baseSpeed = calculateBaseSpeed();
		int speed = reduceSpeedFactor(baseSpeed);
		
		for (Vehicle v : vehicles) {
			if (v.getFaultyTime() > 0) v.setSpeed(0);
			else v.setSpeed(speed);
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
		String report = "";
		
		report += "[road_report]\n";
		report += "id = " + this.getId() + "\n";
		report += "time = " + "\n"; //TODO
		report += "state = ";
		
		for (int i = 0; i < vehicles.size(); i++) {
			report += "(" + vehicles.get(i).getId() + "," + vehicles.get(i).getLocation() + ")";
			
			if(i < vehicles.size()-1) report += ",";
		}
		
		return report;
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
		iniSection.setValue("length", length);
		iniSection.setValue("maxSpeed", maxSpeed);
		iniSection.setValue("srcJunc", srcJunc);
		iniSection.setValue("desJunc", desJunc);
		iniSection.setValue("vehicles", vehicles);
	}
	
	protected int calculateBaseSpeed() {
		int baseSpeed = (maxSpeed / vehicles.size()) + 1;
		return baseSpeed;
	}
	
	protected int reduceSpeedFactor(int baseSpeed) {
		boolean obstacles = false;
		int reductionFactor = 1;
		
		for(int i = 0; i < vehicles.size() && !obstacles; i++) {
			if(vehicles.get(i).getFaultyTime() > 0) obstacles = true;
		}
		
		if (obstacles) reductionFactor = 2;
		
		return baseSpeed/reductionFactor;
	}

}
