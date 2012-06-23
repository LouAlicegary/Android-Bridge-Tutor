package com.example.bridge_db;

import java.io.IOException;
import java.sql.SQLException;

public class Tester {

	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() { 
			public void run() {
				try {
					new Grid_Viewer();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}); 
	}
	
}