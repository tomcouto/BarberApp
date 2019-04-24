package edu.quinnipiac.barberx;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.fasterxml.jackson.databind.util.ObjectIdMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AccountHandler {
    private String userEmail;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Object appointments;
    ArrayList<HashMap<String, Object>> appts;
    HashMap<String, String> map = new HashMap<>();
    Boolean done = false;

    public AccountHandler(){

    }

    public void setEmail(String email){
        userEmail = email;
    }


    /**
     * This class will add all the account details to an array
     * then allow the app local access to the database after login
     * to avoid reaching the database to get info every time
     */

    public void pullDBAppointments(String email){

        DocumentReference docRef = db.collection("users")
                .document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    //if there is a user with the entered email
                    if (document.exists()) {
                        Log.d("TAG HELLPO", "Cached document data: " + document.getData());
                        Map<String, Object> data = document.getData();

                        appts = (ArrayList<HashMap<String, Object>>) data.get("appointments");
                        Log.d("TAG LISTTT", "Cached document data: " + appts.toString());

                        for (HashMap<String, Object> appt: appts
                             ) {
                            Timestamp stamp = (Timestamp) (appt.get("date"));
                            //convert date
                            Date date = new Date(stamp.toString());
                            SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                            String fullDate = sfd.format(date);
                            String[] dateArr = fullDate.split(" ");

                            String name = (String) appt.get("user");
                            map.put(dateArr[1], name);
                            System.out.println("map:" + map);
                        }
                        done = true;

                        Log.d("TAG MAPPP", "Cached document data: " + map.toString());


                    } else {
                        //Toast.makeText(LoginActivity.this,"Username or Password Invalid.", Toast.LENGTH_LONG).show();
                    }
                } else {
                }
            }
        });

        Log.d("TAG LISTTT", "hello");
    }


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
