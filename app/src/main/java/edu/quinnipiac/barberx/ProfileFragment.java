package edu.quinnipiac.barberx;

/**
 * ProfileFragment shows the barber's profile filled with personal data saved to the database.
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
import android.widget.TextView;


public class ProfileFragment extends Fragment{

    TextView username;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(Bundle bundle) {
        Bundle bundle1 = bundle;
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(bundle1);
        return profileFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment

        username = v.findViewById(R.id.profileUsername);
        String email = getArguments().getString("email");
        if(email != null) { username.setText(email); }
        else{ System.out.println("Email is null");}

        return v;
    }
}
