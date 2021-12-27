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
    public int painIntensityLevel;

    @ColumnInfo(name = "pain_location")
    @NonNull
    public String painLocation;

    @ColumnInfo(name = "mood_level")
    @NonNull
    public String moodLevel;

    @ColumnInfo(name = "steps_goal")
    public int stepsGoal;

    @ColumnInfo(name = "steps_taken")
    public int stepsTaken;

    @ColumnInfo(name = "date_of_entry")
    @NonNull
    public String dateOfEntry;

    @ColumnInfo(name = "temperature")
    public int temperature;

    @ColumnInfo(name = "humidity")
    public int humidity;

    @ColumnInfo(name = "pressure")
    public int pressure;

    @ColumnInfo(name = "user_email")
    @NonNull
    public String userEmail;

    public PainRecord(int painIntensityLevel,
                      @NonNull String painLocation, @NonNull String moodLevel, int stepsGoal, int stepsTaken,
                      @NonNull String dateOfEntry, int temperature, int humidity,
                      int pressure, @NonNull String userEmail) {
        this.painIntensityLevel = painIntensityLevel;
        this.painLocation = painLocation;
        this.moodLevel = moodLevel;
        this.stepsGoal = stepsGoal;
        this.stepsTaken = stepsTaken;
        this.dateOfEntry = dateOfEntry;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.userEmail = userEmail;
    }
}
