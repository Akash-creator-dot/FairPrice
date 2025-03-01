package com.example.fairprice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;

public class Comparision extends AppCompatActivity {

    private TextView rideName, rideDescription;
    private LottieAnimationView rideImage;
    private TextView olaPrice, olaTime, olaRating;
    private TextView uberPrice, uberTime, uberRating;
    private TextView rapidoPrice, rapidoTime, rapidoRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_details);

        // Find views
        rideName = findViewById(R.id.ride_name);
        rideDescription = findViewById(R.id.ride_description);
        rideImage = findViewById(R.id.ride_image);

        olaPrice = findViewById(R.id.ola_price);
        olaTime = findViewById(R.id.ola_time);
        olaRating = findViewById(R.id.ola_rating);

        uberPrice = findViewById(R.id.uber_price);
        uberTime = findViewById(R.id.uber_time);
        uberRating = findViewById(R.id.uber_rating);

        rapidoPrice = findViewById(R.id.rapido_price);
        rapidoTime = findViewById(R.id.rapido_time);
        rapidoRating = findViewById(R.id.rapido_rating);

        Button backButton = findViewById(R.id.back_button);

        // Get ride details from Intent
        Intent intent = getIntent();
        String rideType = intent.getStringExtra("ride_name");
        rideName.setText(rideType);
//        rideDescription.setText(intent.getStringExtra("ride_description"));

        // Set Lottie animation
        if (rideType.equals("Sedan")) {
            rideImage.setAnimation(R.raw.sedan);
        } else if (rideType.equals("suv")) {
            rideImage.setAnimation(R.raw.suv);
        } else if (rideType.equals("auto")) {
            rideImage.setAnimation(R.raw.auto);
        }else{
            rideImage.setAnimation(R.raw.bike);
        }
        rideImage.playAnimation();
        rideImage.setRepeatCount(-1);
        backButton.setOnClickListener(v -> finish());
    }
}
