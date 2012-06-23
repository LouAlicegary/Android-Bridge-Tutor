package com.example.bridgetutor;

import com.example.bridgetutor.R;
import java.io.Serializable;

public class Bid implements Serializable {
	
	private static final long serialVersionUID = 9083754778051295441L; // Used for object serialization
	
	
	//String[] bid_ranks ={"1♣", "1♦", "1♥", "1♠", "1NT", "2♣", "2♦", "2♥", "2♠", "2NT", "3♣", "3♦", "3♥", "3♠", "3NT", "4♣", "4♦", "4♥", "4♠", "4NT", "5♣", "5♦", "5♥", "5♠", "5NT", "6♣", "6♦", "6♥", "6♠", "6NT", "7♣", "7♦", "7♥", "7♠", "7NT", "PASS"};
	//List<String> bid_ranks_list = Arrays.asList(bid_ranks);
	
	private char suit, bidder;
	private int value;

	public char getSuit() {
		return suit;
	}

	public int getValue() {
		return value;
	}
	
	public char getBidder() {
		return bidder;
	}

	public Bid(char inSuit, int inValue, char inBidder) { 
		suit=inSuit; 
		value=inValue;
		bidder=inBidder;
	}
	
};