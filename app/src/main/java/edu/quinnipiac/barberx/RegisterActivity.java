package edu.quinnipiac.barberx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /**
         *     REGISTER BUTTON
         *
         *     check that passwords match
         *
         *     check if email is in use
         *
         *     add user to database
         *
         *     return to login screen
         */

        registerButton = (Button)findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(registerIntent);
            }
        });
    }


}
