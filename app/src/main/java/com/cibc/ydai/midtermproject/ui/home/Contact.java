package com.cibc.ydai.midtermproject.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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

// the view details will be inflated in a layout file
// this class is a holder of all the logic for the scrollView, and it's view contact will be flassed out in a layout, res/layout/contact.xml

public class Contact extends ScrollView {

    private TextView firstName;
    private TextView lastName;
    private TextView phone;
    private TextView website;

    private ContactModel mContactModel;
    private int contactModelPosition = -1;

    public Contact(Context context) { super(context); }
    public Contact(Context context, AttributeSet attrs) { this(context, attrs, 0);}
    public Contact(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Subscribe
    public void onEventBusEvent(OnContactEvent onContactEvent) {
        this.mContactModel = onContactEvent.mContactModel;
        this.contactModelPosition = onContactEvent.contactModelPosition;

        updateUI();

        AppActivity.showKeyboard(getContext(), firstName);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // this method will be called at the end of the layout inflation.
        // it is safe to look for any view children

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phone = findViewById(R.id.phone);
        website = findViewById(R.id.website);

        Button web = findViewById(R.id.web);
        web.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String websiteValue = website.getText().toString();

                if (!websiteValue.isEmpty()) {
                    // this is a URL

                    if (!websiteValue.startsWith("http://")) {
                        // append http:// to the begining of the url name if it's not there
                        websiteValue = "http://" + websiteValue;
                    }

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(websiteValue));
                    getContext().startActivity(intent);
                }
            }
        });

        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // put null as contact so nothing will be added or updated
                EventBus.getDefault().post(new OnContactUpdatedEvent(null, -1));
            }
        });

        Button update = findViewById(R.id.update);
        update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstNameValue = firstName.getText().toString();
                String websiteValue = website.getText().toString();

                if (firstNameValue.isEmpty() || websiteValue.isEmpty()) {
                    // empty value for "firstName" or "website"

                    Context context = getContext();

                    Toast
                            .makeText(context, context.getText(R.string.first_name_and_website_cannot_be_empty), Toast.LENGTH_SHORT)
                            .show();
                }
                else {
                    if (mContactModel != null) {
                        // update was definetly called from a user action rather swiping blindly
                        // from the first page

                        mContactModel = new ContactModel(firstNameValue, lastName.getText().toString(),
                                phone.getText().toString(), websiteValue);
                    }

                    // dispatch the event with the updated contact and it's position
                    EventBus.getDefault().post(new OnContactUpdatedEvent(mContactModel, contactModelPosition));

                    // reset UI
                    mContactModel = null;
                    contactModelPosition = -1;
                    updateUI();
                }


            }
        });

    }

    // update the UI from contactModel
    private void updateUI() {
        // defaul everything to nothing or empty
        String firstNameValue = "";
        String lastNameValue = "";
        String phoneValue = "";
        String websiteValue = "";

        // if there is a contact model then just fill in the values with the values of the mContactModel
        if (mContactModel != null) {
            firstNameValue = mContactModel.getFirstName();
            lastNameValue = mContactModel.getLastName();
            phoneValue = mContactModel.getPhone();
            websiteValue = mContactModel.getWebsite();
        }

        firstName.setText(firstNameValue);
        lastName.setText(lastNameValue);
        phone.setText(phoneValue);
        website.setText(websiteValue);
    }

}