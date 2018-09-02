package tp.pr5.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import tp.pr5.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Vehicle> vehicles;
	private String[] columnNames = {"ID", "Road", "Location", "Speed", "Km", "Faulty Units", "Itinerary"};
	
	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
		fireTableStructureChanged();
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		int result = 0;
		if (vehicles != null) result = vehicles.size();
		return result;
	}

	@Override
	public Object getValueAt(int row, int column) {
		Object result = null;
		
		switch (column) {
		case 0:
			result = vehicles.get(row).getId();
			break;
		case 1:
			result = vehicles.get(row).getRoad();
			break;
		case 2:
			result = vehicles.get(row).getLocation();
			break;
		case 3:
			result = vehicles.get(row).getSpeed();
			break;
		case 4:
			result = vehicles.get(row).getKilometrage();
			break;
		case 5:
			result = vehicles.get(row).getFaultyTime();
			break;
		case 6:
			result = vehicles.get(row).getItinerary();
			break;
		}
		
		return result;
	}
	
	public void update() {
		fireTableDataChanged();
	}

}
