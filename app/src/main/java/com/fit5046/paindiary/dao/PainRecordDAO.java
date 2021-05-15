package com.fit5046.paindiary.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fit5046.paindiary.entity.PainRecord;

import java.util.List;

@Dao
public interface PainRecordDAO {
    @Query("SELECT * FROM painrecord ORDER BY pid ASC")
    LiveData<List<PainRecord>> getAll();

    @Query("SELECT * FROM painrecord WHERE pid = :painID LIMIT 1")
    PainRecord findByID(int painID);

    @Insert
    void insert(PainRecord painRecord);

    @Delete
    void delete(PainRecord painRecord);

    @Update
    void updatePainRecord(PainRecord painRecord);

    @Query("DELETE FROM painrecord")
    void deleteAll();
}
