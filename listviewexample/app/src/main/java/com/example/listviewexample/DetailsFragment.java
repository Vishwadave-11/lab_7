package com.example.listviewexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        TextView nameTextView = view.findViewById(R.id.nameTextView);
        TextView heightTextView = view.findViewById(R.id.heightTextView);
        TextView massTextView = view.findViewById(R.id.massTextView);

        Bundle args = getArguments();
        if (args != null) {
            nameTextView.setText(args.getString("name"));
            heightTextView.setText(args.getString("height"));
            massTextView.setText(args.getString("mass"));
        }

        return view;
    }
}
