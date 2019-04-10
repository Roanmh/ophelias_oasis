/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opheliasoasis;

import java.util.Date;
import java.util.List;

import static java.lang.System.out;

/**
 * Main class and CLI operator.
 *
 * @author Computer
 */
public class OpheliasOasis {

    private final Records record;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        out.println("Software Engineering");
    }

    public OpheliasOasis() {
        this.record = new Records();
    }

    private void parse() {
    }

    private void res_Create() {
    }

    private void res_ChangeDate(Date date_in, Date date_int_new, Date date_out_new) {
        // Get specific Reservation id from lookup
        out.println("First, Choose a Reservation");
        int res_id = choose_single(date_in);

        // Call edit_reservation on the specific reservation id with the new dates.
        try {
            this.record.edit_reservation(res_id, null, date_int_new, date_out_new, null, null,
                                         null);
        } catch (RecordsException e) {
            out.println("An error occurred while Changing Reservation Date: " + e.getMessage());
        }
    }

    /**
     * Prompt a query and return a list of matching reservations.
     * @param date_in
     * @return
     */
    private List lookup(Date date_in) {
    }

    /**
     * Interactively lookup reservations and specify util one reservation is found.
     *
     * @param date_in
     * @return
     */
    private int choose_single(Date date_in) {
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
