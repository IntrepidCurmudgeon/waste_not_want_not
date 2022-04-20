package edu.fsu.cs.wastenotwantnot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ListingViewHolder extends RecyclerView.ViewHolder{
    private final TextView listingItemView;

    private ListingViewHolder(View itemView) {
        super(itemView);
        listingItemView = itemView.findViewById(R.id.textView);
    }

    public void bind(String text) {
        listingItemView.setText(text);
    }

    static ListingViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new ListingViewHolder(view);
    }
}
