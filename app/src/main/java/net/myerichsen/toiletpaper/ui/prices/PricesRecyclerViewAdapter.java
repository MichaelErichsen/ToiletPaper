package net.myerichsen.toiletpaper.ui.prices;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.ui.prices.PriceListFragment.OnListFragmentInteractionListener;
import net.myerichsen.toiletpaper.ui.prices.PriceModel.PriceItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PriceModel.PriceItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class PricesRecyclerViewAdapter extends RecyclerView.Adapter<PricesRecyclerViewAdapter.ViewHolder> {
    private final List<PriceModel.PriceItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public interface OnItemClickListener {
        void onItemClick(PriceItem item);
    }

    /**
     * The view holder objects are managed by an adapter, which you create by extending
     * RecyclerView.Adapter. The adapter creates view holders as needed. The adapter also binds the
     * view holders to their data. It does this by assigning the view holder to a position, and
     * calling the adapter's onBindViewHolder() method. That method uses the view holder's position
     * to determine what the contents should be, based on its list position.
     *
     * @param items List of price items
     * @param listener The OnListFragmentInteractionListener
     */
    public PricesRecyclerViewAdapter(List<PriceItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_prices, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mItemNo.setText(mValues.get(position).itemNo);
        holder.mBrand.setText(mValues.get(position).brand);
        holder.mPackagePrice.setText(mValues.get(position).packagePrice);
        holder.mTimeStamp.setText(mValues.get(position).timeStamp);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * Each view holder is in charge of displaying a single item with a view.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mItemNo;
        final TextView mBrand;
        final TextView mPackagePrice;
        final TextView mTimeStamp;
        PriceModel.PriceItem mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mItemNo = view.findViewById(R.id.plItemNo);
            mBrand = view.findViewById(R.id.plBrand);
            mPackagePrice = view.findViewById(R.id.plPackagePrice);
            mTimeStamp = view.findViewById(R.id.plTimeStamp);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mBrand.getText() + "'";
        }
    }
}
