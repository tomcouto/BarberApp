package edu.quinnipiac.barberx;

/**
 * RequestFragment is where the user will see all requests to be put on their schedule shown
 * in a recycler view. Once there is requests the recycler view the user can accept/deny them.
 * If accepted the request will be added to the schedule and deleted. If declined it will be deleted.
 *
 * Version: 1.0
 * Authors: Tom Couto and Dominic Smorra
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;


public class RequestFragment extends Fragment {

    ListView lv;
    AccountHandler handler = new AccountHandler();
    Button accept;
    Button decline;

    public RequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_request, container, false);
        lv = (ListView) v.findViewById(R.id.listview_r);

        List<HashMap<String, String>> listItems = new ArrayList<>();

        SimpleAdapter adapter = new SimpleAdapter(this.getContext(),listItems, R.layout.rlist_item,
                new String[]{"First Line", "Second Line"}, new int[]{R.id.rlist_text1, R.id.rlist_text2});
        Iterator it = handler.fakeRequests.entrySet().iterator();

        while(it.hasNext()) {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            resultsMap.put("First Line", pair.getValue().toString());
            resultsMap.put("Second Line", pair.getKey().toString());
            listItems.add(resultsMap);
        }

//        accept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "Accepted Request", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        decline.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(),"Declined Request", Toast.LENGTH_LONG).show();
//            }
//        });

        lv.setAdapter(adapter);
        return v;
    }
}
