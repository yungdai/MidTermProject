package com.cibc.ydai.midtermproject;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.cibc.ydai.midtermproject.ui.home.Contact;


/**
 * Created by yungdai on 2018-03-12.
 */


// the implimentation makes a strict implimentation that only Contact can use for onPictureTaken.
public class AppActivity extends AppCompatActivity implements Contact.pictureMethods {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appactivity);

    }

    // Set intent key for the camera request
    private static final int CAMERA_REQUEST = 10000;
    private ImageView mImageView;

    public void onPictureTaken(ImageView imageView) {
        mImageView = imageView;
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public void getDefaultImage(ImageView imageView) {

        mImageView = imageView;
        mImageView.setImageDrawable(getDrawable(android.R.drawable.ic_menu_camera));
    }

    // when you get an activity back from the camera intent
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mImageView.setImageBitmap(photo);
        }
    }
}
