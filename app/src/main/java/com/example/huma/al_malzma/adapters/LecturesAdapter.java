package com.example.huma.al_malzma.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huma.al_malzma.R;
import com.example.huma.al_malzma.adapters.VH.DataItemVH;
import com.example.huma.al_malzma.adapters.VH.FooterVH;
import com.example.huma.al_malzma.adapters.VH.HeaderVH;
import com.example.huma.al_malzma.model.ImageType;
import com.example.huma.al_malzma.model.LinkType;
import com.example.huma.al_malzma.model.PdfType;
import com.example.huma.al_malzma.parse.ParseConstants;
import com.example.huma.al_malzma.ui.subject_fragments.LecturesFragment;
import com.truizlop.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import java.util.List;

public class LecturesAdapter extends SectionedRecyclerViewAdapter<HeaderVH, DataItemVH, FooterVH> {

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
    protected HeaderVH onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.view_recycler_header, parent, false);
        return new HeaderVH(view);
    }

    @Override
    protected FooterVH onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected DataItemVH onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.row_data_item, parent, false);
        return new DataItemVH(view);
    }

    @Override
    protected void onBindSectionHeaderViewHolder(HeaderVH holder, int section) {
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
    protected void onBindSectionFooterViewHolder(FooterVH holder, int section) { /***/}

    @Override
    protected void onBindItemViewHolder(DataItemVH holder, int section, int position) {
        switch (section) {
            case 0:
                PdfType pdf = pdfs.get(position);
                holder.render(ParseConstants.KEY_TYPE_IMAGE, "PDF", pdf.getDescription(),
                        pdf.getVotes(), pdf.getCreatorName(), "1 h");
                break;
            case 1:
                ImageType image = images.get(position);
                holder.render(ParseConstants.KEY_TYPE_IMAGE, "PDF", image.getDescription(),
                        image.getVotes(), image.getCreatorName(), "1 h");
                break;
            case 2:
                LinkType link = links.get(position);
                holder.render(ParseConstants.KEY_TYPE_LINK, link.getLink(), link.getDescription(),
                        link.getVotes(), link.getCreatorName(), "1 h");
                break;
        }
    }
}
