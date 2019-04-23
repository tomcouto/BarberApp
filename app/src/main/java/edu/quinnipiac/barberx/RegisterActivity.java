package edu.quinnipiac.barberx;

/**
 * RegisterActivity allows the user to create a new user profile that is saved on the database used
 * for later login.
 *
 * Version: 1.0
 * Authors: Tom Couto and Dominic Smorra
 */

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button registerButton;
    EditText emailText;
    EditText passwordText;
    EditText firstNameText;
    EditText lastNameText;
    EditText usernameText;
    EditText confirmPasswordText;
    EditText addressText;
    FirebaseFirestore db = FirebaseFirestore.getInstance();



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

        registerButton = (Button) findViewById(R.id.register_button);
        emailText = (EditText)findViewById(R.id.email);
        passwordText = (EditText)findViewById(R.id.password);
        firstNameText = (EditText)findViewById(R.id.first_name);
        lastNameText = (EditText)findViewById(R.id.last_name);
        usernameText = (EditText)findViewById(R.id.username);
        confirmPasswordText = (EditText)findViewById(R.id.confirm_password);
        addressText = (EditText)findViewById(R.id.address);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", passwordText.toString());
                Log.d("TAG", confirmPasswordText.toString());
                if(passwordText.getText().toString().equals(confirmPasswordText.getText().toString())) {

                    Map<String, Object> barber = new HashMap<>();
                    barber.put("firstName", firstNameText.getText().toString());
                    barber.put("lastName", lastNameText.getText().toString());
                    barber.put("email", emailText.getText().toString());
                    barber.put("username", usernameText.getText().toString());
                    barber.put("address", addressText.getText().toString());


                    db.collection("users")
                            .add(barber)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                    Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(registerIntent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("TAG", "Error adding document", e);
                                }
                            });

                } else {
                    Toast.makeText(RegisterActivity.this,"Passwords don't match", Toast.LENGTH_LONG).show();
                }


            }
        });
    }


}
