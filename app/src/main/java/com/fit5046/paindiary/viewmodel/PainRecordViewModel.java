package com.fit5046.paindiary.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fit5046.paindiary.entity.PainRecord;
import com.fit5046.paindiary.repo.PainRecordRepo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PainRecordViewModel extends AndroidViewModel {
    private PainRecordRepo painRecordRepo;
    private LiveData<List<PainRecord>> allPainRecords;

    public PainRecordViewModel(Application application) {
        super(application);
        painRecordRepo = new PainRecordRepo(application);
        allPainRecords = painRecordRepo.getAllPainRecords();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<PainRecord> findByIDFuture(final int painID) {
        return painRecordRepo.findByIdFuture(painID);
    }

    public LiveData<List<PainRecord>> getAllPainRecords() {
        return allPainRecords;
    }

    public void insert(PainRecord painRecord) {
        painRecordRepo.insert(painRecord);
    }

    public void deleteAll() {
        painRecordRepo.deleteAll();
    }

    public void update(PainRecord painRecord) {
        painRecordRepo.updatePainRecord(painRecord);
    }
}
