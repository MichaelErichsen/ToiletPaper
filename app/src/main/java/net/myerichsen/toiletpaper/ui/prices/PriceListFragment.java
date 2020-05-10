package net.myerichsen.toiletpaper.ui.prices;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.TPDbAdapter;
import net.myerichsen.toiletpaper.ui.home.HomeFragment;
import net.myerichsen.toiletpaper.ui.prices.dummy.DummyContent;
import net.myerichsen.toiletpaper.ui.prices.dummy.DummyContent.DummyItem;

import java.util.Objects;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */

/**
 * TODO Display with suppliers, all prices and timestamp.
 */
public class PriceListFragment extends Fragment {
    private String itemNo;
    private String brand;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 4;
    private OnListFragmentInteractionListener mListener;
    private View root;
    private Context context;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PriceListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PriceListFragment newInstance(int columnCount) {
        PriceListFragment fragment = new PriceListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_prices_list, container, false);
        context = getContext();

        Button button = root.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PriceListFragmentDirections.ActionNavPricesListToNavPriceGraph action =
                            PriceListFragmentDirections.actionNavPricesListToNavPriceGraph(itemNo, brand);
                    Navigation.findNavController(v).navigate(action);
                } catch (Exception e) {
                    Snackbar snackbar = Snackbar
                            .make(requireActivity().findViewById(android.R.id.content), Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });

        // Set the adapter
        if (root instanceof RecyclerView) {
            Context context = root.getContext();
            RecyclerView recyclerView = (RecyclerView) root;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyPricesRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TPDbAdapter adapter = new TPDbAdapter(context);

        if (getArguments() != null) {
            itemNo = getArguments().getString(HomeFragment.ITEM_NO);
            brand = getArguments().getString(HomeFragment.BRAND);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
