package com.fit5046.paindiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.fit5046.paindiary.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private static final String TAG = Register.class.getSimpleName();
    private ActivityRegisterBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.progressBar2.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();

        binding.regButton.setOnClickListener(v -> {
            register();
        });
    }

    private void register(){
        if (binding.passwordBox.getText().toString().equals(binding.confirmBox.getText().toString())){
            String email = binding.emailBox.getText().toString().trim();
            String password = binding.passwordBox.getText().toString().trim();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(Register.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    binding.progressBar2.setVisibility(View.GONE);
                    updateUI(null);
                }

            });
        }
        else if (binding.passwordBox.getText().toString().length() < 6) {
            binding.passwordBox.setError("Password must have at least 6 characters!");
        }
        //TODO: if (!Patterns.EMAIL_ADDRESS.matcher(email))

        //TODO: be added some validation methods

        //TODO: password visibility configuration
    }

    public void updateUI(FirebaseUser user){
        if (user != null) {
            Toast.makeText(this, "Successfully Signed Up.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
            mAuth.getCurrentUser().reload();
    }
}
