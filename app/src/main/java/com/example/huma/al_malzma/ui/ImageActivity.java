package com.example.huma.al_malzma.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diegocarloslima.byakugallery.lib.TouchImageView;
import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.adapters.VH.DataItemVH;
import com.example.huma.al_malzma.model.ImageType;
import com.example.huma.al_malzma.parse.ParseConstants;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ProgressCallback;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * this activity is used only to display images that downloaded from parse.com
 */
public class ImageActivity extends AppCompatActivity {
    private static final String TAG = ImageActivity.class.getSimpleName();

    @Bind(R.id.imageView) TouchImageView mImageView;
    @Bind(R.id.loading_text_view) TextView mLoadingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String imageID = intent.getStringExtra(DataItemVH.KEY_IMAGE_ID);

        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_IMAGE);
        query.getInBackground(imageID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    ImageType image = (ImageType) parseObject;
                    image.getParseFile(ParseConstants.KEY_IMAGE).getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] bytes, ParseException e) {
                            if (e == null) {
                                Glide.with(ImageActivity.this)
                                        .load(bytes)
                                        .crossFade()
                                        .into(mImageView);
                                mLoadingTextView.setVisibility(View.GONE);
                            } else {
                                Log.e(TAG, "done: Fail: ", e);
                                String text = "Fail! :(";
                                mLoadingTextView.setText(text);
                            }
                        }
                    }, new ProgressCallback() {
                        @Override
                        public void done(Integer integer) {
                            String text = integer + "%";
                            mLoadingTextView.setText(text);
                        }
                    });

                } else {
                    Log.e(TAG, "done: Fail: ", e);
                }
            }
        });

    }
}
