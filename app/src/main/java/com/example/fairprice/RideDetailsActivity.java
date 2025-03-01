package com.example.fairprice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;

public class RideDetailsActivity extends AppCompatActivity {

    private TextView rideName, rideDescription, ridePrice;
    private LottieAnimationView rideImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_details);

        rideName = findViewById(R.id.ride_name);
        rideDescription = findViewById(R.id.ride_description);
        rideImage = findViewById(R.id.ride_image);
        ImageButton backButton = findViewById(R.id.back_button);
        Button btn=findViewById(R.id.book);

        // Get Data from Intent
        Intent intent = getIntent();
        rideName.setText(intent.getStringExtra("ride_name"));
        rideDescription.setText(intent.getStringExtra("ride_description"));
// Set Lottie Animation
        String rideType = intent.getStringExtra("ride_name");

        if(rideType != null) {
            if (rideType.equals("Sedan")) {
                rideImage.setAnimation(R.raw.sedan);
            } else if (rideType.equals("SUV")) {
                rideImage.setAnimation(R.raw.suv);
            } else if (rideType.equals("Auto")) {
                rideImage.setAnimation(R.raw.auto);
            } else if (rideType.equals("Bike")) {
                rideImage.setAnimation(R.raw.bike);
            }
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(RideDetailsActivity.this,Comparision.class);
                intent.putExtra("ride_name", "Sedan");
                intent.putExtra("ride_name", "suv");
                intent.putExtra("ride_name", "auto");
                intent.putExtra("ride_name", "bike");
                intent.putExtra("ride_name", "carpool");
                startActivity(intent1);
            }
        });
        rideImage.playAnimation();  // Start animation
        rideImage.setRepeatCount(-1); // Loop animation
        rideImage.setScaleX(-1); // Flip horizontally
        // Back Button
        backButton.setOnClickListener(v -> finish());

    }
}
