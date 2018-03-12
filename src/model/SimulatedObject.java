package model;

import ini.IniSection;

public abstract class SimulatedObject {
	
	//########################################################################
	// Atributos
	//########################################################################
	
	private String id;
	
	//########################################################################
	// Constructores
	//########################################################################
	
	public SimulatedObject(String id) {
		this.id = id;
	}
	
	//########################################################################
	// Métodos
	//########################################################################
	
	public String getId() {
		return id;
	}
	
	public String toString() {
		return id;
	}
	
	public String generateReport(int i) {
		return null;
	}
	
	//########################################################################
	// Métodos abstractos
	//########################################################################
	
	protected abstract void fillReportDetails(IniSection iniSection);
	
	protected abstract String getReportSectionTag();
	
	abstract void advance(int i);

}
