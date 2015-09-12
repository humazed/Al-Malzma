package com.example.huma.al_malzma.ui.subject_fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.helper.FabAnimationHelper;
import com.example.huma.al_malzma.model.ImageType;
import com.example.huma.al_malzma.model.LinkType;
import com.example.huma.al_malzma.model.PdfType;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.rey.material.widget.EditText;

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
    @Bind(R.id.image_view) ImageView mImageView;

    EditText mLinkEditText;
    EditText mLinkDescriptionEditText;


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
        startActivityForResult(ImageType.getCapturePhotoIntent(getActivity()),
                ImageType.REQUEST_CAPTURE_PHOTO);
    }

    @OnClick(R.id.choose_image_fab)
    void chooseImage() {
        startActivityForResult(ImageType.getChoosePhotoIntent(), ImageType.REQUEST_CHOOSE_PHOTO);
    }

    @OnClick(R.id.pdf_fab)
    void picPDF() {

    }

    @OnClick(R.id.link_fab)
    void addLink() {
        showLinkDialog();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ImageType.REQUEST_CAPTURE_PHOTO:
                    ImageType.refreshGallery(getActivity());
                    break;
                case ImageType.REQUEST_CHOOSE_PHOTO:
                    Uri dir = data.getData();
                    Log.d(TAG, "onActivityResult " + dir);
                    //show it in Glide just to make sure Uri is correct then will upload it to parse.
                    Glide.with(this).load(dir).asBitmap().into(mImageView);
                    break;
                case PdfType.REQUEST_CHOOSE_PDF:
                    //do staff
                    break;
            }
        }
    }


    // TODO: 9/9/2015 it's very long method and will be use a lot so try to make dialogFragment and but them in.
    boolean linkFlag, descriptionFlag;

    private void showLinkDialog() {
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.important_link_dialog_title)
                .customView(R.layout.add_link_dialog, true)
                .positiveText(R.string.add)
                .negativeText(android.R.string.cancel)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        //save link and description to parse.
                        LinkType link = new LinkType();
                        link.setLink(mLinkEditText.getText().toString());
                        link.setDescription(mLinkDescriptionEditText.getText().toString());
                        link.saveToParse(getActivity());
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                    }
                }).build();

        final View positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        mLinkEditText = (EditText) dialog.findViewById(R.id.link_edit_text);
        mLinkDescriptionEditText = (EditText) dialog.findViewById(R.id.link_description_edit_text);

        //it's only faction is to enable and disable the input Button.
        mLinkEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //check the link.
                if (LinkType.validateLink(mLinkEditText.getText().toString()) == null) {
                    linkFlag = false;
                    mLinkEditText.setError(getActivity().getString(R.string.wrong_link_error_message));
                } else {
                    linkFlag = true;
                    mLinkEditText.setError(null);
                }

                //check if the link is correct and there is description.
                if (linkFlag && descriptionFlag) positiveAction.setEnabled(true);
                else positiveAction.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

        mLinkDescriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //check if the text field is Empty or not.
                descriptionFlag = (s.toString().trim().length() > 0);
                //check if the link is correct and there is description.
                if (linkFlag && descriptionFlag) positiveAction.setEnabled(true);
                else positiveAction.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        dialog.show();
        positiveAction.setEnabled(false); // disabled by default
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
