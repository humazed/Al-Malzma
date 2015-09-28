/*
 * Copyright (C) 2015 Tomás Ruiz-López.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.huma.al_malzma.adapters.VH;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huma.al_malzma.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class DataItemViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.type_image_view) ImageView mTypeImageView;
    @Bind(R.id.title) TextView mTitle;
    @Bind(R.id.description) TextView mDescription;
    @Bind(R.id.points) TextView mPoints;
    @Bind(R.id.name) TextView mName;
    @Bind(R.id.time) TextView mTime;
    @Bind(R.id.up_Text_view) TextView mUpTextView;
    @Bind(R.id.down_Text_view) TextView mDownTextView;


    public DataItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void render(String text){
        mDescription.setText(text);
    }
}
