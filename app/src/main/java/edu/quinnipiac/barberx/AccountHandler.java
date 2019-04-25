package edu.quinnipiac.barberx;

import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AccountHandler {
    private String userEmail;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<HashMap<String, Object>> appts;
    HashMap<String, String> map = new HashMap<>();
    Boolean done = false;
    HashMap<String, String> fakeRequests = new HashMap<>();


    public AccountHandler(){

        fakeRequests.put("9:00 AM", "Alex");
        fakeRequests.put("10:00 AM", "Joe");
        fakeRequests.put("11:00 AM", "Brian");
        fakeRequests.put("12:00 PM", "Greg");
        fakeRequests.put("1:00 PM", "Chris");
        fakeRequests.put("2:00 PM", "Tom");

    }

    public void setEmail(String email){
        userEmail = email;
    }



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

                            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                            cal.setTimeInMillis(stamp.getSeconds() * 1000L);
                            String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();

                            Log.d("TAG DATEE", "Cached document data: " + date);

                            String[] dateArr = date.split(" ");

                            String name = (String) appt.get("user");
                            map.put(dateArr[1], name);
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

}
