package com.example.fairprice;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;

public class SelectMode extends AppCompatActivity {

    private LinearLayout rideContainer;
    private TextView pickupLocation, dropoffLocation;
    private HashMap<String, Integer> rideImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode);

        rideContainer = findViewById(R.id.ride_container);

        pickupLocation = findViewById(R.id.pickup_location);
        dropoffLocation = findViewById(R.id.drop_location);

        // ✅ Get Pickup & Drop-off locations from Intent
        Intent intent = getIntent();
        String pickup = intent.getStringExtra("pickup");
        String dropoff = intent.getStringExtra("destination");

        if (pickup != null && dropoff != null) {
            pickupLocation.setText(pickup);
            dropoffLocation.setText(dropoff);
        }

        // ✅ Fix: Initialize Ride Images
        rideImages = new HashMap<>();
        rideImages.put("Sedan", R.drawable.sedan);
        rideImages.put("Bike", R.drawable.bike);
        rideImages.put("Auto", R.drawable.auto);
        rideImages.put("SUV", R.drawable.suv);
        rideImages.put("Car Pool", R.drawable.pool);

        // ✅ Add Ride Options
        addRideOption("Sedan", "Comfortable 4-seater", "Available");
        addRideOption("Bike", "Quick and affordable", "Available");
        addRideOption("Auto", "Budget-friendly ride", "Available");
        addRideOption("SUV", "Spacious for 6 people", "Available");
        addRideOption("Car Pool", "Shared ride, low cost", "Available");
    }

    private void addRideOption(String name, String description, String availability) {
        if (rideContainer == null) {
            return; // Prevents crash if rideContainer is null
        }

        // ✅ Inflate Ride Item Layout
        View rideView = LayoutInflater.from(this).inflate(R.layout.ride_option_item, rideContainer, false);

        // ✅ Find Views Inside Ride Option Layout
        TextView rideName = rideView.findViewById(R.id.ride_name);
        TextView rideDescription = rideView.findViewById(R.id.ride_description);
        TextView rideAvailability = rideView.findViewById(R.id.ride_availability);
        ImageView rideImage = rideView.findViewById(R.id.ride_image);

        // ✅ Set Values
        rideName.setText(name);
        rideDescription.setText(description);
        rideAvailability.setText(availability);

        if (rideImages.containsKey(name)) {
            rideImage.setImageResource(rideImages.get(name));
        }
        rideView.setOnClickListener(v -> {
            if(availability.equals("Available")) {
                Intent intent = new Intent(SelectMode.this, RideDetailsActivity.class);
                intent.putExtra("ride_name", name);
                intent.putExtra("ride_description", description);
                intent.putExtra("ride_image", rideImages.get(name));
                startActivity(intent);
            }else{
                Toast.makeText(SelectMode.this,"This is not available",Toast.LENGTH_SHORT).show();
            }
        });

        // ✅ Add Ride Option to Layout
        rideContainer.addView(rideView);
    }
}
