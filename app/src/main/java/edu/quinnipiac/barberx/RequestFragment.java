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


public class RequestFragment extends Fragment {


    public RequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request, container, false);
    }
}
