package com.example.huma.al_malzma.ui.subject_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.helper.FabAnimationHelper;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LecturesFragment extends Fragment {


    @Bind(R.id.empty_text_view) TextView mEmptyTextView;
    @Bind(R.id.subjects_list_view) ListView mSubjectsListView;
    @Bind(R.id.link_fab) FloatingActionButton mFab1;
    @Bind(R.id.pdf_fab) FloatingActionButton mFab2;
    @Bind(R.id.choose_image_fab) FloatingActionButton mFab3;
    @Bind(R.id.menu) FloatingActionMenu mMenu;

    public LecturesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_lectures, container, false);
        ButterKnife.bind(this, rootView);

        mSubjectsListView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.weeks)));

        mSubjectsListView.setOnScrollListener(FabAnimationHelper.hideMenuOnScrollListener(mMenu));
        FabAnimationHelper.animMenu(getActivity(), mMenu);

        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
