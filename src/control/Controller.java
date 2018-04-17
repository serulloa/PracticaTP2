package control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import ini.Ini;
import ini.IniSection;
import model.Event;
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
		try {
			loadEvents(inputStream);
			sim.run(ticks);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	public void loadEvents(InputStream input) throws IOException {
		//TODO
		Ini iniStream = new Ini(input);
		List<IniSection> sections = iniStream.getSections();
		
		for(IniSection is : sections) {
			this.sim.addEvent(parseEvent(is));
		}
	}
	
	private Event parseEvent(IniSection sec){
		Event result = null;
		for(EventBuilder i: this.eventBuilders){
			Event e = i.parse(sec);
			
			if(e!=null)
				result = e;
		}
		return result;
	}	

}
