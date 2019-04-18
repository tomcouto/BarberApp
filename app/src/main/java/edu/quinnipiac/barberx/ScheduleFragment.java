package edu.quinnipiac.barberx;

/**
 * ScheduleFragment will show a calender and when a date is selected, all appointments accepted
 * will be shown in a list view ordered by time showing data on the client and when they are coming.
 *
 * Version: 1.0
 * Authors: Tom Couto and Dominic Smorra
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class ScheduleFragment extends Fragment {

    Button requests;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        requests = (Button) getView().findViewById(R.id.requests);
//        requests.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fr = getSupportFragmentManager();
//                FragmentTransaction ft = fr.beginTransaction();
//                ft.replace(R.id.screen_area, new RequestFragment());
//                ft.commit();
//            }
//        });
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }
}
