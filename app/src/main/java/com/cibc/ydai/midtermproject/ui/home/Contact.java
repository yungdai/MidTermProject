package com.cibc.ydai.midtermproject.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.cibc.ydai.midtermproject.R;
import com.cibc.ydai.midtermproject.Utilities.Utilities;
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
    private ImageView mImageView;
    private TextView formTitle;
    private Button takePicture;
    private Button cancel;
    private Button update;
    private Button edit;

    // the contact model that is passed or not passed in from the event bus
    private ContactModel mContactModel;
    // check to see if you are editing or not
    private Boolean isEditing = false;
    private Boolean isCreatingContact = false;
    // anyone with a negative positions is going to be ignored.
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
        this.isEditing = onContactEvent.isEditing;
        this.isCreatingContact = onContactEvent.isCreatingContact;

        updateUI();

        Utilities.showKeyboard(getContext(), firstName);
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
        mImageView = findViewById(R.id.picture);
        formTitle = findViewById(R.id.title);

        takePicture = findViewById(R.id.take_picture);
        cancel = findViewById(R.id.cancel);
        update = findViewById(R.id.update);

        edit = findViewById(R.id.edit_button);
        edit.setOnClickListener(v -> {

            toggleVisibility();
        });

        Button web = findViewById(R.id.web);
        web.setOnClickListener(v -> {
            String webAddress = website.getText().toString();

            if (!webAddress.isEmpty()) {
                // this is a URL

                if (!webAddress.startsWith("http://")) {
                    // append http:// to the begining of the url name if it's not there
                    webAddress = String.format("http://%s;", webAddress);
                }

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(webAddress));
                getContext().startActivity(intent);
            }
        });

        // set it so that the first name and last name are capitalised
        firstName.setOnFocusChangeListener(
                (v, hasFocus) -> {
                    if (!hasFocus) {
                        Utilities.capitaliseFirtCharacterIn(firstName);
                    }
                }
        );

        lastName.setOnFocusChangeListener(
                (v, hasFocus) -> {
                    if (!hasFocus) {
                        Utilities.capitaliseFirtCharacterIn(firstName);
                    }
                }
        );

        cancel.setOnClickListener(v -> {

            // put null as contact so nothing will be added or updated
            EventBus.getDefault().post(new OnContactUpdatedEvent(null, -1));

            // reset UI
            mContactModel = null;
            contactModelPosition = -1;
            updateUI();
        });

        update.setOnClickListener(v -> {

            String firstNameValue = firstName.getText().toString();
            String websiteValue = website.getText().toString();


            if (firstNameValue.isEmpty() || websiteValue.isEmpty()) {
                // empty value for "firstName" or "website"
                Toast
                        .makeText(getContext(), getContext().getText(R.string.first_name_and_website_cannot_be_empty), Toast.LENGTH_SHORT)
                        .show();
            }
            else {

                // In case you swiped over instead of pressed the add user button.
                if (mContactModel == null) {
                    mContactModel = new ContactModel("","","","",null);
                }

                if (mContactModel != null) {
                    // update was definitely called from a user action rather swiping blindly
                    // from the first pag

                    // convert the imageView to a bitmap to save in the bundle
                    BitmapDrawable drawable = (BitmapDrawable) mImageView.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();

                    mContactModel = new ContactModel(firstNameValue, lastName.getText().toString(),
                            phone.getText().toString(), websiteValue, bitmap);

                    // dispatch the event with the updated contact and it's position
                    EventBus.getDefault().post(new OnContactUpdatedEvent(mContactModel, contactModelPosition));
                }

                // reset UI
                mContactModel = null;
                contactModelPosition = -1;
                updateUI();
            }
        });

        takePicture.setOnClickListener(v -> {

            // set up the options for the alert
            CharSequence pictureOptions[] = new CharSequence[] { "Camera", "Photo Library"};

            // create an alert dialogue
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setTitle("Choose to get get a picture from");
            builder.setItems(pictureOptions, (dialog, which) -> {
                switch (which) {

                    // which is which pictureOption item
                    case 0:
                        // use an interfact that delegated to the AppActivity to take the picture as it must be done from the Activity object
                        ((pictureMethods)getContext()).onPictureTaken(mImageView);
                        break;
                    case 1:
                        ((pictureMethods)getContext()).onChoosePictureFromGallery(mImageView);
                        break;
                    default:
                        break;
                }
            });
            // make sure you show the builder.
            builder.show();
        });
    }

    //region help methods
    // update the UI from contactModel

    private void updateUI() {
        // default everything to nothing or empty
        String firstNameValue = "";
        String lastNameValue = "";
        String phoneValue = "";
        String websiteValue = "";
        ImageView imageViewValue = new ImageView(getContext());

        setContactEditiability();
        setupEditButtonVisibility();

        // if there is a contact model then just fill in the values with the values of the mContactModel
        if (mContactModel != null) {
            firstNameValue = mContactModel.getFirstName();
            lastNameValue = mContactModel.getLastName();
            phoneValue = mContactModel.getPhone();
            websiteValue = mContactModel.getWebsite();

            if (mContactModel.getImage() != null) {
                imageViewValue.setImageBitmap(mContactModel.getImage());
            } else {
                // set the default image to the imageViewValue
                ((pictureMethods)getContext()).getDefaultImage(imageViewValue);
            }
        } else {
            // set the default image to the imageViewValue
            ((pictureMethods)getContext()).getDefaultImage(imageViewValue);
        }

        firstName.setText(firstNameValue);
        lastName.setText(lastNameValue);
        phone.setText(phoneValue);
        website.setText(websiteValue);

        // set the imageView to the one that's right for it
        BitmapDrawable drawable = (BitmapDrawable) imageViewValue.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        mImageView.setImageBitmap(bitmap);
    }

    private void setEditingOn() {

        firstName.setFocusable(true);
        firstName.setFocusableInTouchMode(true);
        firstName.setClickable(true);

        lastName.setFocusable(true);
        lastName.setFocusableInTouchMode(true);
        lastName.setClickable(true);

        phone.setFocusable(true);
        phone.setFocusableInTouchMode(true);
        phone.setClickable(true);

        website.setFocusable(true);
        website.setFocusableInTouchMode(true);
        website.setClickable(true);

        mImageView.setFocusable(true);
        mImageView.setFocusableInTouchMode(true);
        mImageView.setClickable(true);

        takePicture.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.VISIBLE);
        update.setVisibility(View.VISIBLE);
        formTitle.setText("Contact Form");
    }

    private void setEdittingOff() {

        firstName.setFocusable(false);
        firstName.setFocusableInTouchMode(false);
        firstName.setClickable(false);

        lastName.setFocusable(false);
        lastName.setFocusableInTouchMode(false);
        lastName.setClickable(false);

        phone.setFocusable(false);
        phone.setFocusableInTouchMode(false);
        phone.setClickable(false);

        website.setFocusable(false);
        website.setFocusableInTouchMode(false);
        website.setClickable(false);

        mImageView.setFocusable(false);
        mImageView.setFocusableInTouchMode(false);
        mImageView.setClickable(false);

        takePicture.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        update.setVisibility(View.INVISIBLE);
        formTitle.setText("Contact");
    }

    private void toggleVisibility() {

        isEditing = !isEditing;
        setContactEditiability();
    }

    private void setContactEditiability(){

        if (isEditing == true) {
            setEditingOn();
        } else {

            setEdittingOff();
        }
    }

    private void setupEditButtonVisibility() {

        if (isCreatingContact == true) {
            edit.setVisibility(View.INVISIBLE);
        } else {
            edit.setVisibility(View.VISIBLE);
        }
    }
    //endregion

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        EventBus.getDefault().unregister(this);
    }

    public interface pictureMethods {
         void onPictureTaken(ImageView imageView);
         void getDefaultImage(ImageView imageView);
         void onChoosePictureFromGallery(ImageView imageView);
    }
}
