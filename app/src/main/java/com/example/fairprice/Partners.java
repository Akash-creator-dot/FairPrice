package com.example.fairprice;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Partners extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners);

        // Retrieve passed data from Fairs activity
        String pickup = getIntent().getStringExtra("pickup");
        String destination = getIntent().getStringExtra("destination");

        TextView pickupView = findViewById(R.id.pickup_location_text);
        TextView destinationView = findViewById(R.id.destination_location_text);

        if (pickup != null && destination != null) {
            pickupView.setText("Pickup Location: " + pickup);
            destinationView.setText("Destination: " + destination);
        }
    }
}
