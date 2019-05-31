package com.suru.testactivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private EditText nameEditText;
    private EditText numberEditText;
    private LocationManager locationManager;
    private double lat;
    private double lng;
    // random int for location request
    private static final int LOCATION_REQUEST_CODE = 324321;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameEditText = findViewById(R.id.nameEditText);
        numberEditText = findViewById(R.id.numberEditText);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // checking the location permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, LOCATION_REQUEST_CODE);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    public void openNextActivity(View view) {
        String name = nameEditText.getText().toString();
        String number = numberEditText.getText().toString();

        if (name.isEmpty() || number.isEmpty()) {
            Log.e(getClass().getName(), "Empty name or number");
            Toast.makeText(getApplicationContext(), "Enter both name and number", Toast.LENGTH_SHORT);
        } else {
            Intent intent = new Intent(this, DisplayActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("number", number);
            intent.putExtra("lat", lat);
            intent.putExtra("lng", lng);

            // start new activity with intent data
            startActivity(intent);
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i(getClass().getName(), "Status changed. Provider: " + provider + ", Status: " + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(getClass().getName(), "Provider Enabled. Provider: " + provider);

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(getClass().getName(), "Provider Disabled. Provider: " + provider);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (LOCATION_REQUEST_CODE == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(getClass().getName(), "Location request granted");
            } else {
                lat = -1;
                lng = -1;
                Log.e(getClass().getName(), "Location request denied");
            }
        }
    }

    @Override
    protected void onResume() {
        // clear the text when user presses back button
        nameEditText.setText("");
        numberEditText.setText("");
        super.onResume();
    }
}
