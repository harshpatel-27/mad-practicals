package com.example.locationapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 101;
    private FusedLocationProviderClient fusedLocationClient;
    private TextView locationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationText = findViewById(R.id.locationText);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();

                    // Default lat/lon display
                    String locText = getString(R.string.location_text, lat, lon);

                    // Now get address
                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
                        if (addresses != null && !addresses.isEmpty()) {
                            Address address = addresses.get(0);
                            String fullAddress = address.getAddressLine(0);
                            locText += "\nAddress: " + fullAddress;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Unable to get address", Toast.LENGTH_SHORT).show();
                    }

                    locationText.setText(locText);

                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.location_not_found), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        } else {
            Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
        }
    }
}