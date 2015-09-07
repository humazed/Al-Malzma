package com.example.huma.al_malzma.helper;


import android.content.Context;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;

import com.example.huma.al_malzma.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class FabAnimationHelper {


    private static int mPreviousVisibleItem;

    public static AbsListView.OnScrollListener hideMenuOnScrollListener(final FloatingActionMenu fam) {
        //hide the Menu when scroll.
        return new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem > mPreviousVisibleItem) {
                    fam.hideMenuButton(true);
                } else if (firstVisibleItem < mPreviousVisibleItem) {
                    fam.showMenuButton(true);
                }
                mPreviousVisibleItem = firstVisibleItem;
            }
        };
    }

    public static AbsListView.OnScrollListener hideFabOnScrollListener(final FloatingActionButton fam) {
        //hide the Menu when scroll.
        return new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem > mPreviousVisibleItem) {
                    fam.hide(true);
                } else if (firstVisibleItem < mPreviousVisibleItem) {
                    fam.show(true);
                }
                mPreviousVisibleItem = firstVisibleItem;
            }
        };
    }

    public static void animMenu(final Context context, final FloatingActionMenu fam) {
        fam.hideMenuButton(false);
        fam.setClosedOnTouchOutside(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fam.showMenuButton(true);
                fam.setMenuButtonShowAnimation(AnimationUtils.loadAnimation(context, R.anim.show_from_bottom));
                fam.setMenuButtonHideAnimation(AnimationUtils.loadAnimation(context, R.anim.hide_to_bottom));
            }
        }, 300);
    }

}
