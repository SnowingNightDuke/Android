package com.fit5046.paindiary;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {
    @GET("weather")
    Call<Weather> getWeather(@Query("zip") String zipcode,
                             @Query("appid") String apikey);
}
