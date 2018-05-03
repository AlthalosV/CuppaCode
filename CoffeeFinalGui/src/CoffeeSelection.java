/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;        // Using AWT container and component classes
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JOptionPane;
import java.awt.Container;
import java.text.DecimalFormat;
import java.awt.GridLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CoffeeSelection extends JFrame implements ActionListener {

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();

    static int finalChoice = 0; //boolean to stop do while loop at the bottom
     

    JFrame dialogBoxes = new JFrame(); //creating a frame for the dialog boxes (tochange the icon)

    static final String HYPEN = "- "; //string to use for receipt
    static final String NEWLINE = "\n"; //creating a new line for the receipt

    //had to make these global because they are used in multiple methods 
    boolean whatIsMyMatch; 
    boolean isThisAReturningCustomer;
    
    
    //coffee cup prices 
    final double priceForEightOzCup = 1.25;
    final double stateTax = 0.04;
    final double countyTax = 0.02;
    int numberOfCoffeeCups = 0; //counts the number of coffee cups for the receipt 
    int numberOfCoffeeCups2 = 0; //counts the number of coffee cups total (max is 32)
    final int eightOzOfCoffee = 8;//only 8 oz allowed in a cup of coffee
    
    
    
    final int FRAME_WIDTH = 700; //width
    final int FRAME_HEIGHT = 500; //height

    //price for everything
    double price = 0.00;
    boolean discountMatch = false;

    //global variables for names
    String newCustomerNameWithReturningValidation;
    String newCustomerPhoneNumberWithReturningValidation;
    String nameOfTheReturningCustomer = " ";

    //layout of the box 
    private Container totalBox = getContentPane(); //container for background color

    //creating panels
    JPanel northPanel = new JPanel();
    JPanel southPanel = new JPanel();
    JPanel westPanel = new JPanel();
    JPanel eastPanel = new JPanel();
    JPanel centerPanel = new JPanel();

    //fonts 
    Font bigFont = new Font("Century", Font.BOLD, 27);
    Font littleFont = new Font("Century", Font.PLAIN, 12);
    Font middleFont = new Font("Century", Font.ITALIC, 17);
    Font labelFont = new Font("Century", Font.BOLD, 12);

    //COLOR CREATION!!
    Color LIGHTPURPLE = new Color(255,250,250);
    Color ACCENT_COLOR = new Color(255, 128, 192); //kinda just 

    //sweeteners array 
    static String coffeeSweeteners[] = {"   ", "Cane Sugar", "Raw Sugar", "Stevia", "Sweet & Low", "Brown Sugar", "Splenda", "Honey"}; //array for sweeteners

    //coffee creamer array 
    static String coffeeCreamers[] = {"  ", "French Vanilla", "Hazelnut", "Irish Cream", "Peppermint Mocha",
        "Almond Milk", "Soy Milk", "Coconut Milk", "Half and Half", "Chocolate Caramel", "Amaretto",
        "Toffee Nut", "Chocolate Raspberry", "Vanilla Chai Spice", "Blueberry Cobbler"}; //array for coffee creamers

    //new & returning customer and text box 
    JButton newCustomerButton = new JButton("New Customer"); //creating new customer button
    JButton returningCustomerButton = new JButton("Returning Customer"); //creating returning customer button

    
    JButton enterButton = new JButton("Enter"); //restarts the panel
    JButton finishButton = new JButton("Pay For Order"); //restarts the entire frame 
    JButton questionsAboutCoffeeButton = new JButton("What roast should I get?"); //pulls up a dialog box with info about roasts
    
//Coffee combo box
    static String coffeeFlavors[] = {"Light Roast", "Medium Roast", "Medium Dark Roast", "Dark Roast"};
    //  JComboBox coffeeFlavorsComboBox = new JComboBox(coffeeFlavors); //creating a combo box with coffee flavors

    //receipt text box
    JTextArea receiptTextBox = new JTextArea(16,58); //creating a text field for the receipts

    StringBuilder builder = new StringBuilder(); //builder is the compilation of the strings for the receipt 

   
    //combo boxes 
    JComboBox coffeeCreamersComboBox = new JComboBox(coffeeCreamers);
    JComboBox coffeeSweetenersComboBox = new JComboBox(coffeeSweeteners);
    JComboBox coffeeFlavorsComboBox = new JComboBox(coffeeFlavors);

    
    
    CoffeeSelection() {

        super("Cuppa Code"); //title

        setSize(FRAME_WIDTH, FRAME_HEIGHT); //setting the size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //lets the frame exit 

        totalBox.setLayout(new BorderLayout()); //creates the layout of the entire frame 
        totalBox.setBackground(LIGHTPURPLE); //sets the color to the purple created before 

        //north panel (which is just the panel for the title)
        add(northPanel, BorderLayout.NORTH); //borderlayout
        northPanel.setBackground(LIGHTPURPLE); //sets this to the purple created before 
        JLabel greeting = new JLabel("Welcome to the Cuppa Code Kiosk"); //label at the  top 
        greeting.setFont(bigFont); //font for headline
        greeting.setForeground(ACCENT_COLOR); //sets this to the pink created before 
        northPanel.add(greeting); //adds the label to the panel 

        //west panel with combo boxes 
        add(westPanel, BorderLayout.WEST); //sets the west panel to the west part of the totalBox layout 
        westPanel.setBackground(LIGHTPURPLE); //west panel is now purple 
        GridLayout wstLayout = new GridLayout(6, 1, 10, 40); //6 rows 1 column, 10 hgap, 40 v gap
        westPanel.setLayout(wstLayout); //sets the west panel to the grid layout 
        
        //roast information 
        JLabel roastLabel = new JLabel("ROAST TYPE                                                                    "); //label for coffee roasts 
        roastLabel.setFont(labelFont); //setting the text to little font text 
        roastLabel.setForeground(ACCENT_COLOR); //setting the label to the accent color 
        westPanel.add(roastLabel); //adds the label to the panel 
        coffeeFlavorsComboBox.setSelectedIndex(1); //set the default choice of the combo box to 1 (which is medium roast)
        coffeeFlavorsComboBox.setFont(littleFont); //set the font of the combo box (to the little font which was created earlier)
        coffeeFlavorsComboBox.setBackground(ACCENT_COLOR); //sets the combo box the the accent color 
        westPanel.add(coffeeFlavorsComboBox); //adds the combo box to the panel 

        
        //creamer information
        JLabel creamerLabel = new JLabel("CREAMER TYPE "); //creates the label for the creamers
        creamerLabel.setFont(labelFont); //sets the font of the creamers to the label font (which was created earlier)
        creamerLabel.setForeground(ACCENT_COLOR); //sets the label to the pink color 
        westPanel.add(creamerLabel); //adds the label to the panel
        coffeeCreamersComboBox.setFont(littleFont); //sets the combo box to the little font 
        coffeeCreamersComboBox.setBackground(ACCENT_COLOR); //sets the combo box to pink
        westPanel.add(coffeeCreamersComboBox); //adds the combo box to the panel 
        

        //sweetener information
        JLabel sweetenerLabel = new JLabel("SWEETENER TYPE "); //creates the label
        sweetenerLabel.setFont(labelFont); //sets the label to the medium sized font
        sweetenerLabel.setForeground(ACCENT_COLOR); //sets the label to pink
        westPanel.add(sweetenerLabel); //adds the label to the panel
        coffeeSweetenersComboBox.setFont(littleFont); //sets the combo box to the small font
        coffeeSweetenersComboBox.setBackground(ACCENT_COLOR); //sets the combo box to the pink
        westPanel.add(coffeeSweetenersComboBox); //adds teh combo box to the west panel

        //sets the west panel to false as a default (not visible)
        westPanel.setVisible(false);

        //adding the center panel
        
       
      
        add(centerPanel, BorderLayout.CENTER);
        GridLayout cstLayout = new GridLayout(1,1); //cets the center panel to a flow layout 
        
        centerPanel.setLayout(cstLayout); //center panel is set with the flow layout 
        receiptTextBox.setEditable(false); //does not allow the receipt to be edited 
        JScrollPane scroll = new JScrollPane(receiptTextBox); //creates a scroll bar with the receipt text box 
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED); //allows for a horizontal scroll bar if needed 
        centerPanel.add(scroll); //adds the scroll par to the center pane
    
        //adding east panel which is for the new customer buttons 
        add(eastPanel, BorderLayout.EAST);
        GridLayout estLayout = new GridLayout(2, 2, 150, 150); //ets the east panel to a grid layout 
        eastPanel.setLayout(estLayout); //applies the grid layout to the east panel 
        eastPanel.setBackground(LIGHTPURPLE); //sets the color to the purple

        //the quesion label has been left blank for better button placement 
        JLabel question = new JLabel("                                                      "); //label for "enter screen" 
        question.setFont(middleFont); //sets the label to the middle font 
        question.setForeground(ACCENT_COLOR); //applies the pink color to the font
        question.setBackground(ACCENT_COLOR); //apples 
     //   question.setAlignmentX(CENTER_ALIGNMENT);//sets this at center alignment IF THIS WOULD WORK!!!!
        eastPanel.add(question); //adds the label to the panel 
    //    question.setHorizontalAlignment(JLabel.CENTER); //IF THIS WOULD WORK THE SPACE FILLER IS IN THE WAY 
     
        JPanel spaceFiller = new JPanel(); //this is just a space filler so that the bottom buttons line up 
        eastPanel.add(spaceFiller);//adds the space filler to the panel 
        spaceFiller.setBackground(LIGHTPURPLE); //sets the color of the space filler 
        
        eastPanel.add(spaceFiller);
        newCustomerButton.setFont(littleFont); //sets the font of the new customer button 
        eastPanel.add(newCustomerButton); //adds the new customer button to the panel 
        returningCustomerButton.setFont(littleFont); //sets the font of the returning customer button 
        eastPanel.add(returningCustomerButton); //returning customer button

      
    //   eastPanel.add(new JPanel());
        newCustomerButton.setBackground(ACCENT_COLOR);//sets the color of the new customer button to pink 
        returningCustomerButton.setBackground(ACCENT_COLOR); //sets the color of the returning customer button to pink
        questionsAboutCoffeeButton.setBackground(ACCENT_COLOR);//sets the color of the roast info button to pink 
        enterButton.setBackground(ACCENT_COLOR); //sets the color of the enter button to pink 
        finishButton.setBackground(ACCENT_COLOR);//sets the color of the finish button to pink 

        //add southPanel
        add(southPanel, BorderLayout.SOUTH); //adds the south panel to the border layout
        FlowLayout sthLayout = new FlowLayout(FlowLayout.CENTER, 20, 20); 
        southPanel.setLayout(sthLayout); //sets the flow layout to the south panel
        southPanel.setBackground(LIGHTPURPLE); //sets the background to the purple color 
        enterButton.setFont(littleFont); //sets the font of the enter button to the small font 
        southPanel.add(enterButton); //adds the enter button to the south panel
        southPanel.add(questionsAboutCoffeeButton); //adds the question button to the south panel
        finishButton.setFont(littleFont); //sets the finish button to the south panel 
        southPanel.add(finishButton); //adds the finish button to the south panel 
        questionsAboutCoffeeButton.setFont(littleFont); //sets the question button to have the small font 
        enterButton.setVisible(false);//sets the enter button to invisible  
        finishButton.setVisible(false); //sets the finish button to invisible  
        questionsAboutCoffeeButton.setVisible(false); //sets the quesion button to invisible 

        setInvisibility(centerPanel); //sets the center panel to invisible by calling a method 
        
        receiptTextBox.setLineWrap(true);
        receiptTextBox.setWrapStyleWord(false);
        receiptTextBox.setFont(littleFont);
        receiptTextBox.setText("Welcome To Cuppa Code"); //total 
        
 

        //adding action listeners to each of the buttons 
        newCustomerButton.addActionListener(this);
        returningCustomerButton.addActionListener(this);
        enterButton.addActionListener(this);
        finishButton.addActionListener(this);
        questionsAboutCoffeeButton.addActionListener(this);

        //adding action listeners to the combo box 
        coffeeFlavorsComboBox.addActionListener(this);
        coffeeCreamersComboBox.addActionListener(this);
        coffeeSweetenersComboBox.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
        Object source = e.getSource();
        double coffeePriceForReceipt = 0.0;
        double coffeeSalesTaxForReceipt = 0.0;
        double coffeeTotal = 0.0;
        double discountPrice = 0.0;
        DecimalFormat df = new DecimalFormat("#.##");

        //more strings
        String nameOfNewCustomer;

        //when button one is pressed
        if (source == newCustomerButton) {
            boolean canTheCustomerOrderMore;

            boolean isThisAReturningCustomer;

            canTheCustomerOrderMore = canTheyOrderMore(); //calls the canTheyOrderMore() method and stores
            //it as canTheCustomerOrerMore
            //true if there is enough water for more cups of coffee
            
            //gets the phone number 
            String phoneNumberOfNewCustomer = JOptionPane.showInputDialog(dialogBoxes, "Please enter your phone number ",
                    "Cuppa Code - New Customer",
                    JOptionPane.QUESTION_MESSAGE);

            //takes out any non-digit characters
            phoneNumberOfNewCustomer = phoneNumberOfNewCustomer.replaceAll("[^\\d]", ""); //replacing all non-digit characers

            //checks the length of the phone number (it cannot be longer than 10 digits)
            while (phoneNumberOfNewCustomer.length() != 10) { //if there are two many numbers then it will accept a new format
                phoneNumberOfNewCustomer = JOptionPane.showInputDialog(dialogBoxes, "This is an incorrect format. Please try again: ",
                        "Cuppa Code - New Customer",
                        JOptionPane.ERROR_MESSAGE);
            }

            //calls the numberMatchForNew and sends the phone number. If the number matches
            //they are a returning customer NOT A NEW CUSTOMER
            
            isThisAReturningCustomer = numberMatchForNew(phoneNumberOfNewCustomer);

            //validation for a number matching a number in the database
            if (isThisAReturningCustomer == true) {
                message("You match a person in our system! Welcome back " + nameOfTheReturningCustomer);
                discountMatch = true;
                builder.append("Receipt for Customer: ").append(phoneNumberOfNewCustomer).append(", ").append(nameOfTheReturningCustomer).append("\n").append(date).append("\n\n");
                //this adds the returning customer phone number and name to the receipt
            } 
            
            //if the customer is actually a new person
            else{
                nameOfNewCustomer = JOptionPane.showInputDialog(dialogBoxes, "Please enter your name", "Cuppa Code", JOptionPane.QUESTION_MESSAGE); //gets the name 
                JOptionPane.showMessageDialog(dialogBoxes, "Thank you for signing up with Cuppa Code Coffee, " + nameOfNewCustomer, "Cuppa Code - New Customer", 
                        JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(dialogBoxes, "After your first visit you will recevie 10% off your total for all returning visits",
                        "Cuppa Code - New Customer",
                        JOptionPane.INFORMATION_MESSAGE); //informs the customer of the 10% discount for next time
                builder.append("Receipt for Customer ").append(phoneNumberOfNewCustomer).append(", ").append(nameOfNewCustomer).append("\n").append(date).append("\n\n");
                //this adds the newCustomer phone number and name

            }

            setVisibility(westPanel); //makes the coffee combo boxes visible 
            
            setInvisibility(eastPanel); //sets the customer buttons to invisible 
            
            centerPanel.setVisible(true); //sets the receipt text box to visible 
          
            enterButton.setVisible(true); //sets the enter button to visible 
            questionsAboutCoffeeButton.setVisible(true); //sets the question button to visible 

            //validation so that a new customer cannot order more if the machine is empty
            if (canTheCustomerOrderMore == false) {
                message("Sorry, but this machine is completely empty!");
                resetTheEntireBox(); //calls the reset method which resets the frame for the next customer 
            }
        }

        //source for returning customer button
        if (source == returningCustomerButton) {
            boolean canTheCustomerOrderMore;
            canTheCustomerOrderMore = canTheyOrderMore(); //calls the method to see if there is enough
            //water for more coffee

            String phoneNumber = JOptionPane.showInputDialog(dialogBoxes, "Please enter your phone number", "Cuppa Code - Returning Customer",
                    JOptionPane.QUESTION_MESSAGE); //getting the phone number input
            phoneNumber = phoneNumber.replaceAll("[^\\d]", ""); //replacing all non-digit characers

            //more validation for the number length
            while (phoneNumber.length() != 10) { 
                phoneNumber = JOptionPane.showInputDialog(dialogBoxes, "This is an incorrect format. Please try again: ", "Cuppa Code - Returning Customer",
                        JOptionPane.ERROR_MESSAGE);

                phoneNumber = phoneNumber.replaceAll("[^\\d]", ""); //replacing all non-digit characers
            }

            //calls the numberMatch method and receives a boolean 
            //if bool is true then the customer is already in the system
            //if bool is false then the customer is not yet in the system and therefore a new customer
            boolean returningCustomerMatch = numberMatch(phoneNumber);

            if (returningCustomerMatch == true) {
                discountMatch = true; //turns the customer discount boolean to true 
                builder.append("Receipt for Customer: ").append(phoneNumber).append(", ").append(nameOfTheReturningCustomer).append("\n").append(date).append("\n\n");
                //this adds the returning customer phone number and name to the receipt
            } else {
                builder.append("Receipt for Customer ").append(phoneNumber).append(", ").append(newCustomerNameWithReturningValidation).append("\n").append(date).append("\n\n");
                //this adds the newCustomer phone number and name
                //this happens if the customer clicks returning, but isn't yet in the system.  
            }

            setVisibility(westPanel); //makes the combo boxes visible 
            setInvisibility(eastPanel);//makes the customer buttons invisilbe
            enterButton.setVisible(true); //makes the enter button visible
            questionsAboutCoffeeButton.setVisible(true); //sets the question button as visible 
            setVisibility(centerPanel); //sets the center panel to visible by calling a method 

            //validation for if the machine doesn't have enough water 
            if (canTheCustomerOrderMore == false) {
                message("Sorry, but this machine is completely empty!");
                resetTheEntireBox(); //calls this method to reset the screen
            }
        }
     

        //just displays a dialog box about the different types of coffee roasts if the questions button is pressed
        if (source == questionsAboutCoffeeButton) {
            JOptionPane.showMessageDialog(dialogBoxes, "Light Roast coffee beans have a toasted grainy taste and pronounced acidity.They retain most of their caffeine.\n"
                    + "\nMedium Roast beans lack grainy taste and have a more balanced aroma and flavor. They have less caffeine than the light roast but more than darker roasts.\n"
                    + "\nMedium-Dark Roast beans have a heavier body with a taste that may be spicy. They have less caffeine than the medium roast.\n"
                    + "\nDark Roast beans have a taste that resembles the roasting process which is often bitter or smokey. They have the least amount of caffeine in a roast.", "Cuppa Code - Coffee Information",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        //if the enter button is pressed 
        if (source == enterButton) { //asks if the customer would like another cup of coffee!!!!

            boolean canTheCustomerOrderMore;
            
            //see if this is their last cup of coffee
            finalChoice = JOptionPane.showConfirmDialog(dialogBoxes, "Would you like another cup of coffee?", "Cuppa Code", JOptionPane.YES_NO_OPTION);

            canTheCustomerOrderMore = canTheyOrderMore();//calls the method to see if there is enough 
            //coffee to order more 
            

            //if they want another cup of coffee
            if (canTheCustomerOrderMore == true) {
                if (finalChoice == 0) { //if the customer wants another cup of coffee

                    numberOfCoffeeCups += 1; //number of cups increments (for total number)
                    numberOfCoffeeCups2 += 1; //number of cups increments (for customer's total)

                    //this variable stores the integer of the selected index 
                    int coffeeFlavorSelected = coffeeFlavorsComboBox.getSelectedIndex();

                    //if the customer chose then not default coffee flavor 
                    if (coffeeFlavorSelected != 1) {
                        builder.append(coffeeFlavorsComboBox.getSelectedItem() + "\t\t$1.25");
                        //this adds the coffee and the price to the string called builder 
                    } else {
                        builder.append(coffeeFlavors[1] + "\t\t$1.25");
                    } //this adds the default (which is medium roast) and price to the string 

                    
                    //this variable stores the integer of the selected coffee creamers Combo box choice
                    int creamerSelected = coffeeCreamersComboBox.getSelectedIndex();

                    if (creamerSelected != 0) { //if the creamer chosens is not none then 

                        builder.append(NEWLINE); //new line
                        builder.append(HYPEN); //hypen
                        builder.append(coffeeCreamersComboBox.getSelectedItem()); //and creamer are added to the builder 

                    } else { //this happens if the person wants no creamer 

                        builder.append(NEWLINE); //new line
                        builder.append(HYPEN); //hypen
                        builder.append("No Creamer"); //the text no creamer are added to the builder 

                    }

                    //the integer value of the selected sweetener choice 
                    int sweetenerSelected = coffeeSweetenersComboBox.getSelectedIndex();

                    //if the sweetener selected is not no sweetener 
                    if (sweetenerSelected != 0) {
                        builder.append(NEWLINE); //adds newline
                        builder.append(HYPEN); //adds a hypen
                        builder.append(coffeeSweetenersComboBox.getSelectedItem()); //adds the sweetner 
                        builder.append(NEWLINE); //adds a new line
                        builder.append(NEWLINE); //adds a new line
                    } else { //if no sweetener is chosen
                        builder.append(NEWLINE); //adds a new line
                        builder.append(HYPEN); //adds a hypen
                        builder.append("No Sweetener "); //adds the text no sweetener 
                        builder.append(NEWLINE); //adds a new line
                        builder.append(NEWLINE); //adds a new line

                    }
                 
                    resetTheFrame(); //resets the frame for the next customer 
                }
                //if the customer cannot order more, then it accepts the 32nd cup and displays total 
                else {
                    
                    int coffeeFlavorSelected = coffeeFlavorsComboBox.getSelectedIndex();

                    if (coffeeFlavorSelected != 1) {
                        builder.append(coffeeFlavorsComboBox.getSelectedItem() + "\t\t$1.25");
                    } else {
                        builder.append(coffeeFlavors[1] + "\t\t$1.25");
                    }

                    //integer value for coffee selected 
                    int creamerSelected = coffeeCreamersComboBox.getSelectedIndex();

                    //if not default
                    if (creamerSelected != 0) {

                        builder.append(NEWLINE); //new line
                        builder.append(HYPEN); //hypen
                        builder.append(coffeeCreamersComboBox.getSelectedItem()); //no creamer

                    //if no creamer 
                    } else {

                        builder.append(NEWLINE); //new line
                        builder.append(HYPEN); //hypen
                        builder.append("No Creamer");

                    }

                   
                    //integer value 
                    int sweetenerSelected = coffeeSweetenersComboBox.getSelectedIndex();
                    
                    //if not defaul
                    if (sweetenerSelected != 0) {
                        builder.append(NEWLINE);
                        builder.append(HYPEN);
                        builder.append(coffeeSweetenersComboBox.getSelectedItem());
                        builder.append(NEWLINE);
                        builder.append(NEWLINE);
                        
                    //if no sweetener
                    } else {
                        builder.append(NEWLINE);
                        builder.append(HYPEN);
                        builder.append("No Sweetener");
                        builder.append(NEWLINE);
                        builder.append(NEWLINE);

                    }
                    
                    finishButton.setVisible(true); //sets the finish button to visible 
                    questionsAboutCoffeeButton.setVisible(false); //sets the question button to false
                    enterButton.setVisible(false); //sets the enter button to false 
                    
                    //this is the last incrementation 
                    numberOfCoffeeCups += 1; //increments by one 
                    numberOfCoffeeCups2 += 1; //increments by one
                    
                    
                   
                    coffeePriceForReceipt = calculatePriceForCoffee(); //calls the calculate price method
                    coffeeSalesTaxForReceipt = calculateTax(); //calls the calculate tax method 
                    
                    //this calls the total price method and sends both the tax and coffee price as arguments
                    coffeeTotal = totalPrice(coffeeSalesTaxForReceipt, coffeePriceForReceipt);
                    
                    //this is the double which calls the method discount price. Its sends the boolean of dsicount match
                    //if the discount match is true then the discount price will be the 10% 
                    discountPrice = discountPrice(discountMatch);

                    //if there is no discout this is the string of the receipt 
                    if (discountMatch == false) {
                        builder.append(
                                "      \n"
                                + "SubTotal:\t\t$" + df.format(coffeePriceForReceipt) + "\n" //prints out the subtotal with the coffee price
                                + //before tax
                                "Tax:\t\t$" + df.format(coffeeSalesTaxForReceipt) + "\n" //prints out the tax total 
                                + //tax
                                "Total:\t\t$" + df.format(coffeeTotal)); //prints out the final total 

                    }
                    
                    //if there is a discount 
                    if (discountMatch == true) {
                        builder.append("\n"
                                + "You have a 10% discount, " + nameOfTheReturningCustomer + "\n" //this prints that there is a discount
                                + "Discount:\t\t$" + df.format(discountPrice) + "\n" //this prints how much the discount is 
                                + "SubTotal:\t\t$" + df.format(coffeePriceForReceipt - discountPrice) + "\n" //prints out the subtotal (minus the 10%
                                + //before tax
                                "Tax:\t\t$" + df.format(coffeeSalesTaxForReceipt) + "\n\n" //prints out the tax total (doesn't factor in discount)
                                + //tax
                                "Total:\t\t$" + df.format(coffeeTotal)); //prints out the total total 

                    }

                    setComboInvisible(); //sets the combo buttons invisible
                }

                receiptTextBox.setFont(littleFont); //sets the font for the receipt 
                String resultString = builder.toString(); //confirms of all the strings are in one place

                receiptTextBox.setText(resultString); //sets the entire string into the text box

            }

            //this entire section is for the last coffee to be made if there is no more water after it
            //if 32 cups have already been made 
            if (canTheCustomerOrderMore == false) {
                JOptionPane.showMessageDialog(dialogBoxes, "There is not enough water for more coffee to be made. Sorry!", "Cuppa Code",
                        JOptionPane.ERROR_MESSAGE);
                int coffeeFlavorSelected = coffeeFlavorsComboBox.getSelectedIndex();

                if (coffeeFlavorSelected != 1) {
                    builder.append(coffeeFlavorsComboBox.getSelectedItem() + "\t\t$1.25");
                } else {
                    builder.append(coffeeFlavors[1] + "\t\t$1.25");
                }

                //int thisIsSoStupid = coffeeFlavorsComboBox.getSelectedIndex();
                // Object selected = new Object (coffeeFlavorsComboBox.getSelectedItem());
                //builder.append(selected); //writes the coffee on the receipt  
                int creamerSelected = coffeeCreamersComboBox.getSelectedIndex();

                // JOptionPane.showMessageDialog(null, creamerSelected);
                if (creamerSelected != 0) {

                    builder.append(NEWLINE); //new line
                    builder.append(HYPEN); //hypen
                    builder.append(coffeeCreamersComboBox.getSelectedItem()); //no creamer

                } else {

                    builder.append(NEWLINE);
                    builder.append(HYPEN);
                    builder.append("No Creamer");

                }

                // coffeeSweetenersComboBox.setSelectedIndex(0);
                int sweetenerSelected = coffeeSweetenersComboBox.getSelectedIndex();

                // JOptionPane.showMessageDialog(null, sweetenerSelected);
                //sweetenerSelected = (String)coffeeSweetenersComboBox.getSelectedItem();
                //  JOptionPane.showMessageDialog(null, sweetenerSelected);
                if (sweetenerSelected != 0) {
                    builder.append(NEWLINE);
                    builder.append(HYPEN);
                    builder.append(coffeeSweetenersComboBox.getSelectedItem());
                    builder.append(NEWLINE);
                    builder.append(NEWLINE);
                } else {
                    builder.append(NEWLINE);
                    builder.append(HYPEN);
                    builder.append("No Sweetener");
                    builder.append(NEWLINE);
                    builder.append(NEWLINE);

                }
                
                finishButton.setVisible(true);
                enterButton.setVisible(false);
                questionsAboutCoffeeButton.setVisible(false);
                numberOfCoffeeCups += 1;
                numberOfCoffeeCups2 += 1;

                
                JOptionPane.showMessageDialog(null, numberOfCoffeeCups);

                //calls the necessary methods for calculations
                coffeePriceForReceipt = calculatePriceForCoffee();
                coffeeSalesTaxForReceipt = calculateTax();
                coffeeTotal = totalPrice(coffeeSalesTaxForReceipt, coffeePriceForReceipt);
                discountPrice = discountPrice(discountMatch);

               
                if (discountMatch == false) {
                    builder.append(
                            "      \n"
                            + "SubTotal:\t\t$" + df.format(coffeePriceForReceipt) + "\n" //prints out the subtotal
                            + //before tax
                            "Tax:\t\t$" + df.format(coffeeSalesTaxForReceipt) + "\n" //prints out the tax total 
                            + //tax
                            "Total:\t\t$" + df.format(coffeeTotal)); //prints out the total total 

                }

                if (discountMatch == true) {
                    builder.append("\n"
                            + "You have a 10% discount, " + nameOfTheReturningCustomer + "\n"
                            + "Discount:\t\t$" + df.format(discountPrice) + "\n"
                            + "SubTotal:\t\t$" + df.format(coffeePriceForReceipt - discountPrice) + "\n" //prints out the subtotal (minus the 10%
                            + //before tax
                            "Tax:\t\t$" + df.format(coffeeSalesTaxForReceipt) + "\n\n" //prints out the tax total (doesn't factor in discount)
                            + //tax
                            "Total:\t\t$" + df.format(coffeeTotal)); //prints out the total total 

                }
            }

            receiptTextBox.setFont(littleFont); //sets the font 
            String resultString = builder.toString(); //confirms of all the strings are in one place

            receiptTextBox.setText(resultString); //sets the entire string into the text box

        }

        //resets the entire frame
        if (source == finishButton) {
            JOptionPane.showMessageDialog(dialogBoxes, "Thank you for shopping at the Cuppa Code Kiosk", "Cuppa Code", JOptionPane.PLAIN_MESSAGE);
            resetTheEntireBox(); //restarts the order
        }

    }

    //method to make combo boxes
    public void addingAComboBox(String array[], Container container) {
        JComboBox boxy = new JComboBox(array);
        boxy.setBackground(ACCENT_COLOR);
        boxy.setAlignmentX(Component.LEFT_ALIGNMENT);
        container.add(boxy);

    }

    //method to make buttons
    public void addingAButton(String text, Container container) {
        JButton button = new JButton(text);
        button.setBackground(ACCENT_COLOR);
        button.setAlignmentX(Component.TOP_ALIGNMENT);
        container.add(button);

    }

    //resets the west frame to default 
    public void resetTheFrame() {

        coffeeFlavorsComboBox.setSelectedIndex(1);
        coffeeCreamersComboBox.setSelectedIndex(0);
        coffeeSweetenersComboBox.setSelectedIndex(0);

    }

    //makes a panel visible 
    public void setVisibility(Container panel) {
        panel.setVisible(true);
    }

    //makes a panel invisible 
    public void setInvisibility(Container panel) {
        panel.setVisible(false);
    }

    //sets all of the combo boxes invisible 
    public void setComboInvisible() {
        coffeeCreamersComboBox.setVisible(false);
        coffeeSweetenersComboBox.setVisible(false);
        coffeeFlavorsComboBox.setVisible(false);
    }

    //sets all of the combo boxes to visible 
    public void setComboVisible() {
        coffeeCreamersComboBox.setVisible(true);
        coffeeSweetenersComboBox.setVisible(true);
        coffeeFlavorsComboBox.setVisible(true);
    }

    //resets then entire frame 
    public void resetTheEntireBox() {
        coffeeFlavorsComboBox.setSelectedIndex(1);
        coffeeCreamersComboBox.setSelectedIndex(0);
        coffeeSweetenersComboBox.setSelectedIndex(0);
        builder.setLength(0);
        setVisibility(eastPanel);
        setInvisibility(westPanel);
        setComboVisible();
        finishButton.setVisible(false);
        enterButton.setVisible(false);
        questionsAboutCoffeeButton.setVisible(false);
        receiptTextBox.setText("Welcome to the Cuppa Code");
        numberOfCoffeeCups2 = 0;
        discountMatch = false;
        isThisAReturningCustomer = false;
        setInvisibility(centerPanel);
    }

  

    //method for calculating the how to calculate the tax 
    public double calculateTax() {
        double subtotal = 0.00;
        double totalSalesTax = 0.00;
        subtotal = calculatePriceForCoffee(); //calls for the price of the coffee 
        totalSalesTax = (subtotal * stateTax) + (subtotal * countyTax); //calculates the tax 
        return totalSalesTax; //sends the tax back 
    }

    //method for calculating the coffee cup price 
    public double calculatePriceForCoffee() {
        double coffeePrice = 0;
        coffeePrice = numberOfCoffeeCups2 * priceForEightOzCup; //calculates the price for all the cups ordered 

        return coffeePrice; //returns that price 
    }

    //this method adds the tax and the coffee
    public double totalPrice(double tax, double coffee) {
        double totalTotal = tax + coffee;
        return totalTotal;
    }

    //this method calculates the 10 percent discount 
    public double discountPrice(boolean match) { //receives a boolean if true then there is a discount

        double coffeePrice = 0.00;
        double discountPrice = 0.00;
        if (match == true) {
            coffeePrice = calculatePriceForCoffee();
            discountPrice = coffeePrice * 0.01;

        //if there is no discount 
        } else {
            discountPrice = 0.00;
        }

        return discountPrice;
    }

    //method for easily creating a input dialog box 
    public static String inputMessage(String input) {
        String inputForThis;
        inputForThis = JOptionPane.showInputDialog(null, input);
        return inputForThis;
    }

    //method for creating message dialog boxes 
    public static void message(String input) {
        JOptionPane.showMessageDialog(null, input);
    }

    //this method calculates if the person can order more 
    public boolean canTheyOrderMore() {
        boolean orderMore = true;
        if (numberOfCoffeeCups > 30) { //needs to be like this because numcups starts at 1
            orderMore = false;
        }
        return orderMore;
    }

    //this method is for validating phone numbers
    public boolean numberMatch(String numberToBeTested) {

        Path file = Paths.get("C:\\Users\\devan\\Documents\\CustomerList.txt"); //customer list location
        String[] array = new String[50]; //array with customer list 
        String s;
        String delimiter = ","; //removes the ","
        String name;
        String phoneNumberForValidation; //the number that is being validated against
        //  String numberToBeTested;

        boolean match = false; //initially sets this to false 

        try {
            InputStream input = new BufferedInputStream(Files.newInputStream(file)); //reading the file
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            System.out.println();
            s = reader.readLine();

            while (s != null) {
                array = s.split(delimiter);
                phoneNumberForValidation = array[0];
                name = array[1];

                if (numberToBeTested.equals(phoneNumberForValidation)) {
                    nameOfTheReturningCustomer = array[1];
                    match = true; //boolean is true if the phone number matches
                    //this.match
                }

                s = reader.readLine(); //
            }

            reader.close(); //closes the reader

        } catch (Exception e) {
            System.out.println("Message: " + e); //displays an error message if something goes wrong with the try
        }

        //if the "returning customer" does not have a number that is in the system
        if (match == false) {

            newCustomerNameWithReturningValidation = JOptionPane.showInputDialog(dialogBoxes, "You are not in our system. Please enter your name.",
                    "Cuppa Code - New Customer", JOptionPane.ERROR_MESSAGE);

            JOptionPane.showMessageDialog(dialogBoxes, "Thank you for signing up with Cuppa Code Coffee, " + newCustomerNameWithReturningValidation,
                    "Cuppa Code - New Customer", JOptionPane.INFORMATION_MESSAGE);

            JOptionPane.showMessageDialog(dialogBoxes, "After your first visit you will recevie 10% off your total for all returning visits",
                    "Cuppa Code - New Customer",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(dialogBoxes, "Welcome Back " + nameOfTheReturningCustomer, "Cuppa Code - Returning Customer",
                    JOptionPane.PLAIN_MESSAGE);  //shows name and whether it is a match
        }

        return match;
    }

    //this is to sort the arrays into alphabetical order 
    static String[] sortMyString(String myArray[]) {
        for (int i = 0; i < myArray.length - 1; ++i) {
            int minIndex = i;
            for (int j = i + 1; j < myArray.length; ++j) {
                // "<" changed to use of compareTo()            int minIndex = i;

                if (myArray[j].compareTo(myArray[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            // int changed to String
            String temp = myArray[i];
            myArray[i] = myArray[minIndex];
            myArray[minIndex] = temp;
        }

        return myArray; //returning the new sorted array 

    }
    

    //this method is for checking if a number matches w/out the dialog boxes 
    public boolean numberMatchForNew(String numberToBeTested) {

        Path file = Paths.get("C:\\Users\\devan\\Documents\\CustomerList.txt"); //customer list location
        String[] array = new String[51]; //array with customer list (is 51 because of the black line on top)
        String s;
        String delimiter = ","; //removes the ","
        String name;
        String phoneNumberForValidation; //the number that is being validated against
        //  String numberToBeTested;

        boolean match = false; //initially sets this to false 

        try {
            InputStream input = new BufferedInputStream(Files.newInputStream(file)); //reading the file
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            System.out.println();
            s = reader.readLine();

            while (s != null) {
                array = s.split(delimiter);
                phoneNumberForValidation = array[0];
                name = array[1];

                if (numberToBeTested.equals(phoneNumberForValidation)) {
                    nameOfTheReturningCustomer = array[1];
                    match = true; //boolean is true if the phone number matches
                 
                }

                s = reader.readLine(); //
             
            }

            reader.close(); //closes the reader

        } catch (Exception e) {
            System.out.println("Message: " + e); //displays an error message if something goes wrong with the try
        }

        return match;
    }
    
    //this is the main 
      public static void main(String[] args) {
        sortMyString(coffeeSweeteners); //sorts the coffee sweeteners array
        sortMyString(coffeeCreamers); //sorts the coffee creamers array 
        CoffeeSelection frame = new CoffeeSelection(); //creates the frame 
        frame.setVisible(true); //sets teh frame to visible 
    }

}
