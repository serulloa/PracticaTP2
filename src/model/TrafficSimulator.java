package model;

import java.io.OutputStream;
import java.util.TreeMap;

public class TrafficSimulator {
	
	//########################################################################
	// Atributos
	//########################################################################
	
	private RoadMap map; // estructura que almacena los objetos simulados
	TreeMap<Event, Integer> events; // lista de eventos que hay que ejecutar
	private int time; // contador de pasos del simulador
	private OutputStream outStream; // flujo de salida utilizado
	
	//########################################################################
	// Constructores
	//########################################################################
	
	public TrafficSimulator(OutputStream outStream) {
		this.outStream = outStream;
	}
	
	//########################################################################
	// Métodos
	//########################################################################
	
	public void run(int time) {
		//TODO
	}
	
	public void addEvent(Event e) {
		if(e.getScheduledTime() >= time) events.put(e, e.getScheduledTime());
	}
	
	public void reset() {
		//TODO
	}
	
	public void setOutputStream(OutputStream outStream) {
		this.outStream = outStream;
	}
	
//	public void addObserver(TrafficSimulatorObserver observer) {
//		//TODO
//	}
//	
//	public void removeObserver(TrafficSimulatorObserver observer) {
//		//TODO
//	}
	
	@Override
	public String toString() {
		return null;
	}
	
}
