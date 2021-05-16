package com.fit5046.paindiary.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fit5046.paindiary.R;
import com.fit5046.paindiary.databinding.MapFragmentBinding;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.io.IOException;
import java.util.List;

public class MapFragment extends Fragment {

    private MapFragmentBinding mapBinding;
    private String location = "Melbourne";
    private Geocoder geocoder;
    private final String TAG = "Map";
    public MapFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String token = getString(R.string.mapbox_access_token);
        Mapbox.getInstance(getContext(), token);
        mapBinding = MapFragmentBinding.inflate(inflater, container, false);
        View view = mapBinding.getRoot();
        mapBinding.floatingButton.setOnClickListener(v -> {
            requestAddress();
        });
        requestAddress();
        geocoder = new Geocoder(getContext());
        mapBinding.mapView.onCreate(savedInstanceState);
        mapBinding.mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        try {
                            List<Address> addresses = geocoder.getFromLocationName(location, 10);
                            Address address = addresses.get(0);
                            LatLng newLatLng = new LatLng(address.getLatitude(), address.getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions()
                                    .position(newLatLng)
                                    .title(address.getLocality());
                            mapboxMap.addMarker(markerOptions);
                            mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 13));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        return view;
    }

    private void requestAddress() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Enter your Address");
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        dialog.setView(input);

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                location = input.getText().toString();
                if (isAlpha(location)) {
                    mapBinding.mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                            mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                                @Override
                                public void onStyleLoaded(@NonNull Style style) {
                                    try {
                                        List<Address> addresses = geocoder.getFromLocationName(location, 10);
                                        Address address = addresses.get(0);
                                        LatLng newLatLng = new LatLng(address.getLatitude(), address.getLongitude());
                                        MarkerOptions markerOptions = new MarkerOptions()
                                                .position(newLatLng)
                                                .title(address.getLocality());
                                        mapboxMap.addMarker(markerOptions);
                                        mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 13));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                }
                else
                    Toast.makeText(getContext(), "Please check your address input and try again", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }

    @Override
    public void onStart() {
        super.onStart();
        mapBinding.mapView.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mapBinding.mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapBinding.mapView.onPause();
    }
    @Override
    public void onStop() {
        super.onStop();
        mapBinding.mapView.onStop();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapBinding.mapView.onSaveInstanceState(outState);
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapBinding.mapView.onLowMemory();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapBinding.mapView.onDestroy();
    }
}
