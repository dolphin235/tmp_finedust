package com.example.tmp_finedust;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button location_btn;
    private Button dust_btn;
    private TextView location_textview;
    private TextView dust_textview;

    private MyLocationFinder locationFinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location_btn = findViewById(R.id.button_loc);
        location_textview = findViewById(R.id.textView_loc);

        dust_btn = findViewById(R.id.button_dust);
        dust_textview = findViewById(R.id.textView_dust);

        location_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationFinder = new MyLocationFinder(MainActivity.this);
                double latitude = locationFinder.getLatitude();
                double longitude = locationFinder.getLongitude();

                String address = getCurrentAddress(latitude, longitude);
                location_textview.setText(address);

                Toast.makeText(MainActivity.this, "위도: "+latitude+
                        "\n경도: "+longitude, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCurrentAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(latitude, longitude,5);
        } catch(IOException ioException){
            String errMsg = "IO error 발생";
            return errMsg;
        } catch(IllegalArgumentException illegalArgumentException) {
            String errMsg = "잘못된 GPS 좌표";
            return errMsg;
        }

        if (addresses == null || addresses.size() == 0){
            String errMsg = "주소 미발견";
            return errMsg;
        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString() + "\n";
    }
}