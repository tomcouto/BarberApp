package edu.quinnipiac.barberx;

/**
 * ScheduleFragment will show a calender and when a date is selected, all appointments accepted
 * will be shown in a list view ordered by time showing data on the client and when they are coming.
 *
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
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class ScheduleFragment extends Fragment {

    private static final String TAG = "CalendarFragment";

    private CalendarView mCalendarView;
    public ArrayList<Integer> dateList = new ArrayList<>();
    ListView lv;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<HashMap<String, Object>> appts;
    HashMap<String, String> map = new HashMap<>();
    Boolean done = false;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    public static ScheduleFragment newInstance(Bundle bundle) {
        Bundle bundle1 = bundle;
        ScheduleFragment scheduleFragment = new ScheduleFragment();
        scheduleFragment.setArguments(bundle1);
        return scheduleFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);

        final List<HashMap<String, String>> listItems = new ArrayList<>();
        mCalendarView = (CalendarView) v.findViewById(R.id.calendarView);
        lv = (ListView) v.findViewById(R.id.listview_lv);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                if(dateList.isEmpty()) {
                    dateList.add(month);
                    dateList.add(dayOfMonth);
                    dateList.add(year);
                } else {
                    dateList.clear();
                    dateList.add(month);
                    dateList.add(dayOfMonth);
                    dateList.add(year);
                }
                System.out.println(dateList.toString());
            }
        });

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

                        SimpleAdapter adapter = new SimpleAdapter(getContext(),listItems, R.layout.list_item,
                                new String[]{"First Line", "Second Line"}, new int[]{R.id.list_text1, R.id.list_text2});
                        Iterator it = map.entrySet().iterator();

                        while(it.hasNext()) {
                            HashMap<String, String> resultsMap = new HashMap<>();
                            Map.Entry pair = (Map.Entry) it.next();
                            resultsMap.put("First Line", pair.getValue().toString());
                            resultsMap.put("Second Line", pair.getKey().toString());
                            listItems.add(resultsMap);
                        }

                        lv.setAdapter(adapter);


                    }
                } else {
                }
            }
        });

        // Inflate the layout for this fragment
        return v;
    }
}
