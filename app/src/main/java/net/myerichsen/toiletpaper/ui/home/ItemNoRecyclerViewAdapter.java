package net.myerichsen.toiletpaper.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.ui.home.ItemNoListFragment.OnListFragmentInteractionListener;
import net.myerichsen.toiletpaper.ui.home.ItemNoModel.ItemNoItem;

import java.util.List;

/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

/**
 * {@link RecyclerView.Adapter} that can display a {@link ItemNoItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class ItemNoRecyclerViewAdapter extends RecyclerView.Adapter<ItemNoRecyclerViewAdapter.ViewHolder> {
    private final List<ItemNoItem> fiValues;
    private final OnListFragmentInteractionListener fiListener;

    /**
     * The view holder objects are managed by an adapter, which you create by extending
     * RecyclerView.Adapter. The adapter creates view holders as needed. The adapter also binds the
     * view holders to their data. It does this by assigning the view holder to a position, and
     * calling the adapter's onBindViewHolder() method. That method uses the view holder's position
     * to determine what the contents should be, based on its list position.
     *
     * @param items    List of item number items
     * @param listener The OnListFragmentInteractionListener
     */
    public ItemNoRecyclerViewAdapter(List<ItemNoItem> items, OnListFragmentInteractionListener listener) {
        fiValues = items;
        fiListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_no, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.fiItem = fiValues.get(position);
        holder.fiItemNo.setText(fiValues.get(position).itemNo);
        holder.fiBrand.setText(fiValues.get(position).brand);
        holder.fiSupplier.setText(fiValues.get(position).supplier);
        holder.fiTimeStamp.setText(fiValues.get(position).timeStamp);

        holder.fiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != fiListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    fiListener.onListFragmentInteraction(holder.fiItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return fiValues.size();
    }


//    public interface OnItemClickListener {
//        void onItemClick(ItemNoItem item);
//    }

    /**
     * Each view holder is in charge of displaying a single item with a view.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final View fiView;
        final TextView fiItemNo;
        final TextView fiBrand;
        final TextView fiSupplier;
        final TextView fiTimeStamp;
        ItemNoItem fiItem;

        ViewHolder(View view) {
            super(view);
            fiView = view;
            fiItemNo = view.findViewById(R.id.fiItemNo);
            fiBrand = view.findViewById(R.id.fiBrand);
            fiSupplier = view.findViewById(R.id.fiSupplier);
            fiTimeStamp = view.findViewById(R.id.fiTimeStamp);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + fiBrand.getText() + "'";
        }
    }
}
