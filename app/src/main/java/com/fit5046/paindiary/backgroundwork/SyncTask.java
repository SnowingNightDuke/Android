package com.fit5046.paindiary.backgroundwork;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.fit5046.paindiary.MainActivity;
import com.fit5046.paindiary.entity.PainRecord;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SyncTask extends Worker {
    private static final String TAG = "SyncTask";
    private FirebaseDatabase database;

    public SyncTask(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        MainActivity mainActivity = new MainActivity();
        String uid = mainActivity.getUID();
        database = FirebaseDatabase.getInstance();
        List<PainRecord> painRecords = mainActivity.getSyncData();
        for (PainRecord painRecord : painRecords) {
            String painId = String.valueOf(painRecord.pid);
            DatabaseReference reference = database.getReference(uid);
            reference.child(painId).setValue(painRecord);
        }
        Log.v(TAG, "SyncTask: Sync Done");
        return Result.success();
    }
}



