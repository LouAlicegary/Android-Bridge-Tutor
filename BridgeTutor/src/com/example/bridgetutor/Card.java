package com.example.bridgetutor;

import com.example.bridgetutor.R;
import java.io.Serializable;

public class Card implements Serializable {
	
	private static final long serialVersionUID = 3454943788307841917L; //used for object serialization
	
	private String rank, suit;
	private int value;
	
	String[] suits={"♠", "♥", "♦", "♣"};
	String[] ranks={"A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2"};
	int[] values={14, 13, 12, 11, 10, 10, 9, 8, 7, 6, 5, 4, 3, 2};

	public String getRank() {
		return rank;
	}

	public String getSuit() {
		return suit;
	}

	public int getValue() {
		return value;
	}
	
	public int getValueFromRank(String inRank) {
		
		int the_value = 0;
		
		for (int i=0; i < 13; i++) {
			if (ranks[i].matches(inRank)) {
				the_value = values[i];
			}
		}
		
		return the_value;
	}
	
	public Card(String inRank, String inSuit, int inValue) {
		rank=inRank; 
		suit=inSuit; 
		value=inValue;
	}
	
	public Card(String inFullString ) {
		rank=inFullString.substring(0,1);
		suit=inFullString.substring(1); 
		value=getValueFromRank(rank);
	}
	
	public Card( ) {
		rank="X"; 
		suit="X"; 
		value=1;
	}
};



