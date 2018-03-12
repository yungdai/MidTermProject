package com.cibc.ydai.midtermproject.ui.home;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.cibc.ydai.midtermproject.AppActivity;
import com.cibc.ydai.midtermproject.R;
import com.cibc.ydai.midtermproject.data.contact.ContactModel;
import com.cibc.ydai.midtermproject.ui.home.events.OnContactEvent;
import com.cibc.ydai.midtermproject.ui.home.events.OnContactUpdatedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * Created by yungdai on 2018-03-12.
 */

public class Contacts extends ConstraintLayout {

    private Button add;
    private RecyclerView recyclerView;

    public Contacts(Context context) { this(context, null, 0); }
    public Contacts(Context context, @Nullable AttributeSet attrs) { this(context, attrs, 0); }
    public Contacts(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Subscribe
    public void onEventBusEvent(OnContactUpdatedEvent event) {

        // hide the keybaord when we are updating a contact event
        AppActivity.hideKeyboard(getContext());
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        add = findViewById(R.id.add);
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // send to the event bus that there is a need to create a new contact
                ContactModel emptyContact = new ContactModel("","","","");

                // post to the default event bus a new OnContactEvent with an empty contact at position that's not visible (-1)
                EventBus.getDefault().post(new OnContactEvent(emptyContact, -1));
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        // register to listen for any evens from the bus for this class
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        // unregister from the event bus when detached from a window
        EventBus.getDefault().unregister(this);
    }
}


