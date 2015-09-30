package com.example.huma.al_malzma.adapters.VH;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.model.BaseDataItem;
import com.example.huma.al_malzma.model.LinkType;
import com.example.huma.al_malzma.parse.ParseConstants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DataItemVH extends RecyclerView.ViewHolder {
    private static final String TAG = DataItemVH.class.getSimpleName();


    @Bind(R.id.type_image_view) ImageView mTypeImageView;
    @Bind(R.id.title) TextView mTitle;
    @Bind(R.id.description) TextView mDescription;
    @Bind(R.id.votes) TextView mVotes;
    @Bind(R.id.creator_name) TextView mCreatorName;
    @Bind(R.id.time) TextView mTime;
    @Bind(R.id.up_Text_view) TextView mUpTextView;
    @Bind(R.id.down_Text_view) TextView mDownTextView;

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

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getDataType().equals(ParseConstants.KEY_TYPE_PDF)) {

                } else if (item.getDataType().equals(ParseConstants.KEY_TYPE_IMAGE)) {

                } else if (item.getDataType().equals(ParseConstants.KEY_TYPE_LINK)) {
                    LinkType.openUri(itemView.getContext(), ((LinkType) item).getLink());
                }
                Log.d(TAG, "onClick " + item.getDataType());
            }
        });

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


    }

}
