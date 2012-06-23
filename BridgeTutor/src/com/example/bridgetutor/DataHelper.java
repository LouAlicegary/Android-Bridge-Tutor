package com.example.bridgetutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.bridgetutor.R;

public class DataHelper {

   private static final String DATABASE_NAME = "BridgeTutor.db";
   private static final int DATABASE_VERSION = 1;
   private static final String TABLE_NAME = "QuizQuestions";
   private Context context;
   private SQLiteDatabase db;
   
   public DataHelper(Context context) {
	  Log.d("Debug_Program", "Call to DataHelper Constructor");
	  this.context = context;
      OpenHelper openHelper = new OpenHelper(this.context);
      this.db = openHelper.getWritableDatabase();

      //this.insertStmt = this.db.compileStatement(INSERT);
   }
   
   /*
   public long insert(BiddingQuizQuestion BQQ) throws IOException {
	   
      ContentValues cv = new ContentValues();
      
      cv.put("west_hand", getBytes((Hand)BQQ.getWest()));
      
      cv.put("north_hand", getBytes((Hand)BQQ.getWest()));
      
      cv.put("east_hand", getBytes((Hand)BQQ.getWest()));
      
      cv.put("south_hand", getBytes((Hand)BQQ.getWest()));
      
      cv.put("dealer", BQQ.getDealer());
      
      cv.put("bidding_list", getBytes(BQQ.getBiddingList()));
      
      cv.put("final_contract", BQQ.getFinalContract());
      
      long row_num = this.db.insert(TABLE_NAME, null, cv);
      
      return row_num;
   }
   */
   
   public void deleteAll() {
	  Log.d("Debug_Program", "Call to DataHelper.DeleteAll()");
      this.db.delete(TABLE_NAME, null, null);
   }

   
   @SuppressWarnings("unchecked")
   public List<BiddingQuizQuestion> getAllData() throws IOException {
	   
	   Cursor cursor = this.db.query(TABLE_NAME, null, null, null, null, null, null);
	   List<BiddingQuizQuestion> BQQ_List = new ArrayList<BiddingQuizQuestion>();    
	   
	   if (cursor.moveToFirst()) {
		   
		   //Log.d("Debug_Program", "Cursor is not empty!");
		   
		   do {
	    	 byte[] the_blob;
	    	 String set_name = cursor.getString(1);
	    	 int question_num = cursor.getInt(2);
	    	 the_blob = cursor.getBlob(3);
	    	 List<String> westhand = (List<String>)toObject(the_blob);
	    	 the_blob = cursor.getBlob(4);
	    	 List<String> northhand = (List<String>)toObject(the_blob);
	    	 the_blob = cursor.getBlob(5);
	    	 List<String> easthand = (List<String>)toObject(the_blob);
	    	 the_blob = cursor.getBlob(6);
	    	 List<String> southhand = (List<String>)toObject(the_blob);
	    	 String dealer = cursor.getString(7);
	         the_blob = cursor.getBlob(8);
	         List<String> biddinglist = (List<String>)toObject(the_blob);
	         String finalcontract = cursor.getString(9);
	         
	         BiddingQuizQuestion BQQ = new BiddingQuizQuestion(set_name, question_num, westhand, northhand, easthand, southhand, dealer, biddinglist, finalcontract);
	         BQQ_List.add(BQQ);
	      } 
	      while (cursor.moveToNext());
	   }
	   if (cursor != null && !cursor.isClosed()) {
	      cursor.close();
	   }
	   		
	   	Log.d("Debug_Program", "Call to DataHelper.getAllData(): " + String.valueOf(BQQ_List.size()) + " records in database.");
	    
	   	return BQQ_List;	   
	   
   }
    
   
      
   private static class OpenHelper extends SQLiteOpenHelper {

	   OpenHelper(Context context) {
		   super(context, DATABASE_NAME, null, DATABASE_VERSION);
	       Log.d("Debug_Program", "Call to OpenHelper Constructor");
	   }

	   @Override
	   public void onCreate(SQLiteDatabase db) {
		   Log.d("Debug_Program", "Call to OpenHelper.onCreate() [database = " + db.getPath());
		   db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, set_name TEXT, question_num INTEGER, west_hand BLOB, north_hand BLOB, east_hand BLOB, south_hand BLOB, dealer TEXT, bidding_list BLOB, final_contract TEXT)");
	   }

	   @Override
	   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		   Log.d("Debug_Program", "Call to OpenHelper.onUpgrade() [table name = " + TABLE_NAME + "] [database = " + db.getPath());
	       db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	       onCreate(db);
	   }
	   
	   @Override
	   public void onOpen(SQLiteDatabase db) {
		   Log.d("Debug_Program", "Call to OpenHelper.onOpen() [table name = " + TABLE_NAME + "] [database = " + db.getPath());
		   //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		   onCreate(db); 
	      }     
	        
   }
   
   public static byte[] getBytes(Object object) {
	   java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream(5000);
	   
	   try {
		   java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(baos);
		   oos.writeObject(object);
	   }
	   catch(java.io.IOException ioe) {
		   Log.d("Debug_Program", "getBytes() writeObject problem");
	   }
	   
	   return baos.toByteArray();
   }

   public static Object toObject(byte[] bytes) {
   
	   Object object = null;
   
	   try {
		   object = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(bytes)).readObject();
	   }
	   catch(java.io.IOException ioe) {
	   } 
	   catch (ClassNotFoundException e) {
	   }

	   return object;
   }
   
   
}