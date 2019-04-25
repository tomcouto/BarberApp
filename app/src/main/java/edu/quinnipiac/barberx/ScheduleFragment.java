package edu.quinnipiac.barberx;

/**
 * ScheduleFragment will show a calender and when a date is selected, all appointments accepted
 * will be shown in a list view ordered by time showing data on the client and when they are coming.
 *
 * Version: 1.0
 * Authors: Tom Couto and Dominic Smorra
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ScheduleFragment extends Fragment {

    private static final String TAG = "CalendarFragment";

    private CalendarView mCalendarView;
    public ArrayList<Integer> dateList = new ArrayList<>();
    ListView lv;
    AccountHandler handler = new AccountHandler();

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);

        List<HashMap<String, String>> listItems = new ArrayList<>();
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

        SimpleAdapter adapter = new SimpleAdapter(this.getContext(),listItems, R.layout.list_item,
                new String[]{"First Line", "Second Line"}, new int[]{R.id.list_text1, R.id.list_text2});
        Iterator it = handler.fakeMap.entrySet().iterator();

        while(it.hasNext()) {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }

        lv.setAdapter(adapter);
        System.out.println("map 2" + handler.map);
        // Inflate the layout for this fragment
        return v;
    }
}
