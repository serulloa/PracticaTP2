package tp.pr4.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import tp.pr4.ini.Ini;
import tp.pr4.ini.IniSection;
import tp.pr4.model.Event;
import tp.pr4.model.SimulatorError;
import tp.pr4.model.TrafficSimulator;

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
	
	public Controller(TrafficSimulator sim, int ticks, OutputStream output, InputStream input) {
		this.sim = sim;
		this.ticks = ticks;
		this.outputStream = output;
		this.inputStream = input;
	}
	
	public Controller(TrafficSimulator sim, OutputStream output) {
		this.sim = sim;
		this.outputStream = output;
		
		this.ticks = 0;
		this.inputStream = null;
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
		} catch (SimulatorError e) {
			System.err.println(e);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.err.println("ERROR: Se ha producido un error en el parseo.");
		}
	}
	
	public void reset() {
		//TODO
	}
	
	public void setOutputStream(OutputStream output) {
		this.outputStream = output;
	}
	
	public void run(int time) {
		try {
			loadEvents(inputStream);
			sim.run(ticks);
		} catch (SimulatorError e) {
			System.err.println(e);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.err.println("ERROR: Se ha producido un error en el parseo.");
		}
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
