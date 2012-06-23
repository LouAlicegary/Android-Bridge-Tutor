package com.example.bridgetutor;

import com.example.bridgetutor.R;
import java.io.Serializable;
import java.util.Random;

public class Deck implements Serializable {
	
	private static final long serialVersionUID = 7695716049998077055L;  //used for object serialization
	
	private Card[] deckofcards;

	public void shuffle() {
		for(int i=0; i<52; i++) {
			Random gen=new Random();
			Card temp;
			int number=gen.nextInt(52);
			temp=deckofcards[i];
			deckofcards[i]=deckofcards[number];
			deckofcards[number]=temp;
		}
	}

	public Card getcard(int i) {
		return deckofcards[i];
	}
	
	public Deck() {
		deckofcards=new Card[52];
		String[] suits={"♠", "♥", "♦", "♣"};
		String[] ranks={"A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2"};
		int[] values={14, 13, 12, 11, 10, 10, 9, 8, 7, 6, 5, 4, 3, 2};

		for(int i=0; i<52; i++) {
			deckofcards[i]=new Card(ranks[i%13], suits[i%4], values[i%13]);
		}
	}

};