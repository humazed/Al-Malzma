package com.example.huma.al_malzma.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huma.al_malzma.R;

import butterknife.Bind;
import butterknife.ButterKnife;

// TODO: 3/15/2016 I just don't know why I created this class
public class BaseDataAdapter extends RecyclerView.Adapter<BaseDataAdapter.ViewHolder> {

    Context mContext;

    public BaseDataAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup Parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.base_list_item, Parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iTextView) ImageView mITextView;
        @Bind(R.id.s1_Text_view) TextView mS1TextView;
        @Bind(R.id.s2_Text_view) TextView mS2TextView;
        @Bind(R.id.up_Text_view) TextView mUpTextView;
        @Bind(R.id.down_Text_view) TextView mDownTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind() {
        }

    }


}
