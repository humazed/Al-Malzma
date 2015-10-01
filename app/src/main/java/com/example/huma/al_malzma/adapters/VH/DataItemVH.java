package com.example.huma.al_malzma.adapters.VH;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.model.BaseDataItem;
import com.example.huma.al_malzma.model.ImageType;
import com.example.huma.al_malzma.model.LinkType;
import com.example.huma.al_malzma.model.PdfType;
import com.example.huma.al_malzma.parse.ParseConstants;
import com.example.huma.al_malzma.ui.ImageActivity;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ProgressCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DataItemVH extends RecyclerView.ViewHolder {
    private static final String TAG = DataItemVH.class.getSimpleName();
    public static final String KEY_IMAGE_ID = "imageID";


    @Bind(R.id.type_image_view) ImageView mTypeImageView;
    @Bind(R.id.download_image_view) ImageView mDownlandImageView;
    @Bind(R.id.title) TextView mTitle;
    @Bind(R.id.description) TextView mDescription;
    @Bind(R.id.votes) TextView mVotes;
    @Bind(R.id.creator_name) TextView mCreatorName;
    @Bind(R.id.time) TextView mTime;
    @Bind(R.id.up_Text_view) TextView mUpTextView;
    @Bind(R.id.down_Text_view) TextView mDownTextView;
    @Bind(R.id.row_container) RelativeLayout mContainer;

    View itemView;

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
                mDownlandImageView.setVisibility(View.VISIBLE);
                break;
            case ParseConstants.KEY_TYPE_IMAGE:
                Glide.with(itemView.getContext())
                        .load(Uri.parse(((ImageType) item).getImage().getUrl()))
                        .centerCrop()
                        .crossFade()
                        .into(mTypeImageView);
                break;
            case ParseConstants.KEY_TYPE_LINK:
                break;
        }

        //open the data when type on item.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContainer.setBackgroundColor(0xFFC107);
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
//                        imageIntent.setData(Uri.parse(((ImageType) item).getImage().getUrl()));
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
            public void onClick(View v) {
//                Uri pdfUri = Uri.parse(((PdfType) item).getPDF().getUrl());
                ((PdfType) item).getPDF().getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] bytess, ParseException e) {

                        File dir = Environment.getExternalStorageDirectory();

                        File assist = new File("/mnt/sdcard/Sample.pdf");
                        try {
                            FileInputStream fis = new FileInputStream(assist);

                            long length = assist.length();
                            if (length > Integer.MAX_VALUE) {
                                Log.e(TAG, "Sorry! Your given file is too large. cannnottt   readddd");
                            }
                            byte[] bytes = new byte[(int) length];
                            int offset = 0;
                            int numRead;
                            while (offset < bytes.length && (numRead = fis.read(bytes, offset, bytes.length - offset)) >= 0) {
                                offset += numRead;
                            }

                            File data = new File(dir, "mydemo.pdf");
                            OutputStream op = new FileOutputStream(data);
                            op.write(bytes);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }, new ProgressCallback() {
                    @Override
                    public void done(Integer integer) {

                    }
                });


            }
        });

    }

}
