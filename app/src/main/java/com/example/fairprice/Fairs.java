package com.example.fairprice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class Fairs extends Fragment {

    private static final int AUTOCOMPLETE_PICKUP_REQUEST = 1;
    private static final int AUTOCOMPLETE_DESTINATION_REQUEST = 2;

    private EditText pickupLocation, destination;
    private MaterialButton findFaresButton;

    public Fairs() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fairs, container, false);

        // Initialize Places API
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), "AIzaSyB29I5knYAO6Sb-AWY06mRygi8VVutduB8");
        }

        // Initialize UI elements
        pickupLocation = view.findViewById(R.id.pickup_location);
        destination = view.findViewById(R.id.destination);
        findFaresButton = view.findViewById(R.id.find_fares_button);

        // Set click listeners to open Places Autocomplete
        pickupLocation.setOnClickListener(v -> launchAutocomplete(AUTOCOMPLETE_PICKUP_REQUEST));
        destination.setOnClickListener(v -> launchAutocomplete(AUTOCOMPLETE_DESTINATION_REQUEST));

        // Button click listener
        findFaresButton.setOnClickListener(v -> {
            String pickup = pickupLocation.getText().toString().trim();
            String dest = destination.getText().toString().trim();

            if (TextUtils.isEmpty(pickup) || TextUtils.isEmpty(dest)) {
                Toast.makeText(getActivity(), "Please enter both locations", Toast.LENGTH_SHORT).show();
            } else {
                // Navigate to SelectMode activity with pickup and destination details
                Intent intent = new Intent(getActivity(), SelectMode.class);
                intent.putExtra("pickup", pickup);
                intent.putExtra("destination", dest);
                startActivity(intent);
            }
        });

        return view;
    }

    // Method to launch Google Places Autocomplete
    private void launchAutocomplete(int requestCode) {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .setCountry("IN") // Restrict to India (Optional)
                .build(requireContext());
        startActivityForResult(intent, requestCode);
    }

    // Handle the result from Places Autocomplete
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            if (requestCode == AUTOCOMPLETE_PICKUP_REQUEST) {
                pickupLocation.setText(place.getName());
            } else if (requestCode == AUTOCOMPLETE_DESTINATION_REQUEST) {
                destination.setText(place.getName());
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(getActivity(), "Location selection canceled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Error retrieving location", Toast.LENGTH_SHORT).show();
        }
    }
}
