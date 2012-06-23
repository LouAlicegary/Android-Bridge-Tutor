package com.example.bridgetutor;

import com.example.bridgetutor.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class BiddingQuizQuestion extends Object implements Serializable {
	
	private static final long serialVersionUID = 3861349866188833624L;
	
	// Class Data Members
	private String set_name;
	private int question_num;
	private List<String> westhand, easthand, northhand, southhand;
	private String finalcontract;
	private String dealer;
	private int biddingrounds;
	private List<String> biddinglist;
	private List<List<String>> hand_list = new ArrayList<List<String>>();

	// Class Methods
	
	public String getSetName() {
		return set_name;
	}
	
	public int getQuestionNum() {
		return question_num;
	}
	

	public List<String> getBiddingList() {
		return biddinglist;
	}

	public List<String> getWest() {
		return westhand;
	}
	public List<String> getNorth() {
		return northhand;
	}
	public List<String> getEast() {
		return easthand;
	}
	public List<String> getSouth() {
		return southhand;
	}
	
	public List<List<String>> getListofHands() {
		hand_list.add(westhand); // index=0
		hand_list.add(northhand); // index=1
		hand_list.add(easthand); // index=2
		hand_list.add(southhand); // index=3
		return hand_list;
	}

	public String getDealer() {
		return dealer;
	}
	
	public String getFinalContract() {
		return finalcontract;
	}
	
	public int getBiddingRounds() {
		return biddingrounds;
	}
	
	public BiddingQuizQuestion(String inSetName, int inQuestionNum, List<String> inWestHand, List<String> inEastHand, List<String> inNorthHand, List<String> inSouthHand, String inDealer, List<String> inBiddingList, String inFinalContract) {
		set_name = inSetName;
		question_num = inQuestionNum;
		westhand=inWestHand;
		northhand=inNorthHand;
		easthand=inEastHand;
		southhand=inSouthHand;
		dealer=inDealer; 
		biddinglist=inBiddingList;
		finalcontract=inFinalContract;
	}

};