package com.fit5046.paindiary.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.fit5046.paindiary.api.Weather;
import com.fit5046.paindiary.api.WeatherAPI;
import com.fit5046.paindiary.api.Main;
import com.fit5046.paindiary.databinding.HomeFragmentBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private HomeFragmentBinding homeBinding;
    private String apikey = "9624252aaae11d280ad030a092b155d5";
    public HomeFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeBinding = HomeFragmentBinding.inflate(inflater, container, false);
        View view = homeBinding.getRoot();
        getWeather();
        return view;
    }

    public void getWeather(){
        // this part can be optimized to use an edittext to gather the input from user and get the location instead of hard coding the location.
        String cityZip = "3008,au";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherAPI api = retrofit.create(WeatherAPI.class);
        Call<Weather> weatherCall = api.getWeather(cityZip, apikey);
        weatherCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (response.code() == 404){
                    Toast.makeText(getContext(), "Postcode not found", Toast.LENGTH_LONG).show();
                } else if (!(response.isSuccessful())) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();
                }
                Weather data = response.body();
                Main main = data.getMain();
                Double absTemp = main.getTemp();
                int temperature = (int)(absTemp - 273.15);
                int humidity = main.getHumidity();
                int pressure = main.getPressure();
                String weather = "Temperature: " + temperature + "°C\n"
                                + "Humidity: " + humidity + "%\n"
                                + "Pressure: " + pressure + "mb";

                homeBinding.weatherTextView.setText(weather);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("WEATHER_FILE", Context.MODE_PRIVATE);
                SharedPreferences.Editor spEditor = sharedPreferences.edit();
                spEditor.putInt("temperature", temperature);
                spEditor.putInt("humidity", humidity);
                spEditor.putInt("pressure", pressure);
                spEditor.apply();
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homeBinding = null;
    }

}
