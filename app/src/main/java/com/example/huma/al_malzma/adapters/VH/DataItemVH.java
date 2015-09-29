package com.example.huma.al_malzma.adapters.VH;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huma.al_malzma.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DataItemVH extends RecyclerView.ViewHolder {

    @Bind(R.id.type_image_view) ImageView mTypeImageView;
    @Bind(R.id.title) TextView mTitle;
    @Bind(R.id.description) TextView mDescription;
    @Bind(R.id.points) TextView mPoints;
    @Bind(R.id.name) TextView mName;
    @Bind(R.id.time) TextView mTime;
    @Bind(R.id.up_Text_view) TextView mUpTextView;
    @Bind(R.id.down_Text_view) TextView mDownTextView;


    public DataItemVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void render(String text) {
        mDescription.setText(text);
    }
}
