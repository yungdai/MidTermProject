package com.cibc.ydai.midtermproject.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cibc.ydai.midtermproject.R;
import com.cibc.ydai.midtermproject.data.contact.ContactModel;
import com.cibc.ydai.midtermproject.ui.home.events.OnContactEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by yungdai on 2018-03-12.
 */

class ContactsCellHolder extends RecyclerView.ViewHolder {
    private TextView text;
    private ContactModel mContactModel;

    ContactsCellHolder(ViewGroup recyclerView) {
        super(LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.home_contacts_cell, recyclerView, false));

        text = (TextView) itemView;
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContactModel != null) {
                    // dispatch blindly the model and it's position
                    EventBus.getDefault().post(new OnContactEvent(mContactModel, getAdapterPosition()));
                }
            }
        });
    }

    void bind(ContactModel contactModel) {
        this.mContactModel = contactModel;

        if (contactModel != null) {
            String textValue = contactModel.getFirstName() + " " + contactModel.getLastName();
            text.setText(textValue);
        }
    }
}
