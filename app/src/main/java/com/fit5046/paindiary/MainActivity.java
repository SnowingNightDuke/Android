package com.fit5046.paindiary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.fit5046.paindiary.databinding.ActivityMainBinding;
import com.fit5046.paindiary.entity.PainRecord;
import com.fit5046.paindiary.viewmodel.PainRecordViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    public String email;
    public List<PainRecord> syncData;
    private String uid;
    private FirebaseDatabase database;
    private PainRecordViewModel painRecordViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        syncTask();
        mAuth = FirebaseAuth.getInstance();

        binding.regButton.setOnClickListener(v -> {
            startActivity(new Intent(this, Register.class));
        });

        binding.loginButton.setOnClickListener(v -> {
            email = binding.emailBox.getText().toString().trim();
            String password = binding.passwordBox.getText().toString().trim();
            if (!email.equals("") && !password.equals("")) {
                login(email, password);
            } else if (email.equals("")) {
            binding.emailBox.setError("Please enter your email");
            } else if (password.equals("")) {
            binding.passwordBox.setError("Please enter your password");
            }
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
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    intent.putExtra("userEmail", email);
                    startActivity(intent);

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

    public void syncTask() {
        database = FirebaseDatabase.getInstance();
        painRecordViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()).create(PainRecordViewModel.class);
        painRecordViewModel.getAllPainRecords().observe(this, new Observer<List<PainRecord>>() {
            @Override
            public void onChanged(List<PainRecord> painRecords) {
                List<PainRecord> syncData = painRecords;
                for (PainRecord painRecord : painRecords) {
                    String painId = String.valueOf(painRecord.pid);
                    DatabaseReference reference = database.getReference(uid);
                    reference.child(painId).setValue(painRecord);
                }
            }
        });
    }
    public List<PainRecord> getSyncData() {
        return syncData;
    }

    public String getUID() {
        return uid;
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        uid = currentUser.getUid();
//        if (currentUser != null) {
//            mAuth.getCurrentUser().reload();
//        }
//    }
}