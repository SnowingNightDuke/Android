package com.fit5046.paindiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fit5046.paindiary.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        binding.regButton.setOnClickListener(v -> {
            startActivity(new Intent(this, Register.class));
        });

        binding.loginButton.setOnClickListener(v -> {
            String email = binding.emailBox.getText().toString().trim();
            String password = binding.passwordBox.getText().toString().trim();
            login(email, password);
        });
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Login with Email: Success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    startActivity(new Intent(MainActivity.this, Home.class));
                } else {
                    Log.w(TAG, "Login with Email: Failure", task.getException());
                    Toast.makeText(MainActivity.this, "Login with Email: Failure",Toast.LENGTH_LONG).show();
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            mAuth.getCurrentUser().reload();
        }
    }
}