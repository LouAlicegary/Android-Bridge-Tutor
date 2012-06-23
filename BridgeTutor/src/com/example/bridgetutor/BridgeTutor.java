package com.example.bridgetutor;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
//import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SlidingDrawer;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.example.bridgetutor.R;

public class BridgeTutor extends Activity {
	
	//private static int total_bidding_rounds = 2;
	private static int current_bid_round = 0;

	BiddingQuizQuestion db_info;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	Log.d("Debug_Program", "======================================================================");
    	Log.d("Debug_Program", "======================================================================");
    	Log.d("Debug_Program", "onCreate() method started");
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        if ( getIntent().getExtras() != null ) {
        	Log.d("Debug_Program", "Extras included in getIntent().getExtras()");
        	Bundle extras = getIntent().getExtras();
        	db_info = (BiddingQuizQuestion) extras.getSerializable("db_info");
        	StartQuiz();
        }
        else {
        	Log.d("Debug_Program", "No extras in getIntent().getExtras()");
        	finish();
        }
        
        Log.d("Debug_Program", "onCreate() method finished");

    }
    
    private void StartQuiz() {
    	
    	Log.d("Debug_Program", "StartQuiz() method started");

    	UpdateDealDisplay();
        UpdateBiddingDisplay();
               
        // Kill the App if "Go Back" button is pushed
        //final Button button = (Button) findViewById(R.id.back_button);
        /*
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        */
        
        Log.d("Debug_Program", "StartQuiz() method finished");
            
    }
    
    /*
    private void DealCards(Deck game_deck) {
    	
        // Shuffles deck and deals out four hands
    	Log.d("Debug_Program", "DealCards() method started");
    	
        game_deck.shuffle();
        
        Log.d("Debug_Program", "DealCards() method finished");
        
    }
    */
    
    private void UpdateDealDisplay() {
        
    	List<String> west_hand_string_list = db_info.getWest();
    	Hand west_hand = new Hand(west_hand_string_list);
        
        // pre_ are Lists of Cards; no prefix ones are Lists of Strings
        List<String> spades = new ArrayList<String>();
        List<String> hearts = new ArrayList<String>();       
        List<String> diamonds = new ArrayList<String>();
        List<String> clubs = new ArrayList<String>();
        
        List<Card> pre_spades = west_hand.get_spades();
        List<Card> pre_hearts = west_hand.get_hearts();
        List<Card> pre_diamonds = west_hand.get_diamonds();
        List<Card> pre_clubs = west_hand.get_clubs();
         
        // This part converts Cards to Strings
        for (int i=0; i < pre_spades.size(); i++) {
        	spades.add(pre_spades.get(i).getRank() + pre_spades.get(i).getSuit());
        }
        for (int i=0; i < pre_hearts.size(); i++) {
        	hearts.add(pre_hearts.get(i).getRank() + pre_hearts.get(i).getSuit());
        }
        for (int i=0; i < pre_diamonds.size(); i++) {
        	diamonds.add(pre_diamonds.get(i).getRank() + pre_diamonds.get(i).getSuit());
        }
        for (int i=0; i < pre_clubs.size(); i++) {
        	clubs.add(pre_clubs.get(i).getRank() + pre_clubs.get(i).getSuit());
        }
        
        List<List<String>> SuitsList = new ArrayList<List<String>>();
        SuitsList.add(spades);
        SuitsList.add(hearts);
        SuitsList.add(diamonds);
        SuitsList.add(clubs);
     
        ListView lv1= (ListView)findViewById(R.id.listview1);
        ListView lv2= (ListView)findViewById(R.id.listview2);
        ListView lv3= (ListView)findViewById(R.id.listview3);
        ListView lv4= (ListView)findViewById(R.id.listview4);

        ListAdapter adapter1 = new ArrayAdapter<String>(this, R.layout.list_item, SuitsList.get(0));
        ListAdapter adapter2 = new ArrayAdapter<String>(this, R.layout.list_item, SuitsList.get(1));
        ListAdapter adapter3 = new ArrayAdapter<String>(this, R.layout.list_item, SuitsList.get(2)); 
        ListAdapter adapter4 = new ArrayAdapter<String>(this, R.layout.list_item, SuitsList.get(3));
        
        lv1.setAdapter(adapter1);
        lv2.setAdapter(adapter2);
        lv3.setAdapter(adapter3);
        lv4.setAdapter(adapter4);
    }
    
    //DISPLAYS BIDDING PANEL
    private void UpdateBiddingDisplay() {

    	Log.d("Debug_Program", "UpdateBiddingDisplay() started");
    	
    	ListView lv5 = (ListView)findViewById(R.id.listview5);
    	
    	MyAdapter my_ad = new MyAdapter(this, db_info);
    	my_ad.current_bid_round = current_bid_round;
    	lv5.setAdapter(my_ad);
    	
    	//CLICK HANDLER FOR BIDDING LISTVIEW
	    OnItemClickListener mListViewClickedHandler = new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
            	Log.d("Debug_Program", "Bidding Listview Click Listener Fired");
                myClickHandler(v);
            }
        };
        lv5.setOnItemClickListener(mListViewClickedHandler);
    
        Log.d("Debug_Program", "UpdateBiddingDisplay() finished");
        
    }
    
    //FUNCTION FOR WHEN "CLICK HERE TO BID" IS CLICKED
    public void myClickHandler(View v) {
	    
    	Log.d("Debug_Program", "myClickHandler() called");
    	
	    LayoutInflater inflater = getLayoutInflater();
	    View drawer_view = inflater.inflate(R.layout.drawer, null);
	    
	    LinearLayout old_main = (LinearLayout)findViewById(R.id.main);
	    
	    old_main.addView(drawer_view);
	    
	    this.setContentView(old_main);
	    
	    SlidingDrawer sd = (SlidingDrawer)findViewById(R.id.slide);
	    sd.animateToggle();
	    
	    Log.d("Debug_Program", "myClickHandler() finished");
    
    }
    
    //CLICK HANDLER FOR BIDDING INPUT IN SLIDING PANEL
    public void myClickHandler2(View v) {

    	Log.d("Debug_Program", "myClickHandler2() called");
    	
	    Button btnChild = (Button)v;
	   
	    String my_bid = new String("");
	    
	    Log.d("Debug_Program", btnChild.getText().toString());
	    
	    if (btnChild.getId() == R.id.button6) {
	    	my_bid = "PASS";
	    }
	    else {
	    	LinearLayout ll = (LinearLayout)v.getParent();
	    	LinearLayout ll2 = (LinearLayout)ll.getParent();
	    	RadioGroup rg = (RadioGroup)ll2.getChildAt(0);
	    	RadioGroup rg2 = (RadioGroup)ll2.getChildAt(1);
	    	//Toast.makeText(this, String.valueOf(ll2.getChildCount()),Toast.LENGTH_SHORT).show();
	    	RadioButton rb = (RadioButton)findViewById(rg.getCheckedRadioButtonId());
	    	RadioButton rb2 = (RadioButton)findViewById(rg2.getCheckedRadioButtonId());
	    	
	    	my_bid = (String)rb.getText() + (String)rb2.getText();
	    }
	    
	    String correct_bid = (String)db_info.getBiddingList().get(current_bid_round*4);
	    
	    if ( my_bid.equals(correct_bid) ) {
	    	
	    	Toast.makeText(this, "Correct! Nice job!",Toast.LENGTH_SHORT).show();
	    	current_bid_round = current_bid_round + 1;
		    
	    	SlidingDrawer sd = (SlidingDrawer)findViewById(R.id.slide);
		    sd.animateToggle();
		    
		    int total_bidding_rounds = (db_info.getBiddingList().size()+3)/4;
		    
		    if (current_bid_round < total_bidding_rounds ) {
		    	Log.d("Debug_Program", "Bidding Still In Progress");
		    	UpdateBiddingDisplay();    	
		    }
		    else {
		    	Log.d("Debug_Program", "Bidding Finished");
		    	UpdateBiddingDisplay();
		    	Toast.makeText(this, "YOU BID THIS LIKE A PRO! CONGRATS!",Toast.LENGTH_SHORT).show();
		    	
		    	Intent intent=new Intent(this, QuestionLister.class);
		    	//intent.putExtra("Question_Num", String.valueOf(db_info.getQuestionNum()));
		    	intent.putExtra("Status", "Y");
		    	setResult(RESULT_OK, intent);
		    	
		    	//final int result=1;
		    	//startActivityForResult(intent, result);
		    	
		    	finish();
		    }
	    }
	    else {
	    	Toast.makeText(this, "Incorrect. Try again.",Toast.LENGTH_SHORT).show();
	    }
	    
	    Log.d("Debug_Program", "myClickHandler2() finished");
    }  
   
   public void myClickHandler3(View v) {
	    	Intent intent=new Intent(this, QuestionLister.class);
	    	//intent.putExtra("Question_Num", String.valueOf(db_info.getQuestionNum()));
	    	intent.putExtra("Status", new String("N"));
	    	setResult(RESULT_OK, intent);
	    	finish();
   }
    
}