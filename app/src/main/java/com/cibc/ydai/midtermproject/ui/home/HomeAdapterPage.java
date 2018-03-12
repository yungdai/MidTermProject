package com.cibc.ydai.midtermproject.ui.home;

/**
 * Created by yungdai on 2018-03-12.
 */

public class HomeAdapterPage {
    private final int layoutId;
    private final String title;

    HomeAdapterPage(int layoutId, String title) {
        this.layoutId = layoutId;
        this.title = title;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public String getTitle() {
        return title;
    }
}
