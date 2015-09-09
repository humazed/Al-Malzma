package com.example.huma.al_malzma.ui.subject_fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.helper.FabAnimationHelper;
import com.example.huma.al_malzma.model.LinkType;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LecturesFragment extends Fragment {
    public static final String TAG = LecturesFragment.class.getSimpleName();

    @Bind(R.id.empty_text_view) TextView mEmptyTextView;
    @Bind(R.id.subjects_list_view) ListView mSubjectsListView;
    @Bind(R.id.menu) FloatingActionMenu mMenu;
    @Bind(R.id.camera_fab) FloatingActionButton mCameraFab;
    @Bind(R.id.choose_image_fab) FloatingActionButton mChooseImageFab;
    @Bind(R.id.pdf_fab) FloatingActionButton mPdfFab;
    @Bind(R.id.link_fab) FloatingActionButton mLinkFab;


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

    @OnClick(R.id.camera_fab)
    void takePic() {
        LinkType linkType = new LinkType();
        linkType.setLink("google.com");
        linkType.saveToParse(getActivity());
    }

    @OnClick(R.id.choose_image_fab)
    void chooseImage() {

    }

    @OnClick(R.id.pdf_fab)
    void picPDF() {

    }

    @OnClick(R.id.link_fab)
    void addLink() {
        showInputDialog();
    }

    private void showInputDialog() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.lecture_link_dialog_input)
                .content(R.string.lecture_link_dialog_content)
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .input(R.string.lecture_link_dialog_hint, 0, false, new MaterialDialog.InputCallback() {

                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        String link = input.toString();
                        if (LinkType.validateLinkWithAlderDialog(link, getActivity()) != null) {
                            link = LinkType.validateLinkWithAlderDialog(link, getActivity());


                        }
                    }
                }).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
