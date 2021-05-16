package com.fit5046.paindiary.api;

import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("main")
    private Main main;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
