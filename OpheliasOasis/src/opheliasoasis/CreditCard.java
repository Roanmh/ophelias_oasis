/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opheliasoasis;

import sun.font.CreatedFontTracker;

import java.io.Serializable;

/**
 *
 * @author roanm
 */
public class CreditCard implements Serializable {
    String CardHolder;
    int expMonth;
    int expYear;
    int cardNumber;
    int CSV;
    
    public CreditCard(String CardHolder, int expMonth, int expYear, int cardNumber, int CSV){
        this.CardHolder = CardHolder;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.cardNumber = cardNumber;
        this.CSV = CSV;
        
    }
    
    public String getCardHolder(){
        return CardHolder;
    }
    
    public int getExpMonth(){
        return expMonth;
    }
    
    public int getExpYear(){
        return expYear;
    }
    
    public int getCardNumber(){
        return cardNumber;
    }
    
    public int getCSV(){
        return CSV;
    }
    public void setCardHolder(String NewName){
        CardHolder = NewName;
    }
    public void setExpMonth(int NewMonth){
        expMonth = NewMonth;
    }
    public void setExpYear(int NewYear){
        expYear = NewYear;
    }
    public void setCardNumber(int NewCardNumber){
        cardNumber = NewCardNumber;
    }
    public void setCSV(int NewCSV){
        CSV = NewCSV;
    }

    public String toString() {
        return String.format("CC(%s, %d/%d, ...%d, %s)", CardHolder, expMonth, expYear, cardNumber % 10000, CSV);
    }

    public boolean equals(CreditCard obj) {
        if (obj == null) return false;
        return this.getCardHolder() == obj.getCardHolder()
                && this.getCardNumber() == obj.getCardNumber()
                && this.getCSV() == obj.getCSV()
                && this.getExpMonth() == obj.getExpMonth()
                && this.getExpYear() == obj.getExpYear();
    }
}
