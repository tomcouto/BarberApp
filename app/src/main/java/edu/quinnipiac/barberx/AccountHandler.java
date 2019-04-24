package edu.quinnipiac.barberx;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AccountHandler {

    /**
     * This class will add all the account details to an array
     * then allow the app local access to the database after login
     * to avoid reaching the database to get info every time
     */

    //local account details
    ArrayList<String> accountDetails = new ArrayList<>();
    //save every name in the list based on date
    String[] names = new String[] {"Phil", "John", "Joe", "Mike", "Bill", "Tom"};
    //save the time of each person
    String[] times = new String[] {"8","9","10","11","12","1"};


    //returns the local username
    public String getUsername(int i){ return names[i]; }

    //return local email
    public String getEmail() { return null; }

    public int getAppointments() {return names.length;}

    public String getTime(int i) {return times[i];}

    //return profile picture

    //return all requests

    //return schedule

    //return all uploaded images for profile page
}
