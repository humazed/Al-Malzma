package com.example.huma.al_malzma.ui.subject_fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.helper.FabAnimationHelper;
import com.example.huma.al_malzma.model.DataItem;
import com.example.huma.al_malzma.model.ImageType;
import com.example.huma.al_malzma.model.LinkType;
import com.example.huma.al_malzma.model.PdfType;
import com.example.huma.al_malzma.parse.ParseConstants;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

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


    ImageType image;
    //    PdfType mPdfData;
    PdfType mPdfData;


    List<LinkType> mLinks;
    List<PdfType> mPDFs;
    List<ImageType> mImages;

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
    public void onResume() {
        super.onResume();

        refreshList();
    }


    private void refreshList() {
        Log.d(TAG, "refreshList ");
        ParseQuery<LinkType> linkQuery = ParseQuery.getQuery(ParseConstants.CLASS_LINK);
        ParseQuery<PdfType> pdfQuery = ParseQuery.getQuery(ParseConstants.CLASS_PDF);
        ParseQuery<ImageType> ImageQuery = ParseQuery.getQuery(ParseConstants.CLASS_IMAGE);

        setLinkCurrentConstrains(linkQuery);
        setPdfCurrentConstrains(pdfQuery);
        setImageCurrentConstrains(ImageQuery);

        //List is fill now.
        linkQuery.findInBackground(new FindCallback<LinkType>() {
            public void done(List<LinkType> links, ParseException e) {
                if (e == null) {
                    mLinks = links;
                    ArrayList<String> descriptions = new ArrayList<>();
                    for (LinkType link : links) descriptions.add(link.getDescription());

                    mSubjectsListView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                            descriptions));

                    Log.d(TAG, "done " + links.toString());
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
        pdfQuery.findInBackground(new FindCallback<PdfType>() {
            public void done(List<PdfType> pdfs, ParseException e) {
                if (e == null) {
                    mPDFs = pdfs;
                    Log.d(TAG, "done " + pdfs.toString());
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

        ImageQuery.findInBackground(new FindCallback<ImageType>() {
            public void done(List<ImageType> images, ParseException e) {
                if (e == null) {
                    mImages = images;
                    Log.d(TAG, "done " + images.toString());

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });


        if (mLinks == null) {
            Log.d(TAG, "refreshList " + "null");
        }


//        mSubjectsListView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
//                getResources().getStringArray(R.array.weeks)));

    }


    @OnClick(R.id.camera_fab)
    void takePic() {
        image = new ImageType(getActivity(), ImageType.REQUEST_CAPTURE_PHOTO, ParseConstants.KEY_LECTURES);
        startActivityForResult(image.getActionIntent(), ImageType.REQUEST_CAPTURE_PHOTO);
    }

    @OnClick(R.id.choose_image_fab)
    void chooseImage() {
        image = new ImageType(getActivity(), ImageType.REQUEST_CHOOSE_PHOTO, ParseConstants.KEY_LECTURES);
        startActivityForResult(image.getActionIntent(), ImageType.REQUEST_CHOOSE_PHOTO);
    }

    @OnClick(R.id.pdf_fab)
    void picPDF() {
        mPdfData = new PdfType(getActivity(), ParseConstants.KEY_LECTURES);
        startActivityForResult(Intent.createChooser(PdfType.getPicPdfIntent(), "Open file"),
                PdfType.REQUEST_CHOOSE_PDF);
    }

    @OnClick(R.id.link_fab)
    void addLink() {
        LinkType.showLinkDialog(getActivity(), ParseConstants.KEY_LECTURES);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri dir;
            switch (requestCode) {
                case ImageType.REQUEST_CAPTURE_PHOTO:
                    dir = data.getData();
                    image.setImage(dir);

                    ImageType.showImageDescriptionDialog(getActivity(), image);

                    ImageType.refreshGallery(getActivity());
                    break;
                case ImageType.REQUEST_CHOOSE_PHOTO:
                    dir = data.getData();
                    image.setImage(dir);

                    ImageType.showImageDescriptionDialog(getActivity(), image);

                    Log.d(TAG, "onActivityResult " + dir);
                    Glide.with(this).load(dir).asBitmap().into(mImageView);
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

    private void fillLinksList(ParseQuery<LinkType> linkQuery) {
        linkQuery.findInBackground(new FindCallback<LinkType>() {
            public void done(List<LinkType> links, ParseException e) {
                if (e == null) {
                    mLinks = links;
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void fillPDFsList(ParseQuery<PdfType> linkQuery) {
        linkQuery.findInBackground(new FindCallback<PdfType>() {
            public void done(List<PdfType> pdfs, ParseException e) {
                if (e == null) {
                    mPDFs = pdfs;
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void fillImagesList(ParseQuery<ImageType> linkQuery) {
        linkQuery.findInBackground(new FindCallback<ImageType>() {
            public void done(List<ImageType> images, ParseException e) {
                if (e == null) {
                    mImages = images;
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void setImageCurrentConstrains(ParseQuery<ImageType> imageQuery) {
        imageQuery.whereEqualTo(ParseConstants.KEY_UNIVERSITY, DataItem.getUniversity());
        imageQuery.whereEqualTo(ParseConstants.KEY_FACULTY, DataItem.getFaculty());
        imageQuery.whereEqualTo(ParseConstants.KEY_DEPARTMENT, DataItem.getDepartment());
        imageQuery.whereEqualTo(ParseConstants.KEY_GRADE, DataItem.getGrade());
        imageQuery.whereEqualTo(ParseConstants.KEY_TERM, DataItem.getTerm());
        imageQuery.whereEqualTo(ParseConstants.KEY_SUBJECT, DataItem.getSubject());
        imageQuery.whereEqualTo(ParseConstants.KEY_WEEK, DataItem.getWeek());
    }

    private void setPdfCurrentConstrains(ParseQuery<PdfType> pdfQuery) {
        pdfQuery.whereEqualTo(ParseConstants.KEY_UNIVERSITY, DataItem.getUniversity());
        pdfQuery.whereEqualTo(ParseConstants.KEY_FACULTY, DataItem.getFaculty());
        pdfQuery.whereEqualTo(ParseConstants.KEY_DEPARTMENT, DataItem.getDepartment());
        pdfQuery.whereEqualTo(ParseConstants.KEY_GRADE, DataItem.getGrade());
        pdfQuery.whereEqualTo(ParseConstants.KEY_TERM, DataItem.getTerm());
        pdfQuery.whereEqualTo(ParseConstants.KEY_SUBJECT, DataItem.getSubject());
        pdfQuery.whereEqualTo(ParseConstants.KEY_WEEK, DataItem.getWeek());
    }

    private void setLinkCurrentConstrains(ParseQuery<LinkType> linkQuery) {
        linkQuery.whereEqualTo(ParseConstants.KEY_UNIVERSITY, DataItem.getUniversity());
        linkQuery.whereEqualTo(ParseConstants.KEY_FACULTY, DataItem.getFaculty());
        linkQuery.whereEqualTo(ParseConstants.KEY_DEPARTMENT, DataItem.getDepartment());
        linkQuery.whereEqualTo(ParseConstants.KEY_GRADE, DataItem.getGrade());
        linkQuery.whereEqualTo(ParseConstants.KEY_TERM, DataItem.getTerm());
        linkQuery.whereEqualTo(ParseConstants.KEY_SUBJECT, DataItem.getSubject());
        linkQuery.whereEqualTo(ParseConstants.KEY_WEEK, DataItem.getWeek());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
