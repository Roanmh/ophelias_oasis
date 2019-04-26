/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opheliasoasis;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;
import javafx.util.Pair;


/**
 * Main class and CLI operator.
 *
 * @author Computer
 */
public class OpheliasOasis {
Records records = new Records();// not sure if this is the correct way to go about this
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        OpheliasOasis OO = new OpheliasOasis(); //needs load logic in constuctor
        
        System.out.println("Software Engineering");

        OO.parse();
        
        //TODO: Exit logic

    }

    public OpheliasOasis() {
        //TODO: Load logic
    }

    private void parse() {
        String cmd;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Ophelia's Oasis Reservation System.\n"
                          +"Type a command to begin, or type help for more information");
    
        while(true){
            cmd = scanner.nextLine();
            if (cmd.toLowerCase().contains("help")||cmd.toLowerCase().contains("?")){
                System.out.println("Command List:\n"
                        + "Reservation Create: Create a new reservation\n"
                        + "Reservation Edit: Edit an already exsiting reservation\n"
                        + "Reservation Lookup: Look up resrvation information by date\n"
                        + "Reservation Cancel: Cancel a reservation\n"
                        + "Reservation Check In: Check in a reservation\n"
                        + "Make Daily Occupancy: Generate the daily occupancy report\n"
                        + "Make Daily Arrivals: Generate the daily arrivals report\n"
                        + "Make Monthy Occupancy: Generate the monthly occupancy report\n"
                        + "Make Expected Income: Generate the expected income reprot\n"
                        + "Make Incentive: Generate the incetive discount report\n"
                        + "Make Bill: Generate a bill\n"
                        + "Make Email: Generate a reminder email\n"
                        + "Change Base Rate: Change the base rate for a particular day\n"
                        + "Exit: Save and exit the reservation system\n");
            }
            else if(cmd.toLowerCase().contains("res")){
                if (cmd.toLowerCase().contains("cre")){
                   res_Create();
                }
                else if(cmd.toLowerCase().contains("edi")){
                    res_ChangeDate();
                }
                else if(cmd.toLowerCase().contains("look")){
                    res_Checkin();
                }
                else if(cmd.toLowerCase().contains("can")){
                    res_Cancel();
                }
                else if(cmd.toLowerCase().contains("che")){
                    res_Checkin();
                }            
            }
            else if(cmd.toLowerCase().contains("make")){
                if(cmd.toLowerCase().contains("daily oc")){
                    mk_DailyOccupancy();
                }
                else if(cmd.toLowerCase().contains("daily arr")){
                    mk_DailyArrival();
                }
                else if(cmd.toLowerCase().contains("month")){
                     mk_MonthlyOccupancy();
                }
                else if(cmd.toLowerCase().contains("expe")){
                    mk_ExpectedIncome();
                }
                else if(cmd.toLowerCase().contains("ince")){
                    mk_IncentivesDiscount();
                }
                else if(cmd.toLowerCase().contains("bill")){
                    mk_Bill();
                }
                else if(cmd.toLowerCase().contains("email")){
                    mk_Email();
                }
            }
            else if(cmd.toLowerCase().contains("change")){
                changeBaseRate();
            }
            else if(cmd.toLowerCase().contains("exit")){
                break;
            }

        }
        
    }

    private void res_Create() {
        String temp, name, email, cardHolder;
        int expMonth, expYear, CardNumber, CSV;
        float avgOcc;
        long daysbetween, daysInAdvance;
        LocalDate date_in, date_out, currentDay, runningDay;
        Reservation.ResType res_type;
        List<Pair<Integer, Reservation>> IncentiveCheck;
                
        DateTimeFormatter DTF = DateTimeFormatter.ofPattern("MM-dd-yy");
        currentDay = LocalDate.now();
        
        Scanner scanner = new Scanner(System.in);
        avgOcc=0;
        email = "N/A";
        cardHolder = "N/A";
        expMonth =0;
        expYear = 0;
        CardNumber = 0;
        CSV =0;

        while (true) {                                                           //get reservation type
            System.out.print("What type of reservation do you wish to create?\n");
            temp = scanner.nextLine();
            //check user input for substrings and cast into known type
            if (temp.toLowerCase().contains("six") || temp.toLowerCase().contains("60")) {  //look for some form of "sixty" or "60"
                res_type = Reservation.ResType.sixty_day;
                daysInAdvance=60;
                break;
            } else if (temp.toLowerCase().contains("pre")) {                                   //look for some form or "prepaid"
                res_type = Reservation.ResType.prepaid;
                daysInAdvance=90;
                break;
            } else if (temp.toLowerCase().contains("conv") || temp.toLowerCase().contains("trad")) { //look for some form of "conventional" or "traditional"
                res_type = Reservation.ResType.conventional;
                daysInAdvance=999;
                break;
            } else if (temp.toLowerCase().contains("incen")) {                                   //look for some form of "incentive"
                res_type = Reservation.ResType.incentive;
                daysInAdvance=30;
                break;
            } else if (temp.toLowerCase().contains("quit") || temp.toLowerCase().contains("exit") || temp.toLowerCase().contains("stop")) { //Stop creating a reservation
                System.out.print("Exiting Create Reservation\n");
                return;
            } else if (temp.toLowerCase().contains("?") || temp.toLowerCase().contains("help")) {//user help
                System.out.print("Valid reservation types are: sixty day, prepaid, convetional, incentive\n"
                        + "A Sixty Day reservation must be made 60 days in advance, it requires an email, name, check in date, and check out date\n"
                        + "A Prepaid reservation must be made 90 days in advance, it requires a name, credit card number, check in and check out data\n"
                        + "A Conventional reservation can be made at any time, it requires a name, credit card number, check in and check out date\n"
                        + "A Incentive reservation must be made less than 30 days from today and must be on days where occupancy is less than 60% "
                        + "it requires a name, credit card number, check in and check out date\n");
                continue;
            } else {
                System.out.print("A valid reservation type has not been entered.\n");
            }
        }

//Get Res Name
        System.out.print("What is the name on this reservation?\n");
        name = scanner.nextLine();
 //Get Check in Date
        System.out.print("What is this the check in date for this reservation? In mm-dd-yy format, ie 01-01-19\n");
        if(res_type!= Reservation.ResType.incentive){
            while (true) {
                    reEnterDate:
                    try {
                        date_in = LocalDate.from(DTF.parse(scanner.nextLine()));
                        daysbetween= ChronoUnit.DAYS.between(currentDay, date_in);
                        if (daysbetween < daysInAdvance) {
                            System.out.print("This check in date is not "+daysInAdvance+" days in advance. A "+res_type+" reservation must be made "+daysInAdvance+" days from today.\n"
                                             + "Please enter a valid check in day\n");
                            break reEnterDate;
                        }
                        else
                            break;
                    }
                    catch (Exception e) {
                       System.out.print("Invalid date, try again. Date must be in mm-dd-yy format\n");
                   }
                }
        }
        else{
            while (true) {

                //THIS NEEDS ADDITIONAL LOGIC TO CHECK IF VALID OCCUPANCY FOR RESESERVATION
                    reEnterDate:
                    try {
                        date_in = LocalDate.from(DTF.parse(scanner.nextLine()));
                        daysbetween= ChronoUnit.DAYS.between(currentDay, date_in);
                        if (daysbetween > daysInAdvance) {
                            System.out.print("This check in date is not less than "+daysInAdvance+" days in advance. A "+res_type+" reservation must be made less than"+daysInAdvance+" days from today.\n"
                                             + "Please enter a valid check in day\n");
                            break reEnterDate;
                        }
                        else
                            break;
                    }
                    catch (Exception e) {
                       System.out.print("Invalid date, try again. Date must be in mm-dd-yy format\n");
                   }
                }
        }
//Get Check out date
        System.out.print("What is this the check out date for this reservation? In mm-dd-yy format, ie 01-01-19\n");
        while (true) {
                    reEnterDate:
                    try {
                        date_out = LocalDate.from(DTF.parse(scanner.nextLine()));
                        daysbetween= ChronoUnit.DAYS.between(date_in, date_out);
                        if (daysbetween <= 0) {
                            System.out.print("This check in date is on or before the check out date of"+date_out.format(DTF)+"\n"
                                             + "Please enter a valid check in day\n");
                            break reEnterDate;
                        }
                        else
                            break;
                    }
                    catch (Exception e) {
                       System.out.print("Invalid date, try again. Date must be in dd-mm-yy format\n");
                   }
               }
 //Gather Reservation specific information
        switch (res_type) {

            case sixty_day:
              System.out.print("What is the email address for this reservation?");
              while(true){
                  temp = scanner.nextLine();
                  if (temp.contains("@")&&(temp.contains(".com")||temp.contains(".edu")||temp.contains(".gov")||temp.contains(".org"))){
                  email=temp;
                  break;
                  }
                  else
                      System.out.print("This is not a valid email address, please try again");
              }

              break;
            case incentive:
                 daysbetween= ChronoUnit.DAYS.between(date_in, date_out);
                 runningDay=date_in;
                 for(int i = 0; i<daysbetween; i++){
                    IncentiveCheck=records.lookup(runningDay);
                    avgOcc = avgOcc+IncentiveCheck.size();
                    runningDay.plusDays(1);
                 }
                 avgOcc=avgOcc/(daysbetween*45);
                 if(avgOcc<=60){
                   break;
                 }
                 else{
                     System.out.println("The average expected occupnacy for the length of this stay is over 60% this is not a valid incetive reservation.\n"
                             + "This reservation will be deleted.");
                     return;
                 }
            case prepaid:
                System.out.println("What is the credit card holders name?");
                cardHolder = scanner.nextLine();

                System.out.print("What is the card number?\n Please only enter the numbers, no spaces or dashes.");
                while (true){
                    temp = scanner.nextLine();
                    if (temp.matches("[0-9}+")&& temp.length()==16){
                        CardNumber = Integer.parseInt(temp);
                        break;
                    }
                    else
                        System.out.println("This is not a valid credit card number, please try again");
                }

                System.out.println("What is the CSV number?");
                while (true){
                    temp = scanner.nextLine();
                    if (temp.matches("[0-9]+")&& temp.length()>=3 && temp.length() <= 4){
                        CSV = Integer.parseInt(temp);
                        break;
                    }
                    else
                        System.out.println("This is not a vaild CSV number, please try again.");
                }

                System.out.println("What is the experation month? \nPlease enther the month as a two digit number as it is displayed on the card.");
                while (true){
                    temp = scanner.nextLine();
                    if (temp.matches("[0-9]+") && temp.length()==2 && Integer.parseInt(temp)<= 12){
                        expMonth = Integer.parseInt(temp);
                        break;
                    }
                    else
                        System.out.println("This is not a vaild month, please try again.");
                }

                System.out.println("What is the experation year? \nPlease enther the month as a two digit number as it is displayed on the card.");
                while (true){
                    temp = scanner.nextLine();
                    if (temp.matches("[0-9]+") && temp.length()==2){
                        expYear = Integer.parseInt(temp);
                        break;
                    }
                    else
                        System.out.println("This is not a vaild year, please try again.");
                }

                break;
            case conventional:
                System.out.println("What is the credit card holders name?");
                cardHolder = scanner.nextLine();

                System.out.print("What is the card number?\n Please only enter the numbers, no spaces or dashes.");
                while (true){
                    temp = scanner.nextLine();
                    if (temp.matches("[0-9}+")&& temp.length()==16){
                        CardNumber = Integer.parseInt(temp);
                        break;
                    }
                    else
                        System.out.println("This is not a valid credit card number, please try again");
                }

                System.out.println("What is the CSV number?");
                while (true){
                    temp = scanner.nextLine();
                    if (temp.matches("[0-9]+")&& temp.length()>=3 && temp.length() <= 4){
                        CSV = Integer.parseInt(temp);
                        break;
                    }
                    else
                        System.out.println("This is not a vaild CSV number, please try again.");
                }

                System.out.println("What is the experation month? \nPlease enther the month as a two digit number as it is displayed on the card.");
                while (true){
                    temp = scanner.nextLine();
                    if (temp.matches("[0-9]+") && temp.length()==2 && Integer.parseInt(temp)<= 12){
                        expMonth = Integer.parseInt(temp);
                        break;
                    }
                    else
                        System.out.println("This is not a vaild month, please try again.");
                }

                System.out.println("What is the experation year? \nPlease enther the month as a two digit number as it is displayed on the card.");
                while (true){
                    temp = scanner.nextLine();
                    if (temp.matches("[0-9]+") && temp.length()==2){
                        expYear = Integer.parseInt(temp);
                        break;
                    }
                    else
                        System.out.println("This is not a vaild year, please try again.");
                }

                break;

        }
        CreditCard creditCard =  new CreditCard(cardHolder, expMonth, expYear, CardNumber, CSV);
        
        records.create_reservation(res_type, date_in, date_out, name, creditCard, email);
        System.out.println("Reservation created succefully!");
    }

    private void res_ChangeDate() {
    }

    /**
     * Prompt a query and return a list of matching reservations.
     * @param date_in
     * @return
     */
    private List lookup(LocalDate date_in) {
    }

    /**
     * Interactively lookup reservations and specify util one reservation is found.
     *
     * @param date_in
     * @return
     */
    private int choose_single(LocalDate date_in) {
    }

    private void res_Cancel() {
    }

    private void res_Checkin() {
    }

    private void mk_DailyOccupancy() {
    }

    private void mk_DailyArrival() {
    }

    private void mk_MonthlyOccupancy() {
    }

    private void mk_ExpectedIncome() {
    }

    private void mk_IncentivesDiscount() {
    }

    private void mk_Bill() {
    }

    private void mk_Email() {
    }

    private void changeBaseRate() {
    }

    private void exit() {
    }
}
