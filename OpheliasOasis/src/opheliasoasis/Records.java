/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opheliasoasis;

import java.util.Date;

/**
 * A serializable list of all reservations and base rates.
 *
 * @author roanm
 */
public class Records {

    public Records() {
        this(null);
    }

    public Records(String db_loc) {
    }

    public void lookup(Date date_in) {
    }

    public Reservation edit_reservation(int res_id,
                                        Reservation.ResType res_type,
                                        Date date_in, Date date_out,
                                        String name,
                                        CreditCard cc, String email) throws RecordsException {
    }

    public Reservation create_reservation(Reservation.ResType res_type,
                                          Date date_in, Date date_out,
                                          String name,
                                          CreditCard cc, String email) {
    }

    public Reservation checkin_reservation(int res_id) {
    }

    public void change_baseRate(Date date, float new_rate) {
    }

    public void backup_records() {
    }

}
