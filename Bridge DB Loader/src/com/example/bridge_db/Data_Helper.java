package com.example.bridge_db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Data_Helper {

   Statement stat;
   Connection conn;
   
   private String db_name;
   private String table_name;
   
   public Data_Helper(String in_db_name, String in_table_name) throws ClassNotFoundException,SQLException {
	   
	   db_name = in_db_name;
	   table_name = in_table_name;
	   
	   Class.forName("org.sqlite.JDBC");  
	   conn = DriverManager.getConnection("jdbc:sqlite:" + db_name);
	    
	   stat = conn.createStatement();
	   stat.executeUpdate("create table if not exists " + table_name + " (id INTEGER PRIMARY KEY AUTOINCREMENT, set_name TEXT, question_num INTEGER, west_hand BLOB, north_hand BLOB, east_hand BLOB, south_hand BLOB, dealer TEXT, bidding_list BLOB, final_contract TEXT);");
   }
   
   public void insert(String in_set_name, int in_question_num, List<String> in_west_hand, List<String> in_north_hand, List<String> in_east_hand, List<String> in_south_hand, String in_dealer, List<String> in_bidding_list, String in_final_bid) throws IOException, SQLException {
	   
	    PreparedStatement prep = conn.prepareStatement("insert into " + table_name + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
	    
	    prep.setNull(1, java.sql.Types.INTEGER);
	    prep.setString(2, in_set_name);
	    prep.setInt(3, in_question_num);
	    prep.setBytes(4, getBytes(in_west_hand));
	    prep.setBytes(5, getBytes(in_north_hand));
	    prep.setBytes(6, getBytes(in_east_hand));
	    prep.setBytes(7, getBytes(in_south_hand));
	    prep.setString(8, in_dealer);
	    prep.setBytes(9, getBytes(in_bidding_list));
	    prep.setString(10, in_final_bid );
	    prep.addBatch();
	    
	    conn.setAutoCommit(false);
	    prep.executeBatch();
	    conn.setAutoCommit(true);
   }

   public void deleteAll() {
	   /*
	  Log.d("Debug_Program", "Call to DataHelper.DeleteAll()");
      this.db.delete(TABLE_NAME, null, null);
      */
   }

   public int getNumberOfRecords() throws SQLException {
	   ResultSet r = stat.executeQuery("SELECT COUNT(*) AS rowcount FROM " + table_name);
	   r.next();
	   int count = r.getInt("rowcount");
	   r.close();
	   return count;
   }
   
   @SuppressWarnings("unchecked")
   public List<Object> getAllData() throws IOException, SQLException {
	   
	    ResultSet rs = stat.executeQuery("select * from " + table_name + ";");
	    
	    ArrayList<Object> big_temp = new ArrayList<Object>();
	    
	    while (rs.next()) {
	    	ArrayList<Object> temp = new ArrayList<Object>();
	    	temp.add(rs.getInt("id"));
	    	temp.add(rs.getString("set_name"));
	    	temp.add(rs.getInt("question_num"));
	    	temp.add((List<String>)toObject(rs.getBytes("west_hand")));
	    	temp.add((List<String>)toObject(rs.getBytes("north_hand")));
	    	temp.add((List<String>)toObject(rs.getBytes("east_hand")));
	    	temp.add((List<String>)toObject(rs.getBytes("south_hand")));
	    	temp.add(rs.getString("dealer"));
	    	temp.add((List<String>)toObject(rs.getBytes("bidding_list")));
	    	temp.add(rs.getString("final_contract"));
	    	
	    	big_temp.add(temp);
	    }
	    
	    rs.close();
	   
	   return big_temp;
	   
   }

   public void printContentsToTerminal() {
	   /*
	   table_data = new Object;
	    
	    for (int i=0; i < big_temp.size(); i++) {
	    	for (int j=0; j < 10; j++) {
	    		table_data[i][j] = (((ArrayList<Object>)big_temp.get(i)).get(j)).toString();
	    		System.out.println(table_data[i][j]);
	    	}
	    }
	    */
   }
   
   public static byte[] getBytes(Object object) {
   	   java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream(5000);
   	   
   	   try {
   		   java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(baos);
   		   oos.writeObject(object);
   	   }
   	   catch(java.io.IOException ioe) {
   		   System.out.println("getBytes() writeObject problem");
   	   }
   	   
   	   return baos.toByteArray();
      }

   public static Object toObject(byte[] bytes) {
	   
	   Object object = null;

	   try {
		   object = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(bytes)).readObject();
	   }
	   catch(java.io.IOException ioe) {} 
	   catch (ClassNotFoundException e) {}

	   return object;
   }
   
}