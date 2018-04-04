package com.cibc.ydai.midtermproject.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.cibc.ydai.midtermproject.R;

import com.cibc.ydai.midtermproject.data.contact.ContactModel;
import com.cibc.ydai.midtermproject.ui.home.events.OnContactEvent;
import org.greenrobot.eventbus.EventBus;


/**
 * Created by yungdai on 2018-03-12.
 */

public class ContactsCellHolder extends RecyclerView.ViewHolder   {

    private TextView nameText;
    private TextView phoneNumberText;
    private Button openWebsite;
    private ContactModel mContactModel;
    private ImageView mImageView;
    public WebView mWebView;

    ContactsCellHolder(ViewGroup recyclerView) {
        super(LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.home_contacts_cell, recyclerView, false));

        // instantiate the view items in the cell holder
        nameText = itemView.findViewById(R.id.name_text);
        phoneNumberText = itemView.findViewById(R.id.phoneNumber_text);
        openWebsite = itemView.findViewById(R.id.open_website);
        mImageView = itemView.findViewById(R.id.holderImage);
        mWebView = itemView.findViewById(R.id.webView);

        mWebView.setVisibility(View.INVISIBLE);
        // set it so if you click the view itself to go to the contact
        nameText.setOnClickListener(v -> {
            if (mContactModel != null) {
                // dispatch blindly the model and it's position
                EventBus.getDefault().post(new OnContactEvent(mContactModel, getAdapterPosition(), false, false));

            }
        });

        openWebsite.setOnClickListener(v -> {

            String webAddress = mContactModel.getWebsite().toString();

            if (!webAddress.isEmpty()) {

                // there is a URL
                if (!webAddress.startsWith("http://")) {

                    webAddress = String.format("http://%s", webAddress);
                }

                mWebView.setVisibility(View.VISIBLE);
                WebSettings webSettings = mWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                mWebView.loadUrl(webAddress);
                mWebView.setWebViewClient(new WebViewClient());

//                ValueAnimator animator = ValueAnimator.ofInt(0, 3000);
//                animator.addUpdateListener(valueAnimator -> {
//
//                    int val = (Integer) valueAnimator.getAnimatedValue();
//                    ViewGroup.LayoutParams layoutParams = mWebView.getLayoutParams();
//                    layoutParams.height = val;
//
//                    mWebView.setLayoutParams(layoutParams);
//                });
//
//                animator.setDuration(500);
//                animator.start();
                // go to a website
//                goToWebSiteWith(v, webAddress);
            }
        });

    }



    private void goToWebSiteWith(View v, String webAddress) {
        // show the website on click
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(webAddress));
        v.getContext().startActivity(intent);
    }

    void bind(ContactModel contactModel) {
        this.mContactModel = contactModel;

        if (contactModel != null) {
            String nameValue = contactModel.getFirstName() + " " + contactModel.getLastName();
            String phoneNumberValue = contactModel.getPhone();
            nameText.setText(nameValue);
            phoneNumberText.setText(phoneNumberValue);

            if (contactModel.getImage() != null) {
                mImageView.setImageBitmap(contactModel.getImage());
            }
        }
    }
}
