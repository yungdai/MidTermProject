package com.cibc.ydai.midtermproject;

import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.cibc.ydai.midtermproject.ui.home.Contact;

import java.util.ArrayList;


/**
 * Created by yungdai on 2018-03-12.
 */

public class AppActivity extends AppCompatActivity implements Contact.onPictureTaken {

    // Set intent key for the camera request
    private static final int CAMERA_REQUEST = 10000;
    private ImageView mImageView;

    public static void hideKeyboard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = null;

        if (inputMethodManager == null) {
            return;
        }

        if (context instanceof Activity) {
            view = ((Activity) context).getCurrentFocus();
        }

        IBinder windowToken = view != null ? view.getWindowToken() : null;

        if (windowToken != null) {
            inputMethodManager.hideSoftInputFromInputMethod(windowToken, 0);
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
        }
    }

    /**
     * Show soft keyboard
     *
     * @param context context If null will skip
     * @param view focused view.
     */
    public static void showKeyboard(Context context, @NonNull View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(view, 0);
        }
    }


    public void onPictureTaken(ImageView imageView) {
        mImageView = imageView;
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appactivity);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mImageView.setImageBitmap(photo);
        }
    }
}
