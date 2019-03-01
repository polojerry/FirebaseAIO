package com.polotechnologies.firebaseaio.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.polotechnologies.firebaseaio.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirecastFragment extends Fragment {


    public FirecastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_firecast, container, false);
    }

}
