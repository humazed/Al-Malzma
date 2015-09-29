package com.example.huma.al_malzma.adapters.VH;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.huma.al_malzma.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HeaderVH extends RecyclerView.ViewHolder {

    @Bind({R.id.title}) TextView textView;

    public HeaderVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void render(String text) {
        textView.setText(text);
    }
}
