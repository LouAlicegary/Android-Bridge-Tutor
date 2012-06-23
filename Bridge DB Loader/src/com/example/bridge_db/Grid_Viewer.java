package com.example.bridge_db;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;


public class Grid_Viewer implements ActionListener {
    
////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////CLASS MEMBER VARIABLES////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static String[] player_directions = { "West", "North", "East", "South"};
	public final static String[] ranks = {"A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2"};
	public final static String[] suits = {"♠", "♥", "♦", "♣"};
	public final static String[] bid_ranks = {"1♣", "1♦", "1♥", "1♠", "1NT", "2♣", "2♦", "2♥", "2♠", "2NT", "3♣", "3♦", "3♥", "3♠", "3NT", "4♣", "4♦", "4♥", "4♠", "4NT", "5♣", "5♦", "5♥", "5♠", "5NT", "6♣", "6♦", "6♥", "6♠", "6NT", "7♣", "7♦", "7♥", "7♠", "7NT", "PASS", "DOUBLE", "REDOUBLE"};
	public final static List<String> bid_ranks_list = Arrays.asList(bid_ranks);
	
	public JFrame frame = new JFrame("Bridge Database Updater");
	
	static MyTableModel model;
	static Data_Helper dh;
	
    static JPanel mainPane = new JPanel(); 
    static JPanel firstPane= new JPanel();
    static JPanel secondPane = new JPanel();
    static JPanel thirdPane = new JPanel();
    static JPanel fourthPane = new JPanel();
    static JPanel fifthPane = new JPanel();
    
    static JComboBox dealerList = new JComboBox(player_directions);
    
    static JTabbedPane tabbedPane;
    
    static JTable table = new JTable();
    static Object[][] table_data;
	static String[] table_headers = {"id", "Set Name", "Q#", "West Hand", "North Hand", "East Hand", "South Hand", "Dealer", "Bidding List", "Final Bid" };
    
    static JLabel dealer_label = new JLabel();
    static JLabel bidding_list_label = new JLabel();
    static JLabel declarer_label = new JLabel();
    static JLabel west_hand_list_label = new JLabel();
    static JLabel north_hand_list_label = new JLabel();
    static JLabel east_hand_list_label = new JLabel();
    static JLabel south_hand_list_label = new JLabel();
    
    static JTextField db_tf = new JTextField("BridgeTutor.db",20);
    static JTextField table_tf = new JTextField("QuizQuestions",20);
    static JTextField set_tf = new JTextField("set_name",20);
    static JTextField question_num_tf = new JTextField("1",3);
    
    public static List<String> west_hand = new ArrayList<String>();
    public static List<String> north_hand = new ArrayList<String>();
    public static List<String> east_hand = new ArrayList<String>();
    public static List<String> south_hand = new ArrayList<String>();
    public static List<String> bidding_list = new ArrayList<String>();
    
    static String bid_string = new String();
    static String final_bid = new String();
    
    static int last_good_bid_index=0;
    static int dealer_index = 0;
    
    
    
////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////MAIN METHODS///////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    
    //BASIC CONSTRUCTOR
	public Grid_Viewer () throws ClassNotFoundException, SQLException, IOException {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		dh = new Data_Helper("BridgeTutor.db", "QuizQuestions");
		
		createAndShowGUI();
		
        frame.pack();
        frame.setVisible(true);
	}
	
	//BUILD DISPLAY FOR THE FIRST TIME
    public void createAndShowGUI() throws IOException, SQLException {
        
    	Container contentPane = frame.getContentPane();

        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
        
        getFirstPane();
        getSecondPane();
        getThirdPane();
        getFourthPane();
        getFifthPane();

        
        //BUILD DISPLAY///////////////////////////////////////////////

        mainPane.add(firstPane);
        mainPane.add(Box.createRigidArea(new Dimension(0,20)));
        mainPane.add(secondPane);
        mainPane.add(Box.createRigidArea(new Dimension(0,10)));
        mainPane.add(thirdPane);
        mainPane.add(Box.createRigidArea(new Dimension(0,20)));
        mainPane.add(fourthPane);
        mainPane.add(Box.createRigidArea(new Dimension(0,10)));
        mainPane.add(fifthPane);    
        
        contentPane.add(mainPane);

    }

