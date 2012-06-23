/**
 * 
 */
package com.example.bridgetutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.bridgetutor.R;

public class QuestionLister extends ListActivity {

	//private static BiddingQuizQuestion current_BQQ;	
	private static List<BiddingQuizQuestion> BQQ_List_From_DB = new ArrayList<BiddingQuizQuestion>();
	//private static int current_BQQ_Index;
	
	private DataHelper dh;
	BiddingQuizQuestion db_info;
	
	List<String> testValues_list = new ArrayList<String>();
	
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		dh = new DataHelper(this);
        try {
        	BQQ_List_From_DB = dh.getAllData();
		} 
        catch (IOException e) { 
        	Log.d("Debug_Program", "getAllData() error"); 
        }
		
		ListAdapter adapter = createAdapter();
	    setListAdapter(adapter);
	}
	 
    //OLD DATABASE PUT AND RETRIEVE CODE -- NEEDS TO BE INCLUDED IN NEW APP FOR LISTING QUIZ Qs
    //private void DatabaseRetrieval() {
        

		//current_BQQ_Index = BQQ_List_From_DB.size()-1;
		//current_BQQ = BQQ_List_From_DB.get(current_BQQ_Index);



        //List<String> bid_seq_list = current_BQQ.getBiddingList();
        
        //Hand west_hand = current_BQQ.getWest();
        //Hand north_hand = current_BQQ.getNorth();
        //Hand east_hand = current_BQQ.getEast();
        //Hand south_hand = current_BQQ.getSouth();

        /*
        //DATA ENTRY METHOD TO PUT HAND IN DATABASE
        
        BQQ_To_DB = new BiddingQuizQuestion(west_hand, north_hand, east_hand, south_hand, current_BQQ.getDealer(), bid_seq_list, current_BQQ.getFinalContract());
        
        try {
			dh.insert(BQQ_To_DB);
			Log.d("Debug_Program", "Database Insertion of Quiz Question Successful");
		} 
        catch (IOException e1) {
        	Log.d("Debug_Program", "Error with Insertion of Quiz Question");
		}
      
		*/
    //}

	protected ListAdapter createAdapter() {
	    
		//Log.d("Debug_Program", "createAdapter() started. BQQ list size = " + BQQ_List_From_DB.size());
		
		for (int i=0; i < BQQ_List_From_DB.size(); i++) {
			testValues_list.add( BQQ_List_From_DB.get(i).getSetName() + " : " + BQQ_List_From_DB.get(i).getQuestionNum() );
		}

		String[] testValues = testValues_list.toArray(new String[0]);

		//String[] testValues = new String[] {"Test1", "Test2", "Test3"};
		
	    ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item, testValues);
	    
	    return adapter;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	 //super.onListItemClick(l, v, position, id);
	 BiddingQuizQuestion selection = BQQ_List_From_DB.get(position);
	 
	 Intent i = new Intent(this, BridgeTutor.class);
	 i.putExtra("db_info", selection);
	 
	 startActivityForResult(i, position);
	}
	
	// Listen for results.
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		Log.d("Debug_Program", "QuestionLister::onActivityResult()");
		
	     super.onActivityResult(requestCode, resultCode, data);
	     
	     //String extraData = data.getStringExtra("Question_Num");
	     String extraData2 = data.getStringExtra("Status");
	     String extraData3 = String.valueOf(requestCode);
	     
	     Toast.makeText(this, extraData2 + " : " + extraData3,Toast.LENGTH_SHORT).show();

	}
	
}
