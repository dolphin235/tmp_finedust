package com.example.tmp_finedust;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button location_btn;
    private Button dust_btn;
    private TextView location_textview;
    private TextView dust_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location_btn = findViewById(R.id.button_loc);
        location_textview = findViewById(R.id.textView_loc);

        dust_btn = findViewById(R.id.button_dust);
        dust_textview = findViewById(R.id.textView_dust);

    }
}