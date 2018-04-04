package com.cibc.ydai.midtermproject.ui.home;


import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import com.cibc.ydai.midtermproject.data.contact.ContactModel;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by yungdai on 2018-03-12.
 */

class ContactsAdapter extends RecyclerView.Adapter<ContactsCellHolder> {

    final List<ContactModel> contacts;
    int mExpandedPosition = -1;

    ContactsAdapter() {
        contacts = new ArrayList<>();
    }


    @Override
    public ContactsCellHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
        return new ContactsCellHolder(recyclerView);

    }


    // these are the two important methods to bind the data to the cells.
    @Override
    public void onBindViewHolder(ContactsCellHolder holder, int position) {
        holder.bind(contacts.get(position));

        final boolean isExpanded = position == mExpandedPosition;

        holder.mWebView.setVisibility(isExpanded? View.VISIBLE: View.GONE);

        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(v -> {

            mExpandedPosition = isExpanded ? -1 : position;
            notifyItemChanged(position);

        });

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
