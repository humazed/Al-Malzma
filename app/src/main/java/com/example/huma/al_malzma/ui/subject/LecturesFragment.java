package com.example.huma.al_malzma.ui.subject;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huma.al_malzma.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LecturesFragment extends Fragment {


    public LecturesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lectures, container, false);
    }


}
