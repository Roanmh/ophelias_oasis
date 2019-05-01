/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opheliasoasis;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javafx.util.Pair;

import static java.lang.String.format;

/**
 * Represents a single reservation and valid operations on it.
 *
 * @author roanm
 */
public class Reservation implements Serializable {
    private ResType res_type;
    private LocalDate date_in;
    private LocalDate date_out;
    private String name;
    private String email;
    private CreditCard cc;
    private int roomNumber;
    private Boolean is_Changed;
    private Boolean is_Cancled;
    private Boolean is_CheckedOut;
    private Boolean is_CheckedIn;

    private LocalDate date_paid;
        

    public enum ResType {
        prepaid, sixty_day, conventional, incentive
    };
    public Reservation(){}

    public Reservation(ResType res_type, LocalDate date_in, LocalDate date_out,
                       String name, CreditCard cc, String email) {
        this.res_type = res_type;
        this.date_in = date_in;
        this.date_out = date_out;
        this.name = name;
        this.cc = cc;
        this.email = email;
        roomNumber = 00;
        is_Changed = false;
        is_Cancled = false;
        is_CheckedOut = false;
        is_CheckedIn = false;

    }
    public ResType getResType(){
        return res_type;
    }

    public void check_in(int res_id) {
        Records record = new Records();
        Reservation reservation = record.getResDB().get(res_id);
        reservation.setCheckInStatus(true);
        System.out.println("The person has checked in and the reservation status is changed from expected arrival to occupied(checked in)");
        System.out.println("Your room number is: "+reservation.getRoomNumber());
    }

    
    public LocalDate getDateIn(){
        return date_in;
    }
    public LocalDate getDateOut(){
        return date_out;
    }
    public LocalDate getDatePaid(){ return date_paid; }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public CreditCard getCreditCard(){
        return cc;
    }
    public int getRoomNumber(){
        return roomNumber;
    }
    public Boolean getChangedStatus(){
        return is_Changed;
    }
    public Boolean getCancledStatus(){
        return is_Cancled;
    }
    public Boolean getCheckedInStatus(){
        return is_CheckedIn;
    }
    public Boolean getCheckedOutStatus(){
        return is_CheckedOut;
    }
    public void setDateIn(LocalDate NewDate_in){
        date_in= NewDate_in;
    }
    public void setDateOut(LocalDate NewDate_out){
        date_out = NewDate_out;
    }
    public void setName(String NewName){
        name = NewName;
    }
    public void setEmail(String NewEmail){
        email = NewEmail;
    }
    public void setCreditCard(CreditCard NewCC){
        cc = NewCC;
    }
    public void setRoomNumber(int NewRoomNumber){
        roomNumber = NewRoomNumber;
    }
    public void setChangedStatus(Boolean NewStatus){
        is_Changed = NewStatus;
    }
    public void setCancledStatus(Boolean NewStatus){
        is_Cancled = NewStatus;
    }
    public void setCheckInStatus (Boolean NewStatus){
        is_CheckedIn = NewStatus;
    }
    public void setCheckOutStatus (Boolean NewStatus){
        is_CheckedOut = NewStatus;
    }

    public String toString() {
        return format("Reservation(%s, in: %s, out: %s, \"%s\", <%s>, %s, %d, chgd: %b, cncl: %b, cko: %b)",
        res_type.toString(),
        date_in.toString(),
        date_out.toString(),
        name,
        email,
        cc.toString(),
        roomNumber,
        is_Changed,
        is_Cancled,
        is_CheckedOut);
    }

}
