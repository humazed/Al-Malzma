package com.example.huma.al_malzma.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.adapters.VH.DataItemViewHolder;
import com.example.huma.al_malzma.adapters.VH.FooterViewHolder;
import com.example.huma.al_malzma.adapters.VH.HeaderViewHolder;
import com.example.huma.al_malzma.model.ImageType;
import com.example.huma.al_malzma.model.LinkType;
import com.example.huma.al_malzma.model.PdfType;
import com.example.huma.al_malzma.ui.subject_fragments.LecturesFragment;
import com.truizlop.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import java.util.List;

public class LecturesAdapter extends SectionedRecyclerViewAdapter<HeaderViewHolder, DataItemViewHolder, FooterViewHolder> {

    private static final String TAG = LecturesAdapter.class.getSimpleName();


    protected Context context = null;

    List<PdfType> pdfs;
    List<ImageType> images;
    List<LinkType> links;

    public LecturesAdapter(Context context, List<PdfType> pdfs, List<ImageType> images, List<LinkType> links) {
        this.context = context;
        this.pdfs = pdfs;
        this.images = images;
        this.links = links;
    }


    @Override
    protected int getItemCountForSection(int section) {
        switch (section) {
            case 0:
                LecturesFragment.mPDFs.isEmpty();
                return LecturesFragment.mPDFs.size();
            case 1:
                return LecturesFragment.mImages.size();
            case 2:
                return LecturesFragment.mLinks.size();
            default:
                return 0;
        }
    }

    @Override
    protected int getSectionCount() {
        Log.d(TAG, "getSectionCount ");
        return 3;
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    protected LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(context);
    }

    @Override
    protected HeaderViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.view_recycler_header, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    protected FooterViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected DataItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.row_data_item, parent, false);
        return new DataItemViewHolder(view);
    }

    @Override
    protected void onBindSectionHeaderViewHolder(HeaderViewHolder holder, int section) {
        switch (section) {
            case 0:
                holder.render("PDFs");
                break;
            case 1:
                holder.render("IMAGEs");
                break;
            case 2:
                holder.render("LINKs");
                break;

        }
    }

    @Override
    protected void onBindSectionFooterViewHolder(FooterViewHolder holder, int section) { /***/}

    @Override
    protected void onBindItemViewHolder(DataItemViewHolder holder, int section, int position) {
        switch (section) {
            case 0:
                holder.render(pdfs.get(position).getDescription());
                break;
            case 1:
                holder.render(images.get(position).getDescription());
                break;
            case 2:
                holder.render(links.get(position).getDescription());
                break;
        }
    }
}
