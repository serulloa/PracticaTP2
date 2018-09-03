package tp.pr6.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import tp.pr6.model.IncomingRoad;
import tp.pr6.model.Junction;

public class JunctionsTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Junction> junctions;
	private String[] columnNames = {"ID", "Queues"};
	
	public void setJunctions(List<Junction> junctions) {
		this.junctions = junctions;
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
		if (junctions != null) result = junctions.size();
		return result;
	}

	@Override
	public Object getValueAt(int row, int column) {
		Object result = null;
		
		switch (column) {
		case 0:
			result = junctions.get(row).getId();
			break;
		case 1:
			result = "";
			if (!junctions.get(row).getRoadsInfo().isEmpty()) {
				for (IncomingRoad ir : junctions.get(row).getRoadsInfo())
					result += "(" + ir.toString() + ") ";
			}
		}
		
		return result;
	}
	
	public void update() {
		fireTableDataChanged();
	}

}
