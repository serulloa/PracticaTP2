package model;

import java.util.List;
import ini.IniSection;

public class Vehicle extends SimulatedObject {
	
	//########################################################################
	// Atributos
	//########################################################################
	
	protected int maxSpeed; 			// máxima velocidad
	protected int currSpeed; 			// velocidad actual
	private Road road; 					// carretera por la que viaja
	private int location; 				// localización en la carretera (desde 0)
	protected List<Junction> itinerary;	// lista de cruces
	protected int kilometrage; 			// distancia recorrida por el vehículo
	protected int faultyTime; 			// tiempo que resta en estado averiado
	protected boolean atJunction; 		// true si ha entrado en cola de cruce
	protected boolean arrived; 			// true cuando el coche llega a su destino
	
	//########################################################################
	// Constructores
	//########################################################################
	
	public Vehicle(String id, int speed, List<Junction> itinerary) {
		super(id);

		this.maxSpeed = speed;
		this.itinerary = itinerary;
	}
	
	//########################################################################
	// Métodos
	//########################################################################
	
	public Road getRoad() {
		return road;
	}
	
	public int getMaxSpeed() {
		return maxSpeed;
	}
	
	public int getSpeed() {
		return currSpeed;
	}
	
	public int getLocation() {
		return location;
	}
	
	public int getKilometrage() {
		return kilometrage;
	}
	
	public int getFaultyTime() {
		return faultyTime;
	}
	
	public boolean atDestination() {
		return arrived;
	}
	
	public List<Junction> getItinerary() {
		return itinerary;
	}
	
	/**
	 * Pone el estado del vehículo en modo avería 
	 * durante n pasos. Si el vehículo ya está averiado acumula n al valor 
	 * del contador de tiempo de avería (faultyTime).
	 * 
	 * @param n El tiempo de la siguiente avería.
	 */
	void makeFaulty(int n) {
		faultyTime += n;
		currSpeed = 0;
	}
	
	/**
	 * Pone la velocidad actual del vehículo a
	 * la que se indica por parámetro. Este método es utilizado por las 
	 * carreteras para fijar la velocidad de los vehículos. Si la velocidad 
	 * que se desea poner excede la velocidad máxima del vehículo se pone 
	 * igual a dicha velocidad máxima.
	 * 
	 * @param speed
	 */
	void setSpeed(int speed) {
		if (currSpeed > maxSpeed) currSpeed = maxSpeed;
		else currSpeed = speed;
	}
	
	/*
	 * (non-Javadoc)
	 * @see model.SimulatedObject#advance(int)
	 * 
	 * Con esta operación el vehículo avanza.
	 * Primero comprueba si esta averiado o no. Si está averiado decrementa
	 * faultyTime en 1 y no se mueve. Si no está averiado (faultyTime es 0)
	 * entonces avanza su posición según su velocidad actual. La nueva
	 * localización es la localización anterior mas la velocidad actual. Si
	 * la nueva localización es igual o mayor a la longitud de la carretera
	 * entonces el vehículo entrara en la cola del correspondiente cruce.
	 * Los vehículos que esperan en la cola de un cruce no pueden avanzar y
	 * permanecen en esta cola hasta que el cruce determine que el vehículo
	 * debe moverse a la siguiente carretera, invocando el método
	 * moveToNextRoad().
	 * 
	 */
	@Override
	void advance(int time) {
		if (faultyTime == 0 && !atJunction) {
			location = location + currSpeed;
			kilometrage += currSpeed;
			
			if (location >= road.getLength()) {
				atJunction = true;
				currSpeed = 0;
				kilometrage -= location - road.getLength();
				location = road.getLength();
				
				if (road.getDestination().equals(itinerary.get(0))) road.getDestination().enter(this);
			}
		}
		else if (faultyTime > 0) faultyTime--;
	}
	
	/**
	 * Solicita al vehículo que se mueva a la
	 * siguiente carretera. Para ello el vehículo sale de su carretera 
	 * actual y entra en la siguiente que esta en su itinerario, en la 
	 * localización 0. Observa que la primera vez que se ejecuta este método 
	 * no hay ninguna carretera saliente puesto que no ha entrado todavía en 
	 * ninguna carretera. Además si el vehículo sale de la última carretera 
	 * de su itinerario no entra en ninguna y arrived toma el valor true.
	 */
	void moveToNextRoad() {
//		Junction junction = null;
//		
//		if (road != null)
//			junction = road.getDestination();
//		else
//			junction = itinerary.get(0);
//		
//		if (junction == itinerary.get(itinerary.size())) {
//			arrived = true;
//			currSpeed = 0;
//		}
//		else {
//			road = junction.roadTo(itinerary.get(itinerary.indexOf(junction) + 1));
//			road.enter(this);
//			location = 0;
//		}
		
		boolean next = false;
		
		if (road != null) {
			location = 0;
			currSpeed = 0;
			road.exit(this);
			
			if (itinerary.size() == 1) arrived = true;
			else {
				next = true;
				atJunction = false;
			}
		} 
		else next = true;
		
		if (next) {
			road = itinerary.get(0).roadTo(itinerary.get(1));
			road.enter(this);
			itinerary.remove(0);
		}
	}
	
	@Override
	protected String getReportSectionTag() {
		String report = "";
		
		report += "[vehicle_report]\n";
		report += "id = " + this.getId() + "\n";
		report += "time = " + "\n"; //TODO
		report += "speed = " + currSpeed + "\n";
		report += "kilometrage = " + kilometrage + "\n";
		report += "faulty = " + faultyTime + "\n";
		report += "location = (" + road.getId() + "," + location + ")\n";
		
		return report;
	}
	
	/* (non-Javadoc)
	 * @see model.SimulatedObject#fillReportDetails(ini.IniSection)
	 * 
	 * Rellena los 
	 * valores de una sección ini con los atributos del vehículo para 
	 * mostrar un informe de su estado en uno de los pasos de simulación.
	 */
	@Override
	protected void fillReportDetails(IniSection iniSection) {
		iniSection.setValue("maxSpeed", maxSpeed);
		iniSection.setValue("currSpeed", currSpeed);
		iniSection.setValue("road", road);
		iniSection.setValue("location", location);
		iniSection.setValue("itinerary", itinerary);
		iniSection.setValue("kilometrage", kilometrage);
		iniSection.setValue("faultyTime", faultyTime);
		iniSection.setValue("atJunction", atJunction);
		iniSection.setValue("arrived", arrived);
	}

}
