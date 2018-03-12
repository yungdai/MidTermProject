package com.cibc.ydai.midtermproject.data.contact;

import android.os.Bundle;

/**
 * Created by yungdai on 2018-03-12.
 */

public class ContactModel {

    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PHONE = "phone";
    private static final String WEBSITE = "website";

    private final Bundle mBundle;


    // class initialiser
    public ContactModel(String firstName, String lastName, String phone, String website) {

        mBundle = new Bundle();
        mBundle.putString(FIRST_NAME, firstName);
        mBundle.putString(LAST_NAME, lastName);
        mBundle.putString(PHONE, phone);
        mBundle.putString(WEBSITE, website);
    }

    public ContactModel(Bundle bundle) { this.mBundle = bundle; }


    public String getFirstName() { return mBundle.getString(FIRST_NAME, ""); }
    public String getLastName() { return mBundle.getString(LAST_NAME, ""); }
    public String getPhone() { return mBundle.getString(PHONE, ""); }
    public String getWebsite() { return mBundle.getString(WEBSITE, ""); }
}
