package tp.pr6.model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import tp.pr6.misc.SortedArrayList;

public class TrafficSimulator {
	
	//########################################################################
	// Atributos
	//########################################################################
	
	private RoadMap map; // estructura que almacena los objetos simulados
	ArrayList<Event> events; // lista de eventos que hay que ejecutar
	private int time; // contador de pasos del simulador
	private OutputStream outStream; // flujo de salida utilizado
	private List<TrafficSimulatorObserver> observers;
	
	//########################################################################
	// Constructores
	//########################################################################
	
	public TrafficSimulator(OutputStream outStream) {
		this.outStream = outStream;
		this.map = new RoadMap();
		this.events = new SortedArrayList<Event>();
		this.time = 0;
		this.observers = new ArrayList<TrafficSimulatorObserver>();
	}
	
	//########################################################################
	// Métodos
	//########################################################################
	
	public void run(int ticks) throws IOException {
		while(time < ticks) {
			try {
				for (Event event : events) 
					event.execute(map, time);
				
				List<Road> roads = map.getRoads();
				for (Road road : roads) {
					road.advance(time);
				}
				
				List<Junction> junctions = map.getJunctions();
				for (Junction junction : junctions) {
					junction.advance(time);
				}
				
				time++;
				
				notifyAdvanced();
				
				if (outStream != null) outStream.write(map.generateReport(time).getBytes());
			} catch (SimulatorError e) {
				notifyError(e);
			}
		}
	}
	
	public void addEvent(Event e) {
		this.events.add(e);
		notifyEventAdded();
	}
	
	public void reset() {
		map.clear();
		events.clear();
		
		time = 0;
		
		notifyReset();
	}
	
	public void setOutputStream(OutputStream outStream) {
		this.outStream = outStream;
	}
	
	@Override
	public String toString() {
		return null;
	}
	
	public interface TrafficSimulatorObserver {
		public void registered(int time, RoadMap map, List<Event> events); 
		public void reset(int time, RoadMap map, List<Event> events); 
		public void eventAdded(int time, RoadMap map, List<Event> events); 
		public void advanced(int time, RoadMap map, List<Event> events); 
		public void simulatorError(int time, RoadMap map, List<Event> events, SimulatorError e);
	}
	
	private void notifyRegistered(TrafficSimulatorObserver o) {
		o.registered(time, map, events);
	}
	
	private void notifyReset() {
		for (TrafficSimulatorObserver observer : observers) observer.reset(time, map, events);
	}
	
	private void notifyEventAdded() {
		for (TrafficSimulatorObserver observer : observers) observer.eventAdded(time, map, events);
	}
	
	private void notifyAdvanced() {
		for (TrafficSimulatorObserver observer : observers) observer.advanced(time, map, events);
	}
	
	private void notifyError(SimulatorError e) {
		for (TrafficSimulatorObserver observer : observers) observer.simulatorError(time, map, events, e);
	}
	
	public void addObserver(TrafficSimulatorObserver o) {
		observers.add(o);
		notifyRegistered(o);
	}
	
	public void removeObserver(TrafficSimulatorObserver o) {
		observers.remove(o);
	}
	
}
