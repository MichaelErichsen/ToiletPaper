package net.myerichsen.toiletpaper.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.ui.home.BrandListFragment.OnListFragmentInteractionListener;
import net.myerichsen.toiletpaper.ui.home.BrandModel.BrandItem;

import java.util.List;

/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

/**
 * {@link RecyclerView.Adapter} that can display a {@link BrandItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class BrandRecyclerViewAdapter extends RecyclerView.Adapter<BrandRecyclerViewAdapter.ViewHolder> {
    private final List<BrandItem> fbValues;
    private final OnListFragmentInteractionListener fbListener;

    /**
     * The view holder objects are managed by an adapter, which you create by extending
     * RecyclerView.Adapter. The adapter creates view holders as needed. The adapter also binds the
     * view holders to their data. It does this by assigning the view holder to a position, and
     * calling the adapter's onBindViewHolder() method. That method uses the view holder's position
     * to determine what the contents should be, based on its list position.
     *
     * @param items    List of brand items
     * @param listener The OnListFragmentInteractionListener
     */
    public BrandRecyclerViewAdapter(List<BrandItem> items, OnListFragmentInteractionListener listener) {
        fbValues = items;
        fbListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_brand, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.fbItem = fbValues.get(position);
        holder.fbItemNo.setText(fbValues.get(position).itemNo);
        holder.fbBrand.setText(fbValues.get(position).brand);
        holder.fbSupplier.setText(fbValues.get(position).supplier);
        holder.fbTimeStamp.setText(fbValues.get(position).timeStamp);

        holder.fbView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != fbListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    fbListener.onListFragmentInteraction(holder.fbItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return fbValues.size();
    }

    /**
     * Each view holder is in charge of displaying a single item with a view.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final View fbView;
        final TextView fbItemNo;
        final TextView fbBrand;
        final TextView fbSupplier;
        final TextView fbTimeStamp;
        BrandItem fbItem;

        ViewHolder(View view) {
            super(view);
            fbView = view;
            fbItemNo = view.findViewById(R.id.fbItemNo);
            fbBrand = view.findViewById(R.id.fbBrand);
            fbSupplier = view.findViewById(R.id.fbSupplier);
            fbTimeStamp = view.findViewById(R.id.fbTimeStamp);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + fbItemNo.getText() + "'";
        }
    }
}
