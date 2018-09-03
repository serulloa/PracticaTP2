package tp.pr6.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import tp.pr6.model.Event;

public class EventsTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Event> events;
	private String[] columnNames = {"#", "Time", "Type"};
	
	public void setEvents(List<Event> events) {
		this.events = events;
		fireTableStructureChanged();
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		int result = 0;
		if (events != null) result = events.size();
		return result;
	}

	@Override
	public Object getValueAt(int row, int column) {
		Object result = null;
		
		switch (column) {
		case 0:
			result = row;
			break;
		case 1:
			result = events.get(row).getScheduledTime();
			break;
		case 2:
			result = events.get(row).toString();
		}
		
		return result;
	}
	
	public void update() {
		fireTableDataChanged();
	}

}
