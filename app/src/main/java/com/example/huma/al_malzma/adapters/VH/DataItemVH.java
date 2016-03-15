package com.example.huma.al_malzma.adapters.VH;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.helper.FileHelper;
import com.example.huma.al_malzma.model.BaseDataItem;
import com.example.huma.al_malzma.model.ImageType;
import com.example.huma.al_malzma.model.LinkType;
import com.example.huma.al_malzma.model.PdfType;
import com.example.huma.al_malzma.parse.ParseConstants;
import com.example.huma.al_malzma.ui.ImageActivity;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ProgressCallback;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DataItemVH extends RecyclerView.ViewHolder {
    private static final String TAG = DataItemVH.class.getSimpleName();
    public static final String KEY_IMAGE_ID = "imageID";


    @Bind(R.id.type_image_view) ImageView mTypeImageView;
    @Bind(R.id.download_image_view) ImageView mDownlandImageView;
    @Bind(R.id.open_image_view) ImageView mOpenImageView;
    @Bind(R.id.loading_text_view) TextView mLoadingTextView;
    @Bind(R.id.title) TextView mTitle;
    @Bind(R.id.description) TextView mDescription;
    @Bind(R.id.votes) TextView mVotes;
    @Bind(R.id.creator_name) TextView mCreatorName;
    @Bind(R.id.time) TextView mTime;
    @Bind(R.id.up_Text_view) TextView mUpTextView;
    @Bind(R.id.down_Text_view) TextView mDownTextView;
    @Bind(R.id.data_container) RelativeLayout mDataContainer;


    View itemView;
    private File mPdfFile;

    public DataItemVH(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }


    public void render(final BaseDataItem item, String title,
                       String description) {
        mTitle.setText(title);
        mDescription.setText(description);
        mVotes.setText(String.valueOf(item.getVotes()));
        mCreatorName.setText(item.getCreatorName());
        mTime.setText(item.getCreatedAt().toString());


        switch (item.getDataType()) {
            case ParseConstants.KEY_TYPE_PDF:
                Log.d(TAG, "render " + item.getDataType());
                Glide.with(itemView.getContext())
                        .load(R.drawable.ic_insert_drive_file_36dp)
                        .placeholder(R.drawable.ic_insert_drive_file_36dp)
                        .into(mTypeImageView);
                mDownlandImageView.setVisibility(View.VISIBLE);
                break;
            case ParseConstants.KEY_TYPE_IMAGE:
                Glide.with(itemView.getContext())
                        .load(Uri.parse(((ImageType) item).getImage().getUrl()))
                        .centerCrop()
                        .crossFade()
                        .placeholder(R.drawable.ic_photo_36dp)
                        .into(mTypeImageView);
                mDownlandImageView.setVisibility(View.GONE);
                break;
            case ParseConstants.KEY_TYPE_LINK:
                Log.d(TAG, "render " + "link");
                Glide.with(itemView.getContext())
                        .load(R.drawable.ic_insert_link_36dp)
                        .placeholder(R.drawable.ic_insert_link_36dp)
                        .into(mTypeImageView);

                mDownlandImageView.setVisibility(View.GONE);
                break;
        }

        //open the data when type on item.
        mDataContainer.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(View v) {
                switch (item.getDataType()) {
                    case ParseConstants.KEY_TYPE_PDF:
                        String pdfUrl = ((PdfType) item).getPDF().getUrl();

                        String googleDocsUrl = "http://docs.google.com/viewer?url=" + pdfUrl;
                        Intent viewPdfIntent = new Intent(Intent.ACTION_VIEW);
                        viewPdfIntent.setDataAndType(Uri.parse(googleDocsUrl), "text/html");
                        itemView.getContext().startActivity(viewPdfIntent);
                        break;
                    case ParseConstants.KEY_TYPE_IMAGE:
                        Intent imageIntent = new Intent(v.getContext(), ImageActivity.class);
                        imageIntent.putExtra(KEY_IMAGE_ID, item.getObjectId());
                        v.getContext().startActivity(imageIntent);
                        break;
                    case ParseConstants.KEY_TYPE_LINK:
                        LinkType.openUri(itemView.getContext(), ((LinkType) item).getLink());
                        break;
                }
                Log.d(TAG, "onClick " + item.getDataType());
            }
        });

        //handel up and down tv
        mUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.incrementPositiveVotes();
                mVotes.setText(String.valueOf(item.getVotes()));
            }
        });

        mDownTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.incrementNegativeVotes();
                mVotes.setText(String.valueOf(item.getVotes()));
            }
        });

        mDownlandImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mLoadingTextView.setVisibility(View.VISIBLE);
                mDownlandImageView.setVisibility(View.GONE);

                ParseFile pdfParseFile = ((PdfType) item).getPDF();
                final String fileName = pdfParseFile.getName();

                pdfParseFile.getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] bytess, ParseException e) {
                        if (e == null) {
                            mLoadingTextView.setVisibility(View.GONE);
                            mOpenImageView.setVisibility(View.VISIBLE);

                            File subjectDir = FileHelper.getSubjectFile(v.getContext());
                            mPdfFile = new File(subjectDir, fileName);
                            try {
                                FileUtils.writeByteArrayToFile(mPdfFile, bytess);
                                Toast.makeText(v.getContext(), "Saved to: " + mPdfFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            Log.e(TAG, "done: Fail: ", e);
                            Toast.makeText(v.getContext(), "Fail! :(", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new ProgressCallback() {
                    @Override
                    public void done(Integer integer) {
                        String percent = integer + "%";
                        mLoadingTextView.setText(percent);
                    }
                });
            }
        });

        mOpenImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PdfType.displayPdf(v.getContext(), Uri.fromFile(mPdfFile.getAbsoluteFile()));
            }
        });

    }

}
