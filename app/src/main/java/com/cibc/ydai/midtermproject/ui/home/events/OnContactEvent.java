package com.cibc.ydai.midtermproject.ui.home.events;

import com.cibc.ydai.midtermproject.data.contact.ContactModel;

/**
 * Created by yungdai on 2018-03-12.
 */

public class OnContactEvent {

    // if null we will create a new brand new contact
    public final ContactModel mContactModel;
    public final int contactModelPosition;

    // @param contactModelPosition Position of the contact in the dataset. If a negative value will treat as new
    public OnContactEvent(ContactModel contactModel, int contactModelPosition) {
        this.mContactModel = contactModel;
        this.contactModelPosition = contactModelPosition;
    }
}
