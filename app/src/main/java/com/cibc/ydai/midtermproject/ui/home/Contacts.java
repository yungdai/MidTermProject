package com.cibc.ydai.midtermproject.ui.home;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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
    private RecyclerView.LayoutManager mLayoutManager;

    // add teh contacts adaptor to work with the recycling view
    private ContactsAdapter mContactsAdapter;

    public Contacts(Context context) { this(context, null, 0); }
    public Contacts(Context context, @Nullable AttributeSet attrs) { this(context, attrs, 0); }
    public Contacts(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Subscribe
    public void onEventBusEvent(OnContactUpdatedEvent onContactUpdatedEvent) {

        // hide the keybaord when we are updating a contact event
        AppActivity.hideKeyboard(getContext());

        if (onContactUpdatedEvent.mContactModel != null) {

            // we have to add something because there is something to add
            if (onContactUpdatedEvent.contactModelPosition < 0) {

                // negative value for position is treated as the last position when adding
                mContactsAdapter.contacts.add(onContactUpdatedEvent.mContactModel);
                mContactsAdapter.notifyItemInserted(mContactsAdapter.contacts.size() - 1);
            }
            else {

                // update the existing contact with the one provided and refresh the list
                mContactsAdapter.contacts.set(onContactUpdatedEvent.contactModelPosition, onContactUpdatedEvent.mContactModel);
                mContactsAdapter.notifyItemChanged(onContactUpdatedEvent.contactModelPosition);
            }
        }
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

        // have the contact adapter set up the recycler view
        mContactsAdapter = new ContactsAdapter();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mContactsAdapter);
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


