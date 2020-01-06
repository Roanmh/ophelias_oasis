/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opheliasoasis;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static java.lang.System.out;

/**
 * A serializable list of all reservations and base rates.
 *
 * @author roanm
 */
public class Records {

    private String db_loc;
    private ArrayList<Reservation> res_db;
    private TreeMap<LocalDate, Float> rate_db;
    // Part of a requirement that a separate backup be kept. Written by
    // backup_records function stub. (Definitely a cop-out)
    private final String backup_loc = "backup.db";

    public ArrayList<Reservation> getResDB() {
        return res_db;
    }

    /**
     * Contructs record class with custom database location.
     *
     * @param dbloc location of database to use
     */
    public Records(String db_loc) {
        this.db_loc = db_loc;
        this.res_db = null;
        this.rate_db = null;

        // Load database with extra checks
        fetch_db();
    }

    /**
     * Constructs record class with default database location.
     */
    public Records() {
        this("oo.db");
    }


    /**
     * Read in database (only if not already in memory).
     */
    private void fetch_db() {
        if (this.res_db == null || this.rate_db == null) {
            read_db();
        }
    }

    /**
     * Finds reservations by date checked in.
     *
     * @param date_in check-in date to return from database
     * @returns list of index, reservation pairs matching the date
     */
    public List<Pair<Integer, Reservation>> lookup(LocalDate date_in) {

        List<Pair<Integer, Reservation>> result = new ArrayList<>();
        for (int i = 0; i < res_db.size(); i++) {
            Reservation res = res_db.get(i);
            if (date_in.isEqual(res.getDateIn())
                    || date_in.isEqual(res.getDateOut())
                    || (date_in.isAfter(res.getDateIn())
                        && date_in.isBefore(res.getDateOut()))) {
               result.add(new Pair(i, res));
            }
        }

        return result;
    }

    /**
     * Edits the reservation given by res_id.
     *
     * <p> NOTE: All parameters can be `null` for no change.
     *
     * @param res_id reservation id to edit
     * @param res_type (unimplemented)
     * @param date_in check in date of the reservation
     * @param date_out check out date of the reservation
     * @param name name of the reservation holder
     * @param cc credit card of the reservation
     * @param email of the reservation
     * @returns changed resevation object
     */
    public Reservation edit_reservation(int res_id,
                                        Reservation.ResType res_type,
                                        LocalDate date_in, LocalDate date_out,
                                        String name,
                                        CreditCard cc, String email) {
        Reservation res;
        res = this.res_db.get(res_id);

        if (date_in != null && date_in != res.getDateIn()) {
            res.setDateIn(date_in);
        }
        if (date_out != null && date_out != res.getDateOut()) {
            res.setDateOut(date_out);
        }
        if (name != null && name != res.getName()) {
            res.setName(name);
        }
        if (cc != null && cc != res.getCreditCard()) {
            res.setCreditCard(cc);
        }
        if (email != null && email != res.getEmail()) {
            res.setEmail(email);
        }

        write_db();

        return res;
    }

    /**
     * Creates the reservation given by the arguments.
     *
     * @param res_type type of the reservation (see Reservation.ResType)
     * @param date_in check in date of the reservation
     * @param date_out check out date of the reservation
     * @param name name of the reservation holder
     * @param cc credit card of the reservation
     * @param email of the reservation
     * @param isChanged If this reservation is the result of a changed
     *        reservation
     * @returns created resevation object
     */
    public Reservation create_reservation(Reservation.ResType res_type,
                                          LocalDate date_in, LocalDate date_out,
                                          String name,
                                          CreditCard cc, String email,
                                          boolean isChanged) {

        res_db.add(new Reservation(res_type, date_in, date_out, name, cc, email,
                                   isChanged));

        write_db();

        return res_db.get(res_db.size() - 1);
    }

    /**
     * Changes the base rate for a given day.
     *
     * @param date the date to change
     * @param new_rate rate to set the given date to
     */
    public void change_baseRate(LocalDate date, float new_rate) {

        rate_db.put(date, new_rate);

        write_db();
    }

