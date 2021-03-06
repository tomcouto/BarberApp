package edu.quinnipiac.barberx;

/**
 * RequestFragment is where the user will see all requests to be put on their schedule shown
 * in a recycler view. Once there is requests the recycler view the user can accept/deny them.
 * If accepted the request will be added to the schedule and deleted. If declined it will be deleted.
 * <p>
 * Version: 1.0
 * Authors: Tom Couto and Dominic Smorra
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;

public class RequestFragment extends Fragment {

    ListView lv;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<HashMap<String, Object>> reqs;
    ArrayList<HashMap<String, Object>> appts;
    HashMap<String, String> map = new HashMap<>();
    Boolean done = false;
    Button accept;
    Button decline;

    public RequestFragment() {
        // Required empty public constructor
    }

    public static RequestFragment newInstance(Bundle bundle) {
        Bundle bundle1 = bundle;
        RequestFragment requestFragment = new RequestFragment();
        requestFragment.setArguments(bundle1);
        return requestFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_request, container, false);
        lv = (ListView) v.findViewById(R.id.listview_r);

        accept = (Button) v.findViewById(R.id.accept_btn);
        decline = (Button) v.findViewById(R.id.decline_btn);

        final List<HashMap<String, String>> listItems = new ArrayList<>();

        DocumentReference docRef = db.collection("users")
                .document(getArguments().getString("email"));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    //if there is a user with the entered email
                    if (document.exists()) {
                        Log.d("TAG HELLPO", "Cached document data: " + document.getData());
                        Map<String, Object> data = document.getData();

                        reqs = (ArrayList<HashMap<String, Object>>) data.get("requests");
                        appts = (ArrayList<HashMap<String, Object>>) data.get("appointments");
                        Log.d("TAG LISTTT", "Cached document data: " + reqs.toString());

                        for (HashMap<String, Object> reqs : reqs
                        ) {
                            Timestamp stamp = (Timestamp) (reqs.get("date"));

                            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                            cal.setTimeInMillis(stamp.getSeconds() * 1000L);
                            String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();

                            Log.d("TAG DATEE", "Cached document data: " + date);

                            String[] dateArr = date.split(" ");

                            String name = (String) reqs.get("user");
                            map.put(dateArr[1], name);
                        }
                        done = true;

                        Log.d("TAG MAPPP", "Cached document data: " + map.toString());

                        final SimpleAdapter adapter = new SimpleAdapter(getContext(), listItems, R.layout.list_item,
                                new String[]{"First Line", "Second Line"}, new int[]{R.id.list_text1, R.id.list_text2});
                        Iterator it = map.entrySet().iterator();

                        while (it.hasNext()) {
                            HashMap<String, String> resultsMap = new HashMap<>();
                            Map.Entry pair = (Map.Entry) it.next();
                            resultsMap.put("First Line", pair.getValue().toString());
                            resultsMap.put("Second Line", pair.getKey().toString());
                            listItems.add(resultsMap);
                        }

                        //set click listener for accept button
                        accept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(listItems.isEmpty()) {
                                    Toast.makeText(getContext(), "No More Requests", Toast.LENGTH_LONG).show();
                                } else {
                                    //add top value to databse list of appointments

                                    //remove from database list of requests
                                    appts.add(reqs.get(0));
                                    reqs.remove(0);
                                    db.collection("users").document(getArguments().getString("email"))
                                            .update(
                                                    "requests", reqs,
                                                    "appointments", appts
                                            );

                                    //remove from listview and update adapter
                                    listItems.remove(0);
                                    adapter.notifyDataSetChanged();
                                    Log.d("TAG MAPPP New", "Cached document data: " + listItems.toString());

                                    //show toast for accepted
                                    Toast.makeText(getContext(), "Accepted Request", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        //set click listener for decline button
                        decline.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(listItems.isEmpty()) {
                                    Toast.makeText(getContext(), "No More Requests", Toast.LENGTH_LONG).show();
                                } else {

                                    reqs.remove(0);
                                    db.collection("users").document(getArguments().getString("email"))
                                            .update(
                                                    "requests", reqs
                                            );
                                    //remove top value from requests list

                                    //remove from listview and update adapter
                                    listItems.remove(0);
                                    adapter.notifyDataSetChanged();
                                    Log.d("TAG MAPPP New", "Cached document data: " + listItems.toString());

                                    //show toast for declined
                                    Toast.makeText(getContext(), "Declined Request", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        lv.setAdapter(adapter);
                    }
                } else {
                }
            }
        });

        return v;
    }
}
