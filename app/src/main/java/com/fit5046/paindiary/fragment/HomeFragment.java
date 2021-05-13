package com.fit5046.paindiary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fit5046.paindiary.Home;
import com.fit5046.paindiary.Weather;
import com.fit5046.paindiary.WeatherAPI;
import com.fit5046.paindiary.api.Main;
import com.fit5046.paindiary.databinding.HomeFragmentBinding;
import com.fit5046.paindiary.viewmodel.SharedViewModel;

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
        homeBinding.button.setOnClickListener(v -> {
            getWeather();
        });
        return view;
    }


    public void getWeather(){
        String cityZip = homeBinding.cityZipText.getText().toString().trim() + ",au";
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
                    Toast.makeText(getContext(), "Zipcode not found", Toast.LENGTH_LONG).show();
                } else if (!(response.isSuccessful())) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();
                }
                Weather data = response.body();
                Main main = data.getMain();
                Double absTemp = main.getTemp();
                int temp = (int)(absTemp - 273.15);
                homeBinding.weatherTextView.setText(temp + "°C");
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
