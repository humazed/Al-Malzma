package com.example.huma.al_malzma.ui.subject_fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.adapters.LSAdapter;
import com.example.huma.al_malzma.helper.FabAnimationHelper;
import com.example.huma.al_malzma.helper.Utility;
import com.example.huma.al_malzma.model.BaseDataItem;
import com.example.huma.al_malzma.model.ImageType;
import com.example.huma.al_malzma.model.LinkType;
import com.example.huma.al_malzma.model.PdfType;
import com.example.huma.al_malzma.parse.ParseConstants;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SectionsFragment extends Fragment {
    public static final String TAG = SectionsFragment.class.getSimpleName();

    @Bind(R.id.menu) FloatingActionMenu mMenu;
    @Bind(R.id.camera_fab) FloatingActionButton mCameraFab;
    @Bind(R.id.choose_image_fab) FloatingActionButton mChooseImageFab;
    @Bind(R.id.pdf_fab) FloatingActionButton mPdfFab;
    @Bind(R.id.link_fab) FloatingActionButton mLinkFab;
    @Bind(R.id.sections_recyclerView) RecyclerView mSecrionsRecyclerView;
    @Bind(R.id.empty_loading_text_view) TextView mEmptyLoadingTextView;

    ImageType image;
    PdfType mPdfData;

    private List<LinkType> mLinks;
    private List<PdfType> mPDFs;
    private List<ImageType> mImages;

    private boolean linkFlag = false;
    private boolean imageFlag = false;
    private boolean pdfFlag = false;


    public SectionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sections, container, false);
        ButterKnife.bind(this, rootView);

        mSecrionsRecyclerView.addOnScrollListener(FabAnimationHelper.hideMenuOnRecyclerScrollListener(mMenu));
        FabAnimationHelper.animMenu(getActivity(), mMenu);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mLinks = null;
        mPDFs = null;
        mImages = null;

        if (Utility.isNetworkAvailableWithToast(getContext()))
            refreshList();
        else mEmptyLoadingTextView.setText(R.string.no_internet_connection);

    }

    private void refreshList() {
        Log.d(TAG, "refreshList ");
        ParseQuery<LinkType> linkQuery = ParseQuery.getQuery(ParseConstants.CLASS_LINK);
        ParseQuery<PdfType> pdfQuery = ParseQuery.getQuery(ParseConstants.CLASS_PDF);
        ParseQuery<ImageType> ImageQuery = ParseQuery.getQuery(ParseConstants.CLASS_IMAGE);

        BaseDataItem.setQueryCurrentConstrains(ParseConstants.KEY_SECTIONS,
                linkQuery, pdfQuery, ImageQuery);

        //List is fill now.
        linkQuery.findInBackground(new FindCallback<LinkType>() {
            @Override
            public void done(List<LinkType> links, ParseException e) {
                if (e == null) {
                    mLinks = links;
                    linkFlag = true;
                    fillFinally();

                    Log.d(TAG, "done " + links.toString());
                } else {
                    Log.d("linkQuery Error: ", e.getMessage());
                }
            }
        });
        pdfQuery.findInBackground(new FindCallback<PdfType>() {
            @Override
            public void done(List<PdfType> pdfs, ParseException e) {
                if (e == null) {
                    mPDFs = pdfs;
                    pdfFlag = true;
                    fillFinally();

                    Log.d(TAG, "done " + pdfs.toString());
                } else {
                    Log.d("pdfQuery Error: ", e.getMessage());
                }
            }
        });

        ImageQuery.findInBackground(new FindCallback<ImageType>() {
            @Override
            public void done(List<ImageType> images, ParseException e) {
                if (e == null) {
                    mImages = images;
                    imageFlag = true;
                    fillFinally();

                    Log.d(TAG, "done " + images.toString());
                } else {
                    Log.d("ImageQuery Error", e.getMessage());
                }
            }
        });
    }

    //fill the recyclerView when all three findInBackground finished.
    private void fillFinally() {
        Log.d(TAG, "fillFinally " + "loading");
        if (linkFlag && imageFlag && pdfFlag) {
            if (hasData()) {
                mEmptyLoadingTextView.setVisibility(View.GONE);
                Log.d(TAG, "hasData " + false);
                LSAdapter adapter = new LSAdapter(getActivity(), mPDFs, mImages, mLinks);
                mSecrionsRecyclerView.setAdapter(adapter);
                mSecrionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            } else {
                mEmptyLoadingTextView.setText(R.string.empty_sections_message);
            }
        }
    }

    @OnClick(R.id.camera_fab)
    void takePic() {
        image = new ImageType(getActivity(), ImageType.REQUEST_CAPTURE_PHOTO, ParseConstants.KEY_SECTIONS);
        startActivityForResult(image.getActionIntent(), ImageType.REQUEST_CAPTURE_PHOTO);
    }

    @OnClick(R.id.choose_image_fab)
    void chooseImage() {
        image = new ImageType(getActivity(), ImageType.REQUEST_CHOOSE_PHOTO, ParseConstants.KEY_SECTIONS);
        startActivityForResult(image.getActionIntent(), ImageType.REQUEST_CHOOSE_PHOTO);
    }

    @OnClick(R.id.pdf_fab)
    void picPDF() {
        mPdfData = new PdfType(getActivity(), ParseConstants.KEY_SECTIONS);
        startActivityForResult(Intent.createChooser(PdfType.getPicPdfIntent(), "Open file"),
                PdfType.REQUEST_CHOOSE_PDF);
    }

    @OnClick(R.id.link_fab)
    void addLink() {
        LinkType.showLinkDialog(getActivity(), ParseConstants.KEY_SECTIONS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri dir;
            switch (requestCode) {
                case ImageType.REQUEST_CAPTURE_PHOTO:
                    dir = image.getImageUri();
                    image.setImage(dir);
                    ImageType.showImageDescriptionDialog(getActivity(), image);

                    image.refreshGallery(getActivity());
                    break;
                case ImageType.REQUEST_CHOOSE_PHOTO:
                    dir = data.getData();
                    image.setImage(dir);

                    ImageType.showImageDescriptionDialog(getActivity(), image);

                    Log.d(TAG, "onActivityResult " + dir);
                    break;
                case PdfType.REQUEST_CHOOSE_PDF:
                    dir = data.getData();
                    mPdfData.setPDF(dir);

                    PdfType.showPdfDescriptionDialog(getActivity(), mPdfData);

                    Log.d(TAG, "onActivityResult " + dir);
                    break;
            }
        }
    }


    private boolean hasData() {
        return mPDFs != null && mImages != null && mLinks != null
                && (!(mPDFs.isEmpty()) || !(mImages.isEmpty()) || !(mLinks.isEmpty()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
