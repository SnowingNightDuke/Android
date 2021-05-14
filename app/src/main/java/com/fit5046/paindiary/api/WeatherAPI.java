package com.fit5046.paindiary.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {
    @GET("weather?&appid=9624252aaae11d280ad030a092b155d5")
    Call<Weather> getWeather(@Query("zip") String cityZip,
                             @Query("appid") String apikey);
}
