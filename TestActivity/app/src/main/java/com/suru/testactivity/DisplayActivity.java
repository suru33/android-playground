package com.suru.testactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView numberTextView;
    private TextView locationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        nameTextView = findViewById(R.id.textView2);
        numberTextView = findViewById(R.id.textView4);
        locationTextView = findViewById(R.id.textView5);

        Intent intent = getIntent();
        nameTextView.setText(intent.getStringExtra("name"));
        numberTextView.setText(intent.getStringExtra("number"));
        locationTextView.setText("Location:\n\tlat=" + intent.getDoubleExtra("lat", 0)
                + "\n\tlng=" + intent.getDoubleExtra("lng", 0));


    }
}
