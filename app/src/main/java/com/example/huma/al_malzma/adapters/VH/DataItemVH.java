package com.example.huma.al_malzma.adapters.VH;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.parse.ParseConstants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DataItemVH extends RecyclerView.ViewHolder {

    @Bind(R.id.type_image_view) ImageView mTypeImageView;
    @Bind(R.id.title) TextView mTitle;
    @Bind(R.id.description) TextView mDescription;
    @Bind(R.id.votes) TextView mVotes;
    @Bind(R.id.creator_name) TextView mCreatorName;
    @Bind(R.id.time) TextView mTime;
    @Bind(R.id.up_Text_view) TextView mUpTextView;
    @Bind(R.id.down_Text_view) TextView mDownTextView;


    public DataItemVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void render(@ParseConstants.DataType String type, String title,
                       String description, int votes, String creatorName, String time) {
        mTitle.setText(title);
        mDescription.setText(description);
        mVotes.setText(String.valueOf(votes));
        mCreatorName.setText(creatorName);
        mTime.setText(time);

    }
}
