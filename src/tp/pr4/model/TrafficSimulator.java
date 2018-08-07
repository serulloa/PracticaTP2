package tp.pr4.model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import tp.pr4.misc.SortedArrayList;

public class TrafficSimulator {
	
	//########################################################################
	// Atributos
	//########################################################################
	
	private RoadMap map; // estructura que almacena los objetos simulados
	ArrayList<Event> events; // lista de eventos que hay que ejecutar
	private int time; // contador de pasos del simulador
	private OutputStream outStream; // flujo de salida utilizado
	
	//########################################################################
	// Constructores
	//########################################################################
	
	public TrafficSimulator(OutputStream outStream) {
		this.outStream = outStream;
		this.map = new RoadMap();
		this.events = new SortedArrayList<Event>();
		this.time = 0;
	}
	
	//########################################################################
	// Métodos
	//########################################################################
	
	public void run(int ticks) throws IOException {
		boolean done = false;
		
		while(time < ticks) {
			for(int i = 0; i < events.size() && !done; i++) {
				if(events.get(i).getScheduledTime() == time)
					events.get(i).execute(map, time);
				else if(events.get(i).getScheduledTime() >= time)
					done = true;
			}
			
			List<Road> roads = map.getRoads();
			for (Road road : roads) {
				road.advance(time);
			}
			
			List<Junction> junctions = map.getJunctions();
			for (Junction junction : junctions) {
				junction.advance(time);
			}
			
			time++;
			
			this.outStream.write(map.generateReport(time).getBytes());
		}
	}
	
	public void addEvent(Event e) {
		this.events.add(e);
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
