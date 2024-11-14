package com.example.attandanceapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    // Declare EditText and Button
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    TextView createBtn, forgotBtn;
    FirebaseAuth auth;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  // Link to the login layout file
        auth = FirebaseAuth.getInstance();

//        FirebaseUser user = auth.getCurrentUser();
//
//        if(user!=null){
//            Intent myIntent = new Intent(login.this, MainActivity.class);
//            startActivity(myIntent);
//            finish();
//        }

        // Initialize UI elements
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        createBtn = findViewById(R.id.createAccountLink);
        forgotBtn = findViewById(R.id.forgotPasswordLink);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(login.this, signup.class);
                startActivity(myIntent);
            }
        });

        // Set OnClickListener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text from EditText fields
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Check if username or password is empty
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(login.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Perform login validation

                    SignIn(username,password);



            }
        });
    }

    private void SignIn(String username, String password) {

        auth.signInWithEmailAndPassword(username,password)
                        .addOnCompleteListener(this,task->{
                            if(task.isSuccessful()){
                                Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                // Redirect to another activity
                                Intent intent = new Intent(login.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(login.this, "Faield", Toast.LENGTH_SHORT).show();

                            }
                        });



    }


}