    //PUT HAND INTO DATABASE
	private void doDatabaseConnection() throws ClassNotFoundException, SQLException, NumberFormatException, IOException {
		
		dh.insert(set_tf.getText(), Integer.valueOf(question_num_tf.getText()), west_hand, north_hand, east_hand, south_hand, dealer_label.getText(), bidding_list, final_bid);
	    
		List<Object> obj = new ArrayList<Object>();
		obj.add(dh.getNumberOfRecords());
		obj.add(set_tf.getText());
		obj.add(Integer.valueOf(question_num_tf.getText()));
		obj.add(west_hand);
		obj.add(north_hand);
		obj.add(east_hand); 
		obj.add(south_hand);
		obj.add(dealer_label.getText()); 
		obj.add(bidding_list);
		obj.add(final_bid);
		

		model.addRow( dh.getNumberOfRecords()-1, (List<Object>)obj );

		question_num_tf.setText( Integer.toString(  Integer.valueOf(question_num_tf.getText()) + 1  ) );
	    
		table.revalidate();
		
		
	    //TO DO
	    
	}
    
    //ACTION LISTENER -- PROCESSES ALL EVENTS OCCURRING IN WINDOW (BUTTONS, CHECKBOXES, COMBO-BOX)
    public void actionPerformed(ActionEvent e) {

    	String event_source_string = new String( e.getSource().getClass().getName() );
	 
    	//ACTION ON HAND CHECKBOX CLICK
    	String jcheckbox_string = new String("javax.swing.JCheckBox");
    	if (jcheckbox_string.contentEquals(event_source_string)) {
		   
		   tabbedPane = ((JTabbedPane) ((JCheckBox)e.getSource()).getParent().getParent());
		   String selected_tab_string = new String(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
	
		   
		   if (selected_tab_string.contentEquals("West")) {
			   if ( !(((JCheckBox)e.getSource()).isSelected()) ) {
				   west_hand.remove(west_hand.indexOf(e.getActionCommand()));
			   }
			   else {
				   west_hand.add(e.getActionCommand());
			   }
			   west_hand_list_label.setText(HandStringMaker(west_hand));
			   west_hand_list_label.revalidate();

		   }
		   if (selected_tab_string.contentEquals("North")) {
			   if ( !(((JCheckBox)e.getSource()).isSelected()) ) {
				   north_hand.remove(north_hand.indexOf(e.getActionCommand()));
			   }
			   else {
				   north_hand.add(e.getActionCommand());
			   }
			   north_hand_list_label.setText(HandStringMaker(north_hand));
			   north_hand_list_label.revalidate();

		   }
		   if (selected_tab_string.contentEquals("East")) {
			   if ( !(((JCheckBox)e.getSource()).isSelected()) ) {
				   east_hand.remove(east_hand.indexOf(e.getActionCommand()));
			   }
			   else {
				   east_hand.add(e.getActionCommand());
			   }
			   east_hand_list_label.setText(HandStringMaker(east_hand));
			   east_hand_list_label.revalidate();

		   }
		   if (selected_tab_string.contentEquals("South")) {
			   if ( !(((JCheckBox)e.getSource()).isSelected()) ) {
				   south_hand.remove(south_hand.indexOf(e.getActionCommand()));
			   }
			   else {
				   south_hand.add(e.getActionCommand());
			   }
			   south_hand_list_label.setText(HandStringMaker(south_hand));
			   south_hand_list_label.revalidate();
		   }
    	}
	   
    	//ACTION ON COMBOBOX
    	String jcombobox_string = new String("javax.swing.JComboBox");
    	if (jcombobox_string.contentEquals(event_source_string)) {
		   dealer_label.setText(player_directions[((JComboBox)e.getSource()).getSelectedIndex()]);
		   dealer_label.revalidate();
		   dealer_index = ((JComboBox)e.getSource()).getSelectedIndex();
		   System.out.println("Dealer Direction Clicked: " + dealer_index );
    	}
	   
    	//ACTION ON *ANY* BUTTON CLICK
    	String jbutton_string = new String("javax.swing.JButton");
    	if ( jbutton_string.contentEquals(event_source_string) ) {
		   
		   //ACTION FROM "SUBMIT" BUTTON ON FOURTH PANE
		   String submit_string = new String("Submit");
		   String cancel_string = new String("Cancel");
		   
		   if ( submit_string.contentEquals(e.getActionCommand()) ) { 
			   doSubmitActions();
		   } 
		   else if ( cancel_string.contentEquals(e.getActionCommand()) ) { 
			   bidding_list.remove(bidding_list.size()-1); 
			   bidding_list_label.setText(BiddingStringMaker());
			   bidding_list_label.revalidate();
		   }
		   	   
		   //ACTION FROM BUTTONS IN BIDDING PANE
		   else {
			   
			   String bid_string = new String(e.getActionCommand());			 
			   int this_bid_index = bid_ranks_list.indexOf(bid_string);
			   
			   if ( bidding_list.size() == 0) { //IF FIRST BID
				   
				   bidding_list.add(bid_string);
				   if (this_bid_index < 35) { // IF FIRST BID IS NORMAL
					   last_good_bid_index = this_bid_index;
				   }
				   else { // IF BID IS PASS/DOUBLE/REDOUBLE
					   last_good_bid_index = 0;
				   } 
				   
			   }
			   
			   else { //IF SECOND OR LATER BID
				   String last_bid = new String(bidding_list.get(bidding_list.size()-1));
				   int last_bid_index = bid_ranks_list.indexOf(last_bid);
				   
				   if (this_bid_index == 35) { //PASS
					   bidding_list.add(bid_string); 
				   }
				   else if (this_bid_index == 36) { // DOUBLE
					   if ( (last_bid_index == 36) || (last_bid_index == 37) ) {
						   JOptionPane.showMessageDialog(frame, "Doubling is not possible immediately after a Double or Redouble.", "User Error", JOptionPane.ERROR_MESSAGE);
					   }
					   else {
						   bidding_list.add(bid_string);
					   }
				   }
				   else if (this_bid_index == 37) { //REDOUBLE
					   
					   if (last_bid_index == 36) {
						   	bidding_list.add(bid_string);
					   }
					   else {
						   JOptionPane.showMessageDialog(frame, "Redoubling can only be done after a double.", "User Error", JOptionPane.ERROR_MESSAGE);
					   }
				   }
				   else if ( (last_good_bid_index < this_bid_index) ) { //ANY GOOD BID
					   bidding_list.add(bid_string);
					   last_good_bid_index = this_bid_index;
				   }
				   else { //ANY BAD BID
					   JOptionPane.showMessageDialog(frame, "This bid must be higher than the previous bid.", "User Error", JOptionPane.ERROR_MESSAGE);
				   } 
			   }
			   
			   bidding_list_label.setText(BiddingStringMaker());
			   bidding_list_label.revalidate();
		   }  
    	}       
   }  

////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////HELPER METHODS/////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //DISPLAYS DATABASE CONFIG PANE (PANE 1)
    private void getFirstPane() {
        //1ST PANE/////////////////////////////////////////////////
        
        JPanel firstPaneSubPane1 = new JPanel();
        JPanel firstPaneSubPane2 = new JPanel();
        JPanel firstPaneSubPane3 = new JPanel();
        JPanel firstPaneSubPane4 = new JPanel();
 
        firstPane.setBorder(new EtchedBorder());
        
        firstPane.setLayout(new GridLayout(2,2));
        firstPaneSubPane1.setLayout(new GridLayout(0,2));
        firstPaneSubPane2.setLayout(new GridLayout(0,2));
        firstPaneSubPane3.setLayout(new GridLayout(0,2));
        firstPaneSubPane4.setLayout(new GridLayout(0,2));
        
        firstPaneSubPane1.add(new JLabel("Database File:"));
        firstPaneSubPane1.add(db_tf);
        
        firstPaneSubPane2.add(new JLabel("Table Name:"));
        firstPaneSubPane2.add(table_tf);
        
        firstPaneSubPane3.add(new JLabel("Set Name:"));
        firstPaneSubPane3.add(set_tf);
        
        firstPaneSubPane4.add(new JLabel("Question Number:"));
        firstPaneSubPane4.add(question_num_tf);
        
        firstPane.add(firstPaneSubPane1);
        firstPane.add(firstPaneSubPane2);
        firstPane.add(firstPaneSubPane3);
        firstPane.add(firstPaneSubPane4);
    }
    
    //DISPLAYS HAND TABS (PANE 2)
    private void getSecondPane() {
        //2ND PANE/////////////////////////////////////////////////
    	
    	tabbedPane = new JTabbedPane();
    	ImageIcon icon = new ImageIcon();
    	
        tabbedPane.addTab(player_directions[0], icon, getSecondPaneHandPane());
        tabbedPane.addTab(player_directions[1], icon, getSecondPaneHandPane());
        tabbedPane.addTab(player_directions[2], icon, getSecondPaneHandPane());
        tabbedPane.addTab(player_directions[3], icon, getSecondPaneHandPane());
    	
        secondPane.add(tabbedPane);
    }
    
    //DISPLAY PANE VIEW INSIDE OF EACH TABBED WINDOW (PANE 2A-D)
    private JPanel getSecondPaneHandPane() {
	   
       JPanel cardsPane = new JPanel();
       cardsPane.setLayout(new GridLayout(0,13));
       
       for (int iSuit=0; iSuit < 4; iSuit++) { 
       	for (int iRank=0; iRank < 13; iRank++) {
       		JCheckBox cb = new JCheckBox(ranks[iRank]+suits[iSuit]);
       		cb.addActionListener(this);
       		cardsPane.add(cb);
       		
       	}
       }
       
       //Add the tabbed pane to this panel.
       return cardsPane;
   }
    
    //DISPLAYS BIDDING BUTTONS (PANE 3)
    private void getThirdPane() {
        
        //3RD PANE///////////////////////////////////////////////////
 
        thirdPane.setBorder(new EtchedBorder());
        thirdPane.setLayout(new BoxLayout(thirdPane, BoxLayout.LINE_AXIS));
        
        JPanel thirdPaneSubPane1 = new JPanel();
        JPanel thirdPaneSubPane2 = new JPanel();
        
        

        //thirdPaneSubPane1.setLayout(new BoxLayout(thirdPaneSubPane1, BoxLayout.Y_AXIS));
        thirdPaneSubPane1.add(new JLabel("Dealer: "));
	    dealerList.setSelectedIndex(0);
	    dealerList.addActionListener(this);
	    thirdPaneSubPane1.add(dealerList);
      
      
        thirdPaneSubPane2.setLayout(new GridLayout(0,5));
        
        for (int i=0; i < bid_ranks.length; i++) {
        	JButton button = new JButton(bid_ranks[i]);
        	thirdPaneSubPane2.add(button);
        	button.addActionListener(this);
        }
        
    	JButton cancel_button = new JButton("Cancel");
    	thirdPaneSubPane2.add(cancel_button);
    	cancel_button.addActionListener(this);
        
        thirdPane.add(thirdPaneSubPane1);
        thirdPane.add(thirdPaneSubPane2);
    }
    
    //DISPLAYS STATUS BOX (PANE 4)
    private void getFourthPane() {
        //4TH PANE/////////////////////////////////////////////////

        JPanel fourthPaneSubPane1 = new JPanel();
        JPanel fourthPaneSubPane1A = new JPanel();
        JPanel fourthPaneSubPane1B = new JPanel();
        JPanel fourthPaneSubPane1C = new JPanel();
        JPanel fourthPaneSubPane1D = new JPanel();
        JPanel fourthPaneSubPane1E = new JPanel();
        JPanel fourthPaneSubPane1F = new JPanel();
        JPanel fourthPaneSubPane1G = new JPanel();
        JPanel fourthPaneSubPane2 = new JPanel();
 
        fourthPane.setBorder(new EtchedBorder());
        fourthPane.setLayout(new BoxLayout(fourthPane, BoxLayout.LINE_AXIS));
        
        fourthPaneSubPane1.setLayout(new GridLayout(7,0));

        bidding_list_label.setText(BiddingStringMaker());
        fourthPaneSubPane1A.add(new JLabel("Dealer: "));
        dealer_label.setText((String) dealerList.getSelectedItem());
        fourthPaneSubPane1A.add(dealer_label);
        fourthPaneSubPane1A.setLayout(new FlowLayout(FlowLayout.LEADING));
        
        bidding_list_label.setText(BiddingStringMaker());
        fourthPaneSubPane1B.add(new JLabel("Bidding Sequence: "));
        fourthPaneSubPane1B.add(bidding_list_label);
        fourthPaneSubPane1B.setLayout(new FlowLayout(FlowLayout.LEADING));
        
        bidding_list_label.setText(BiddingStringMaker());
        fourthPaneSubPane1C.add(new JLabel("Declarer: "));
        fourthPaneSubPane1C.add(declarer_label);
        fourthPaneSubPane1C.setLayout(new FlowLayout(FlowLayout.LEADING));
        
        west_hand_list_label.setText(HandStringMaker(west_hand));
        fourthPaneSubPane1D.add(new JLabel(player_directions[0] + "'s Hand: "));
        fourthPaneSubPane1D.add(west_hand_list_label);
        fourthPaneSubPane1D.setLayout(new FlowLayout(FlowLayout.LEADING));
        
        north_hand_list_label.setText(HandStringMaker(north_hand));
        fourthPaneSubPane1E.add(new JLabel(player_directions[1] + "'s Hand: "));
        fourthPaneSubPane1E.add(north_hand_list_label);
        fourthPaneSubPane1E.setLayout(new FlowLayout(FlowLayout.LEADING));
        
        east_hand_list_label.setText(HandStringMaker(east_hand));
        fourthPaneSubPane1F.add(new JLabel(player_directions[2] + "'s Hand: "));
        fourthPaneSubPane1F.add(east_hand_list_label);
        fourthPaneSubPane1F.setLayout(new FlowLayout(FlowLayout.LEADING));
        
        south_hand_list_label.setText(HandStringMaker(south_hand));
        fourthPaneSubPane1G.add(new JLabel(player_directions[3] + "'s Hand: "));
        fourthPaneSubPane1G.add(south_hand_list_label);
        fourthPaneSubPane1G.setLayout(new FlowLayout(FlowLayout.LEADING));
        
        fourthPaneSubPane1.add(fourthPaneSubPane1A);
        fourthPaneSubPane1.add(fourthPaneSubPane1B);
        fourthPaneSubPane1.add(fourthPaneSubPane1C);
        fourthPaneSubPane1.add(fourthPaneSubPane1D);
        fourthPaneSubPane1.add(fourthPaneSubPane1E);
        fourthPaneSubPane1.add(fourthPaneSubPane1F);
        fourthPaneSubPane1.add(fourthPaneSubPane1G);
        
  
    	JButton submit_button = new JButton("Submit");
    	submit_button.addActionListener(this);
    	fourthPaneSubPane2.add(submit_button);
    	
        
        fourthPaneSubPane2.setLayout(new FlowLayout(FlowLayout.TRAILING));
        
        fourthPane.add(fourthPaneSubPane1);
        fourthPane.add(Box.createHorizontalGlue());
        fourthPane.add(fourthPaneSubPane2);
        fourthPane.add(Box.createRigidArea(new Dimension(20,0)));
    }
    
    //DISPLAYS DATABASE ENTRIES (PANE 5)
    @SuppressWarnings("unchecked")
	private void getFifthPane() throws IOException, SQLException {
    	
    	List<Object> table_data = dh.getAllData();
    	
    	model = new MyTableModel(table_data.size(), table_headers);
    	//model.addTableModelListener(this);
    	
    	
    	for (int i=0; i < table_data.size(); i++) {
    		model.addRow( i, (List<Object>)table_data.get(i) );
    	}
        
    	table = new JTable(model);
             
    	JScrollPane sp = new JScrollPane(table);
    	sp.setPreferredSize(new Dimension(1000,300));
    	fifthPane.setBorder(new EtchedBorder());
    	fifthPane.add(sp);
    	
    }
    
    //BUILD BIDDING STRING FROM LIST OF BIDS
    public String BiddingStringMaker() {
        
    	String final_string = new String();
    	
        for (int i=0; i < bidding_list.size(); i++) {
        	String the_string = new String(bidding_list.get(i));
        	final_string = final_string.concat(the_string);
        	
        	if (i != (bidding_list.size()-1) ) {
        		final_string = final_string.concat(" -> ");
        	}
        	
        }
        
       return final_string;
    }
     
    //BUILD HAND STRINGS FROM LISTS OF HANDS
    public String HandStringMaker(List<String> in_list) {
        
    	String final_string = new String();
    	 	
        for (int i=0; i < in_list.size(); i++) {
        	String the_string = new String(in_list.get(i));
        	final_string = final_string.concat(the_string);
        	
        	if (i != (in_list.size()-1) ) {
        		final_string = final_string.concat("; ");
        	}
        	
        }
        
       return final_string;
    }
    
    //DETERMINES DECLARER BASED ON BID STRING
    public void getDeclarer() {
		   
    	int declare_offset = 0;
    	
    	final_bid = bid_ranks[last_good_bid_index];
		System.out.println("FINAL BID WAS: " + final_bid);
		   
		   
		   if ( final_bid.charAt(1) == '♠' ) {
			   if ( bidding_list.contains("1♠") )
			   {
				   declare_offset = bidding_list.indexOf("1♠");
			   }
			   else if ( bidding_list.contains("2♠") )
			   {
				   declare_offset = bidding_list.indexOf("2♠");
			   }
			   else if ( bidding_list.contains("3♠") )
			   {
				   declare_offset = bidding_list.indexOf("3♠");
			   }
			   else if ( bidding_list.contains("4♠") )
			   {
				   declare_offset = bidding_list.indexOf("4♠");
			   }
			   else if ( bidding_list.contains("5♠") )
			   {
				   declare_offset = bidding_list.indexOf("5♠");
			   }
			   else if ( bidding_list.contains("6♠") )
			   {
				   declare_offset = bidding_list.indexOf("6♠");
			   }
			   else 
			   {
				   declare_offset = bidding_list.indexOf("7♠");
			   }
		   }
		   
		   else if ( final_bid.charAt(1) == '♥' ) {
			   if ( bidding_list.contains("1♥") )
			   {
				   declare_offset = bidding_list.indexOf("1♥");
			   }
			   else if ( bidding_list.contains("2♥") )
			   {
				   declare_offset = bidding_list.indexOf("2♥");
			   }
			   else if ( bidding_list.contains("3♥") )
			   {
				   declare_offset = bidding_list.indexOf("3♥");
			   }
			   else if ( bidding_list.contains("4♥") )
			   {
				   declare_offset = bidding_list.indexOf("4♥");
			   }
			   else if ( bidding_list.contains("5♥") )
			   {
				   declare_offset = bidding_list.indexOf("5♥");
			   }
			   else if ( bidding_list.contains("6♥") )
			   {
				   declare_offset = bidding_list.indexOf("6♥");
			   }
			   else 
			   {
				   declare_offset = bidding_list.indexOf("7♥");
			   }
		   }
		   
		   else if ( final_bid.charAt(1) == '♦' ) {
			   if ( bidding_list.contains("1♦") )
			   {
				   declare_offset = bidding_list.indexOf("1♦");
			   }
			   else if ( bidding_list.contains("2♦") )
			   {
				   declare_offset = bidding_list.indexOf("2♦");
			   }
			   else if ( bidding_list.contains("3♦") )
			   {
				   declare_offset = bidding_list.indexOf("3♦");
			   }
			   else if ( bidding_list.contains("4♦") )
			   {
				   declare_offset = bidding_list.indexOf("4♦");
			   }
			   else if ( bidding_list.contains("5♦") )
			   {
				   declare_offset = bidding_list.indexOf("5♦");
			   }
			   else if ( bidding_list.contains("6♦") )
			   {
				   declare_offset = bidding_list.indexOf("6♦");
			   }
			   else 
			   {
				   declare_offset = bidding_list.indexOf("7♦");
			   }
		   }
		   
		   else if ( final_bid.charAt(1) == '♣' ) {
			   if ( bidding_list.contains("1♣") )
			   {
				   declare_offset = bidding_list.indexOf("1♣");
			   }
			   else if ( bidding_list.contains("2♣") )
			   {
				   declare_offset = bidding_list.indexOf("2♣");;
			   }
			   else if ( bidding_list.contains("3♣") )
			   {
				   declare_offset = bidding_list.indexOf("3♣");
			   }
			   else if ( bidding_list.contains("4♣") )
			   {
				   declare_offset = bidding_list.indexOf("4♣");
			   }
			   else if ( bidding_list.contains("5♣") )
			   {
				   declare_offset = bidding_list.indexOf("5♣");
			   }
			   else if ( bidding_list.contains("6♣") )
			   {
				   declare_offset = bidding_list.indexOf("6♣");
			   }
			   else 
			   {
				   declare_offset = bidding_list.indexOf("7♣");
			   }
		   }
		   
		   else {
			   if ( bidding_list.contains("1NT") )
			   {
				   declare_offset = bidding_list.indexOf("1NT");
			   }
			   else if ( bidding_list.contains("2NT") )
			   {
				   declare_offset = bidding_list.indexOf("2NT");;
			   }
			   else if ( bidding_list.contains("3NT") )
			   {
				   declare_offset = bidding_list.indexOf("3NT");
			   }
			   else if ( bidding_list.contains("4NT") )
			   {
				   declare_offset = bidding_list.indexOf("4NT");
			   }
			   else if ( bidding_list.contains("5NT") )
			   {
				   declare_offset = bidding_list.indexOf("5NT");
			   }
			   else if ( bidding_list.contains("6NT") )
			   {
				   declare_offset = bidding_list.indexOf("6NT");
			   }
			   else 
			   {
				   declare_offset = bidding_list.indexOf("7NT");
			   }
		   }
		   
		   System.out.println( "Declarer = " + player_directions[(dealer_index + declare_offset) % 4] );
		   declarer_label.setText(player_directions[(dealer_index + declare_offset) % 4]);
		   declarer_label.revalidate();
    }

    //RUN WHEN SUBMIT BUTTON IS CLICKED
    public void doSubmitActions() {
    	//int double_flag = 0;
    	//int redouble_flag = 0;
    	int halt_db_update_flag = 0;
    	
		//VALIDATE BIDDING LIST (ends in pass->pass->pass and larger than 4 bids)
    	if (bidding_list.size() > 3) {
    		if ( (bidding_list.get(bidding_list.size()-1).equals("PASS")) && (bidding_list.get(bidding_list.size()-2).equals("PASS")) && (bidding_list.get(bidding_list.size()-3).equals("PASS")) && !(bidding_list.get(bidding_list.size()-4).equals("PASS") )) {   
    			
    			if ( bidding_list.get(bidding_list.size()-4).equals("DOUBLE") ) {
    				//double_flag = 1;
    			}
    			if ( bidding_list.get(bidding_list.size()-4).equals("REDOUBLE") ) {
    				//redouble_flag = 1;
    			}
    			   
    			//VALIDATE HANDS LISTS (13 or 0 cards)
    			if ( (west_hand.size() == 0 || west_hand.size() == 13) &&  (north_hand.size() == 0 || north_hand.size() == 13) && (east_hand.size() == 0 || east_hand.size() == 13) && (south_hand.size() == 0 || south_hand.size() == 13) ) {
    				getDeclarer();    			
    			}
    			else {
    				 System.out.println("HAND LIST SIZES ARE INVALID: (" + west_hand.size() + ", " + north_hand.size() + ", " + east_hand.size() + ", " + south_hand.size() + ")");
    				 halt_db_update_flag=1;
    			}
    			
    		}
    		
    		else {
    			System.out.println("INVALID BIDDING SEQUENCE");
    			halt_db_update_flag=1;
    		}
    		
    		if (halt_db_update_flag != 1) {
    			try {
    				doDatabaseConnection();
    				runCleanup();
    			} catch (Exception e) {
    				System.out.println("DATABASE PROBLEM");
    				e.printStackTrace();
    			}
    		}
    	}
    	else {
    		System.out.println("NOT ENOUGH BIDS");
    		halt_db_update_flag=1;
    	}

		halt_db_update_flag=0;
    }
    
    private void runCleanup() {
    	
    	System.out.println(((JPanel)tabbedPane.getComponentAt(0)).getComponentCount());
    	for (int j=0; j < tabbedPane.getComponentCount(); j++) {
    		for (int i=0; i < ((JPanel)tabbedPane.getComponentAt(j)).getComponentCount(); i++) {
    			((JCheckBox)((JPanel)tabbedPane.getComponentAt(j)).getComponent(i)).setSelected(false);
    		}
    	}
        dealer_label.setText(player_directions[dealerList.getSelectedIndex()]);
        bidding_list_label.setText(""); 
        declarer_label.setText(""); 
        west_hand_list_label.setText(""); 
        north_hand_list_label.setText(""); 
        east_hand_list_label.setText(""); 
        south_hand_list_label.setText(""); 
        
        bidding_list = new ArrayList<String>();
        west_hand = new ArrayList<String>();
        north_hand = new ArrayList<String>();
        east_hand = new ArrayList<String>();
        south_hand = new ArrayList<String>();
    	last_good_bid_index=0;
    	
    	tabbedPane.setSelectedIndex(0);
        
        
    }
}