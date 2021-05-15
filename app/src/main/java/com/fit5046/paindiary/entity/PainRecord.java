package com.fit5046.paindiary.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PainRecord {
    @PrimaryKey(autoGenerate = true)
    public int pid;

    @ColumnInfo(name = "pain_intensity_level")
    @NonNull
    public int painIntensityLevel;

    @ColumnInfo(name = "pain_location")
    @NonNull
    public String painLocation;

    @ColumnInfo(name = "mood_level")
    @NonNull
    public int moodLevel;

    @ColumnInfo(name = "steps_taken")
    @NonNull
    public int stepsTaken;

    @ColumnInfo(name = "date_of_entry")
    @NonNull
    public String dateOfEntry;

    @ColumnInfo(name = "temperature")
    @NonNull
    public int temperature;

    @ColumnInfo(name = "humidity")
    @NonNull
    public int humidity;

    @ColumnInfo(name = "pressure")
    @NonNull
    public int pressure;

    @ColumnInfo(name = "user_email")
    @NonNull
    public String userEmail;

    public PainRecord(@NonNull int pid, @NonNull int painIntensityLevel,
                      @NonNull String painLocation, @NonNull int moodLevel, @NonNull int stepsTaken,
                      @NonNull String dateOfEntry, @NonNull int temperature, @NonNull int humidity,
                      @NonNull int pressure, @NonNull String userEmail) {
        this.pid = pid;
        this.painIntensityLevel = painIntensityLevel;
        this.painLocation = painLocation;
        this.moodLevel = moodLevel;
        this.stepsTaken = stepsTaken;
        this.dateOfEntry = dateOfEntry;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.userEmail = userEmail;
    }
}
