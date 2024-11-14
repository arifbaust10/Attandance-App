package com.example.attandanceapp;



import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPass;
    private Button buttonSignUp;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth = FirebaseAuth.getInstance();

        // Initialize the views
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPass = findViewById(R.id.editTextPass);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        // Set click listener for Sign Up button
        buttonSignUp.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String pass = editTextPass.getText().toString().trim();

            // Validate the input
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                Toast.makeText(signup.this, "All fields are required", Toast.LENGTH_SHORT).show();
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(signup.this, "Invalid email address", Toast.LENGTH_SHORT).show();
            } else if (pass.length() < 8) {
                Toast.makeText(signup.this, "Invalid pass number", Toast.LENGTH_SHORT).show();
            } else {

           SignUp(email,pass);


            }
        });
    }

    private void SignUp(String email, String pass) {
        auth.createUserWithEmailAndPassword(email,pass).
                addOnCompleteListener(this,task->{
                    if(task.isSuccessful()){
                        Toast.makeText(signup.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(signup.this, login.class);
                        startActivity(myIntent);
                        finish();
                    }else{
                        Toast.makeText(signup.this, "Faield", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
