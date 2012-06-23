package com.example.bridgetutor;

import com.example.bridgetutor.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//import android.util.Log;

public class Hand implements Serializable {

	private static final long serialVersionUID = 387944630523801314L; //used for serialization
	
	List<Card> the_hand = new ArrayList<Card>();
	
	List<Card> spades = new ArrayList<Card>();
	List<Card> hearts = new ArrayList<Card>();
	List<Card> diamonds = new ArrayList<Card>();
	List<Card> clubs = new ArrayList<Card>();
	
	
	public List<Card> get_spades() {
		
        for(int i = 0; i < 13; i++) {
        	if (the_hand.get(i).getSuit().matches("♠")) {
        		spades.add(the_hand.get(i));
        	}
        }
		return spades;
	}
	
	public List<Card> get_hearts() {
		
        for(int i = 0; i < 13; i++) {
        	if (the_hand.get(i).getSuit().matches("♥")) {
        		hearts.add(the_hand.get(i));
        	}
        }
		return hearts;
	}
	
	public List<Card> get_diamonds() {
		
        for(int i = 0; i < 13; i++) {
        	if (the_hand.get(i).getSuit().matches("♦")) {
        		diamonds.add(the_hand.get(i));
        	}
        }
		return diamonds;
	}
	
	public List<Card> get_clubs() {
		
        for(int i = 0; i < 13; i++) {
        	if (the_hand.get(i).getSuit().matches("♣")) {
        		clubs.add(the_hand.get(i));
        	}
        }
		return clubs;
	}
	
	public int getsize() {
		return the_hand.size();
	}
	public Card getcard(int cardnum) {
		return the_hand.get(cardnum);
	}
	
	//CONSTRUCTOR FOR HAND BASED ON DECK AND DIRECTION
	public Hand(Deck the_deck, int player_num) {
		
		List<Card> temp_hand = new ArrayList<Card>();
		
		for (int i=0; i < 13; i++) {
			temp_hand.add(the_deck.getcard(i*4 + player_num));
		}
		
		for (int val=14; val > 1; val--) {
			for (int i=0; i < 13; i++) {
				if (temp_hand.get(i).getValue() == val) {
					the_hand.add(temp_hand.get(i));

				}
			}
		}
		
	}
	
	//CONSTRUCTOR FOR HAND BASED ON LIST<STRING>
	public Hand(List<String> the_hand_string_list) {
		
		for (int i=0; i < 13; i++) {
			the_hand.add(new Card(the_hand_string_list.get(i)));
		}

	}
	
	//NULL CONSTRUCTOR FOR HAND: SPITS OUT 13 RANDOM CARDS
	public Hand() {
		
		for (int i=0; i < 13; i++) {
			Deck the_deck = new Deck();
			the_deck.shuffle();
			
			the_hand.add(the_deck.getcard(i));
		}
	}
};
