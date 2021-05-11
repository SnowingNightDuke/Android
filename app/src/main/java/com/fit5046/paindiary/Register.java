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
            binding.progressBar2.setVisibility(View.VISIBLE);
            String email = binding.emailBox.getText().toString().trim();
            String password = binding.passwordBox.getText().toString().trim();
            register(email, password);
        });
    }

    private void register(String email, String password){
        if (binding.confirmBox.getText().toString().equals(password)){
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(Register.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    binding.progressBar2.setVisibility(View.GONE);
                    updateUI(null);
                }
            });
        }
        else if (password.length() < 6) {
            binding.passwordBox.setError("Password must have at least 6 characters!");
        } else {
            binding.confirmBox.setError("Incompatible Password");
        }

        //TODO: if (!Patterns.EMAIL_ADDRESS.matcher(email))

        //TODO: be added some validation methods (considering if user input includes white spaces)

        //TODO: password visibility configuration (As above)
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
