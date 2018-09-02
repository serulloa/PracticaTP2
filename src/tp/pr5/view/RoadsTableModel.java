package tp.pr5.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import tp.pr5.model.Road;

public class RoadsTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Road> roads;
	private String[] columnNames = {"ID", "Source", "Destination", "Length", "Max Speed", "Vehicles"};
	
	public void setRoads(List<Road> roads) {
		this.roads = roads;
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
		if (roads != null) result = roads.size();
		return result;
	}

	@Override
	public Object getValueAt(int row, int column) {
		Object result = null;
		
		switch (column) {
		case 0:
			result = roads.get(row).getId();
			break;
		case 1:
			result = roads.get(row).getSource().getId();
			break;
		case 2:
			result = roads.get(row).getDestination().getId();
			break;
		case 3:
			result = roads.get(row).getLength();
			break;
		case 4:
			result = roads.get(row).getMaxSpeed();
			break;
		case 5:
			result = roads.get(row).getVehicles();
			break;
		}
		
		return result;
	}
	
	public void update() {
		fireTableDataChanged();
	}

}
