package control;

import java.io.InputStream;
import java.io.OutputStream;

import model.TrafficSimulator;

public class Controller {
	
	//########################################################################
	// Atributos
	//########################################################################
	
	protected TrafficSimulator sim; 		// simulador que usa
	protected OutputStream outputStream; 	// flujo de salida
	protected InputStream inputStream; 		// flujo de entrada
	protected int ticks; 					// pasos de la simulación dados por el usuario
	EventBuilder[] eventBuilders = {}; 		// array de constructores de eventos
	
	//########################################################################
	// Constuctores
	//########################################################################
	
	public Controller(TrafficSimulator sim, OutputStream output, InputStream input) {
		this.sim = sim;
		this.outputStream = output;
		this.inputStream = input;
	}
	
	public Controller(TrafficSimulator sim, OutputStream output) {
		this.sim = sim;
		this.outputStream = output;
	}
	
	//########################################################################
	// Métodos
	//########################################################################
	
	public void setEventBuilders(EventBuilder[] eventBuilders) {
		this.eventBuilders = eventBuilders;
	}
	
	public EventBuilder[] getEventBuilders() {
		return this.eventBuilders;
	}
	
	public void run() {
		//TODO
	}
	
	public void reset() {
		//TODO
	}
	
	public void setOutputStream(OutputStream output) {
		this.outputStream = output;
	}
	
	public void run(int time) {
		//TODO
	}
	
	public void loadEvents(InputStream input) {
		//TODO
	}

}
