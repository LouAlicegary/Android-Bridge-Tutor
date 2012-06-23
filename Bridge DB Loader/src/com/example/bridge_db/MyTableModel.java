package com.example.bridge_db;

import java.awt.Point;
import java.util.Hashtable;
import java.util.List;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("unchecked")
public class MyTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 7943074088281408176L;

	private Hashtable lookup;
	private int rows;
	private final int columns;
	private final String headers[];

	public MyTableModel(int in_rows, String columnHeaders[]) {
		if ((in_rows < 0) || (columnHeaders == null)) {
		      throw new IllegalArgumentException("Invalid row count/columnHeaders");
		}
		
		this.rows = in_rows;
		this.columns = columnHeaders.length;
		headers = columnHeaders;
		lookup = new Hashtable();
	}

	public int getColumnCount() {
		return columns;
	}

	public int getRowCount() {
		return rows;
	}

	public String getColumnName(int column) {
		 return headers[column];
	}

	public String getValueAt(int row, int column) { //Changed from Object to String returner
		String this_string = new String(lookup.get(new Point(row, column)).toString()); 
		
		return this_string;
	}
	
	public void addRow(int in_row, List<Object> in_list) {
		for (int i=0; i < 10; i++) {
			setValueAt((Object)in_list.get(i), in_row, i);
		}
		
		if (in_row == this.rows) {
			this.rows++;
		}
	}

	public void setValueAt(Object value, int row, int column) {
		 if ((rows < 0) || (columns < 0)) {
			 throw new IllegalArgumentException("Invalid row/column setting");
		 }
		 
		 //if ((row < rows) && (column < columns)) {
			 lookup.put(new Point(row, column), value);
		 //}
	}
}
