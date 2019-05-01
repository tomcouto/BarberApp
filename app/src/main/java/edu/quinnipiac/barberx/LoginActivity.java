package edu.quinnipiac.barberx;

/**
 * LoginActivity is where a barber will login to the application using their email address and password
 * and the information entered will then be checked with the database to make sure a user with those credentials does in fact exist.
 * If the user does exist, they will then be sent to the MainActivity. If not, a toast will appear alerting the user that they entered
 * invalid information.
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText emailText;
    EditText passwordText;
    Button loginButton;
    Button registerButton;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = (EditText)findViewById(R.id.email);
        passwordText = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.login_button);
        registerButton = (Button)findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailText.getText().toString();
                final String password = passwordText.getText().toString();


                DocumentReference docRef = db.collection("users")
                        .document(email);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            //if there is a user with the entered email
                            if (document.exists()) {
                                Map<String, Object> data = document.getData();
                                //if the password is correct
                                if(data.containsValue(password)){
                                    AccountHandler handler = new AccountHandler();
                                    handler.setEmail(email);
                                    Log.d("TAG HELLO", "Cached document data: " + document.getData());
                                    Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                                    loginIntent.putExtra("email", email);
                                    startActivity(loginIntent);
                                } else {
                                    Toast.makeText(LoginActivity.this,"Username or Password Invalid.", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this,"Username or Password Invalid.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                        }
                    }
                });


            }
        });

    }
}
