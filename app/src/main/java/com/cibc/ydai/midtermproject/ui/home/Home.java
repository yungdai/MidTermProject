package com.cibc.ydai.midtermproject.ui.home;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.cibc.ydai.midtermproject.R;
import com.cibc.ydai.midtermproject.ui.home.events.OnContactEvent;
import com.cibc.ydai.midtermproject.ui.home.events.OnContactUpdatedEvent;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yungdai on 2018-03-12.
 */

public class Home extends ViewPager {


    private static final int POSITION_CONTACTS = 0;
    private static final int POSITION_CONTACT = 1;


    // create a context of Home with out attributeSets
    public Home(Context context) { this(context, null); }

    // allow for creating a Context with an attributeSets for Home
    public Home(Context context, AttributeSet attrs) { super(context, attrs); }


    // these two subscribed events are created in the events folder
    @Subscribe
    public void onEventBus(OnContactEvent onContactEvent) {
        // move to the second page of contacts
        setCurrentItem(POSITION_CONTACT);
    }

    @Subscribe
    public void onEventBus(OnContactUpdatedEvent onContactUpdatedEvent) {
        // move to the first page of contacts
        setCurrentItem(POSITION_CONTACTS);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // set the two pages for the view pager
        List<HomeAdapterPage> pages = new ArrayList<>(2);
        pages.add(POSITION_CONTACTS, new HomeAdapterPage(R.layout.home_contacts, "Contacts"));
        pages.add(POSITION_CONTACT, new HomeAdapterPage(R.layout.home_contact, "Contact Form"));

        // and set the adapter
        setAdapter(new HomeAdapter(getContext(), pages));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        // register to listen to any event from the event bus
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        // unregister to listen for any events
        EventBus.getDefault().unregister(this);
    }
}
