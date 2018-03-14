package com.cibc.ydai.midtermproject.data.contact;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by yungdai on 2018-03-12.
 */

public class ContactModel {

    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PHONE = "phone";
    private static final String WEBSITE = "website";
    private static final String IMAGE = "Image";

    @NonNull private final Bundle mBundle;


    // class initialiser
    public ContactModel(String firstName, String lastName, String phone, String website, Bitmap image) {

        mBundle = new Bundle();
        mBundle.putString(FIRST_NAME, firstName);
        mBundle.putString(LAST_NAME, lastName);
        mBundle.putString(PHONE, phone);
        mBundle.putString(WEBSITE, website);
        mBundle.putParcelable(IMAGE, image);
    }

    /**
     * Compares against other object and returns true if other object is from 'ContactModel' class,
     * as well as 'First name' and 'Web site' strings are equal
     *
     * @param obj Any other object to compare against
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  ContactModel) {

            ContactModel other = (ContactModel) obj;
            return getFirstName().equals(other.getFirstName()) && getWebsite().equals(other.getWebsite());
        }
        else {

            return false;
        }


    }

    public String getFirstName() { return mBundle.getString(FIRST_NAME, ""); }
    public String getLastName() { return mBundle.getString(LAST_NAME, ""); }
    public String getPhone() { return mBundle.getString(PHONE, ""); }
    public String getWebsite() { return mBundle.getString(WEBSITE, ""); }
    public Bitmap getImage() {

        return mBundle.getParcelable(IMAGE); }


}
