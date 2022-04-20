package edu.fsu.cs.wastenotwantnot;

import android.view.ViewGroup;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.annotation.NonNull;

public class ListingListAdapter extends ListAdapter<Listing, ListingViewHolder> {
    public ListingListAdapter(@NonNull DiffUtil.ItemCallback<Listing> diffCallback) {
        super(diffCallback);
    }

    @Override
    public ListingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ListingViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(ListingViewHolder holder, int position) {
        Listing current = getItem(position);
        holder.bind(current.getListingTitle());
    }

    static class ListingDiff extends DiffUtil.ItemCallback<Listing> {

        @Override
        public boolean areItemsTheSame(@NonNull Listing oldItem, @NonNull Listing newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Listing oldItem, @NonNull Listing newItem) {
            return oldItem.getListingTitle().equals(newItem.getListingTitle());
        }
    }
}