    /**
     * Gets the base rate for a given day.
     *
     * @param date date to get
     */
    public Float get_baseRate(LocalDate date) {
        Float baseRate;
        try {
            baseRate = rate_db.get(date);
            return baseRate;
        }
        catch (Exception e){
            System.out.println("Base rate not set for this date " + date);
            return Float.valueOf(0);
        }

    }

    /**
     * Notes a reservation as having checked in.
     *
     * @param res_id reservation id to check in
     */
    public void check_in(int res_id) {
        Reservation reservation = getResDB().get(res_id);
        reservation.setCheckInStatus(Boolean.TRUE);
        System.out.println("The person has checked in and the reservation status is changed from expected arrival to occupied(checked in)");
        System.out.println("Your room number is: "+reservation.getRoomNumber());

        write_db();
    }

    /** Writes database to a backup location specified by the class. */
    public void backup_records() {
        write_db(backup_loc);
    }

    /**
     * Assigns room numbers to the reservations that check in on the given day.
     *
     * @param date_in check in date to assign room numbers for
     */
    public void assignDailyRoomnumbers(LocalDate date_in){
        List<Integer> result = occupiedRoomnumbers(date_in);
        fetch_db();
        for (int i = 0, j =1; (i < res_db.size()& j<=45 ); i++, j++) {
            Reservation res = res_db.get(i);
            if (date_in.isEqual(res.getDateIn()) && (!(result.contains(j)))) {
               res.setRoomNumber(j);
            }
        }
        write_db();

    }

    /**
     * Gets list of room numbers that have already been assigned.
     *
     * @param date_in date to check for reserved room numbers
     */
    public List<Integer> occupiedRoomnumbers(LocalDate date_in){
        fetch_db();

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < res_db.size(); i++) {
            Reservation res = res_db.get(i);
            if (date_in.isEqual(res.getDateIn())
                    || date_in.isEqual(res.getDateOut())
                    || (date_in.isAfter(res.getDateIn())
                        && date_in.isBefore(res.getDateOut()))) {
               result.add(res.getRoomNumber());
            }
        }
        return result;

    }

    /**
     * Read the database from the location specified by the object.
     */
    private void read_db() {
        Pair<ArrayList<Reservation>, TreeMap<LocalDate, Float>> dbs;

        // Using limited context input stream.
        try (FileInputStream file = new FileInputStream(db_loc);
             ObjectInputStream in = new ObjectInputStream(file)) {

            dbs = (Pair<ArrayList<Reservation>, TreeMap<LocalDate, Float>>) in.readObject();

            this.res_db = dbs.getKey();
            this.rate_db = dbs.getValue();

        } catch (IOException | ClassNotFoundException e) {
            // Fail (Somewhat) gracefully
            out.println("Database file is unreadable or not found. Working on empty database.");
            this.res_db = new ArrayList<>();
            this.rate_db = new TreeMap<>();
        }
    }

    /**
     * Writes the database to the given location.
     *
     * <p>NOTE: The location parameter is included to allows use for backups.
     *
     * @param loc location to write the database to.
     */
    private void write_db(String loc) {

        if (this.res_db == null || this.rate_db == null) {
            out.println("Writing blank structures to database.");
            this.res_db = new ArrayList<>();
            this.rate_db = new TreeMap<>();
        }

        try (FileOutputStream file = new FileOutputStream(loc);
             ObjectOutputStream out = new ObjectOutputStream(file)) {

            out.writeObject(new Pair<>(res_db, rate_db));

        } catch (IOException e) {
            out.println("Unable to write to database file.");
            e.printStackTrace();
        }
    }

    /**
     * Writes the database to the location sprecified by the object.
     */
    private void write_db() {
        write_db(db_loc);
    }

    /** Prints to string (for debug purposes) */
    public String toString() {
        return "Records@\"%s\"(\n" + this.res_db.toString() + "\n" + this.rate_db.toString() + "\n)\n";
    }

}
