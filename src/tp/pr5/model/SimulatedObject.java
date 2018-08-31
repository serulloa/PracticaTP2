package tp.pr5.model;

import tp.pr5.ini.Ini;
import tp.pr5.ini.IniSection;

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
		Ini ini = new Ini();
		IniSection iniSection = new IniSection(getReportSectionTag());
		
		iniSection.setValue("id", this.id);
		iniSection.setValue("time", i);
		
		fillReportDetails(iniSection);
		ini.addsection(iniSection);
		
		return ini.toString();
	}
	
	//########################################################################
	// Métodos abstractos
	//########################################################################
	
	protected abstract void fillReportDetails(IniSection iniSection);
	
	protected abstract String getReportSectionTag();
	
	abstract void advance(int i);

}
