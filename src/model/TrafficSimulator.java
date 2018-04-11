package model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

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
	}
	
	//########################################################################
	// Métodos
	//########################################################################
	
	public void run(int ticks) throws IOException {
		//TODO
		int limit = time + ticks - 1;
		boolean done = false;
		
		while(time <= limit) {
			for(int i = 0; i < events.size() && !done; i++) {
				if(events.get(i).getScheduledTime() == time)
					events.get(i).execute(map, time);
				else if(events.get(i).getScheduledTime() >= time)
					done = true;
			}
			
			// TODO
			/*
			 * 2. invocar método avanzar carreteras
			 * 3. invocar método avanzar cruces
			 */
			
			time++;
			
			String report = this.toString();
			if(report != null && !report.isEmpty())
				outStream.write(report.getBytes());
		}
	}
	
	public void addEvent(Event e) {
		int time = e.getScheduledTime();
		boolean equalTime = false;
		boolean inserted = false;
		
		if(time >= this.time) {
			for(int i = 0; i < events.size() && !inserted; i++) {
				if(!equalTime) {
					if(time == events.get(i).getScheduledTime()) equalTime = true;
				}
				else {
					if(time != events.get(i).getScheduledTime()) {
						events.add(i, e);
						inserted = true;
					}
				}
			}
		}
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
