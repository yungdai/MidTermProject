package com.cibc.ydai.midtermproject.ui.home.events;

import com.cibc.ydai.midtermproject.data.contact.ContactModel;

/**
 * Created by yungdai on 2018-03-12.
 */

public class OnContactUpdatedEvent {

    /**
     *if the event is null we will create a brand new contact
     */

    public final ContactModel mContactModel;
    public final int contactModelPosition;

    public OnContactUpdatedEvent(ContactModel mContactModel, int contactModelPosition) {

        this.mContactModel = mContactModel;
        this.contactModelPosition = contactModelPosition;
    }
}
