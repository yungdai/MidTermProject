package com.cibc.ydai.midtermproject.ui.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by yungdai on 2018-03-12.
 */

public class HomeAdapter extends PagerAdapter {

    private final LayoutInflater mLayoutInflater;
    private final List<HomeAdapterPage> pages;


    public HomeAdapter(Context context, List<HomeAdapterPage> pages) {
        mLayoutInflater = LayoutInflater.from(context);
        this.pages = pages;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup viewPagerContainter, int position) {
        // set the position fo the page to the position set by the home adaptor
        HomeAdapterPage page = pages.get(position);

        View view = mLayoutInflater.inflate(page.getLayoutId(), viewPagerContainter, false);
        viewPagerContainter.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup viewPagerContainer, int position, @NonNull Object pageViewObject) {
        viewPagerContainer.removeView((View) pageViewObject);
    }

    @Override
    public int getCount() {
        return  pages.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pages.get(position).getTitle();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }
}
