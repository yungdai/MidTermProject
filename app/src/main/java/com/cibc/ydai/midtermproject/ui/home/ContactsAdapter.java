package com.cibc.ydai.midtermproject.ui.home;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.cibc.ydai.midtermproject.data.contact.ContactModel;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by yungdai on 2018-03-12.
 */

class ContactsAdapter extends RecyclerView.Adapter<ContactsCellHolder> {
    final List<ContactModel> contacts;

    ContactsAdapter() {
        contacts = new ArrayList<>();
    }

    @Override
    public ContactsCellHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
        return new ContactsCellHolder(recyclerView);
    }

    @Override
    public void onBindViewHolder(ContactsCellHolder holder, int position) {
        holder.bind(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
