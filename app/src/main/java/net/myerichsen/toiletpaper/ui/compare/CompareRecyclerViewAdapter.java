package net.myerichsen.toiletpaper.ui.compare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.ui.compare.CompareListFragment.OnListFragmentInteractionListener;
import net.myerichsen.toiletpaper.ui.compare.CompareModel.CompareItem;

import java.util.List;

/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

/**
 * {@link RecyclerView.Adapter} that can display a {@link CompareItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class CompareRecyclerViewAdapter extends RecyclerView.Adapter<CompareRecyclerViewAdapter.ViewHolder> {
    private final List<CompareItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    /**
     * The view holder objects are managed by an adapter, which you create by extending
     * RecyclerView.Adapter. The adapter creates view holders as needed. The adapter also binds the
     * view holders to their data. It does this by assigning the view holder to a position, and
     * calling the adapter's onBindViewHolder() method. That method uses the view holder's position
     * to determine what the contents should be, based on its list position.
     *
     * @param items    List of price items
     * @param listener The OnListFragmentInteractionListener
     */
    public CompareRecyclerViewAdapter(List<CompareItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_compare_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.clItem = mValues.get(position);
        holder.clItemNoView.setText(mValues.get(position).clItemNo);
        holder.clBrandView.setText(mValues.get(position).clBrand);
        holder.clKiloPriceView.setText(mValues.get(position).clKiloPrice);
        holder.clMeterPriceView.setText(mValues.get(position).clMeterPrice);

        holder.clView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.clItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final View clView;
        final TextView clItemNoView;
        final TextView clBrandView;
        final TextView clKiloPriceView;
        final TextView clMeterPriceView;
        CompareItem clItem;

        ViewHolder(View view) {
            super(view);
            clView = view;
            clItemNoView = view.findViewById(R.id.clItemNo);
            clBrandView = view.findViewById(R.id.clBrand);
            clKiloPriceView = view.findViewById(R.id.clKiloPrice);
            clMeterPriceView = view.findViewById(R.id.clMeterPrice);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + clBrandView.getText() + "'";
        }
    }
}
