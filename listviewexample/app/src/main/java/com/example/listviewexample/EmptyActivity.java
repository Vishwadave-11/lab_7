package com.example.listviewexample;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class EmptyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details);

        String name = getIntent().getStringExtra("name");
        String height = getIntent().getStringExtra("height");
        String mass = getIntent().getStringExtra("mass");

        // Create an instance of DetailsFragment and pass the data using a Bundle
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("height", height);
        bundle.putString("mass", mass);
        detailsFragment.setArguments(bundle);

        // Use FragmentManager to replace the layout with the DetailsFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(android.R.id.content, detailsFragment) // Using the root layout of this activity
                .commit();
    }
}
