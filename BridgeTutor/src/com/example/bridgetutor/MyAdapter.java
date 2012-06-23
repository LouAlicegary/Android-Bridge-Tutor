package com.example.bridgetutor;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.bridgetutor.R;

public class MyAdapter extends BaseAdapter {
	 
	private LayoutInflater mInflater;
	public int current_bid_round;
	
	int total_bidding_rounds = 2;
	Deck game_deck = new Deck();
    Hand west_hand = new Hand(game_deck,0);
    Hand north_hand = new Hand(game_deck,1);
    Hand east_hand = new Hand(game_deck,2);
    Hand south_hand = new Hand(game_deck,3); 
    
    BiddingQuizQuestion current_BQQ;
    
	public MyAdapter(Context context, BiddingQuizQuestion in_current_BQQ) {
		Log.d("Debug_Program", "Call to MyAdapter Constructor");
		
		mInflater = LayoutInflater.from(context);
		
		current_BQQ = in_current_BQQ;
		 
	}
	 
	public int getCount() {
		 
		 if (total_bidding_rounds != current_bid_round) {
			 //Log.d("Debug_Program", "MyAdapter.getCount() called; returns " + (current_bid_round+1));
			 return current_bid_round+1;
		 }
		 else {
			 //Log.d("Debug_Program", "MyAdapter.getCount() called; returns " + current_bid_round);
			 return current_bid_round;
		 }
	}

	public Object getItem(int position) {
		Log.d("Debug_Program", "MyAdapter.getItem() called; returns " + String.valueOf(position));
		return position;
	}

	public long getItemId(int position) {
		//Log.d("Debug_Program", "MyAdapter.getItemId() called; returns " + String.valueOf(position));
		return position;
	}
	 
	public View getView(int position, View convertView, ViewGroup parent) {
		 
		//Log.d("Debug_Program", "MyAdapter.getView() called; position " + String.valueOf(position));
		ViewHolder holder;
		
		if (convertView == null) {
			 convertView = mInflater.inflate(R.layout.grid_item, null);
			 holder = new ViewHolder();
			 holder.text = (TextView) convertView.findViewById(R.id.west_col);
			 holder.text2 = (TextView) convertView.findViewById(R.id.north_col);
			 holder.text3 = (TextView) convertView.findViewById(R.id.east_col);
			 holder.text4 = (TextView) convertView.findViewById(R.id.south_col);				 
	
			 convertView.setTag(holder);
		 } 
		 else {
			 holder = (ViewHolder) convertView.getTag();
		 }
		 
		 //ListView lv = (ListView)parent;
		 
		 if (position == current_bid_round) {
				holder.text.setText("Click");
				holder.text2.setText("Here");
				holder.text3.setText("To");
				holder.text4.setText("Bid");
				convertView.setBackgroundColor(Color.YELLOW);
		 }
			
		 else {
			 if (current_BQQ.getBiddingList().size() == position*4+1) {
				 holder.text.setText(current_BQQ.getBiddingList().get(position*4));
				 holder.text2.setText("");
				 holder.text3.setText("");
				 holder.text4.setText("");			 
			 }
			 else if (current_BQQ.getBiddingList().size() == position*4+2) {
				 holder.text.setText(current_BQQ.getBiddingList().get(position*4));
				 holder.text2.setText(current_BQQ.getBiddingList().get(position*4+1));
				 holder.text3.setText("");
				 holder.text4.setText("");
			 }
			 else if (current_BQQ.getBiddingList().size() == position*4+3) {
				 holder.text.setText(current_BQQ.getBiddingList().get(position*4));
				 holder.text2.setText(current_BQQ.getBiddingList().get(position*4+1));
				 holder.text3.setText(current_BQQ.getBiddingList().get(position*4+2));
				 holder.text4.setText("");
			 }
			 else {
				 holder.text.setText(current_BQQ.getBiddingList().get(position*4));
				 holder.text2.setText(current_BQQ.getBiddingList().get(position*4+1));
				 holder.text3.setText(current_BQQ.getBiddingList().get(position*4+2));
				 holder.text4.setText(current_BQQ.getBiddingList().get(position*4+3));
			 }
			 	
			 	convertView.setBackgroundColor(Color.TRANSPARENT);
			 	convertView.setClickable(false);
		 }

		 return convertView;
	 }

	static class ViewHolder {
		 TextView text;
		 TextView text2;
		 TextView text3;
		 TextView text4;
	 }
}