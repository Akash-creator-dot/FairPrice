package com.example.fairprice;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;

public class Fairs extends Fragment {

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

        pickupLocation = view.findViewById(R.id.pickup_location);
        destination = view.findViewById(R.id.destination);
        findFaresButton = view.findViewById(R.id.find_fares_button);

        findFaresButton.setOnClickListener(v -> {
            String pickup = pickupLocation.getText().toString().trim();
            String dest = destination.getText().toString().trim();

            if (pickup.isEmpty() || dest.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter both locations", Toast.LENGTH_SHORT).show();
            } else {
                // Open Partners activity with pickup and destination
                Intent intent = new Intent(getActivity(), Partners.class);
                intent.putExtra("pickup", pickup);
                intent.putExtra("destination", dest);
                startActivity(intent);
            }
        });

        return view;
    }
}
