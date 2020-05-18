package net.myerichsen.toiletpaper.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.ui.home.ItemNoModel.ItemNoItem;

import java.util.Objects;

import static net.myerichsen.toiletpaper.ui.home.HomeFragment.ITEM_NO;

/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ItemNoListFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private String itemNo;
    private Snackbar snackbar;

    private int mColumnCount = 2;
    private OnListFragmentInteractionListener fiListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemNoListFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment
     */
    public static ItemNoListFragment newInstance(int columnCount) {
        ItemNoListFragment fragment = new ItemNoListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    private static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() == null) {
            return;
        }

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            itemNo = getArguments().getString(HomeFragment.ITEM_NO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_item_no_list, container, false);
        Context context = getContext();
        View snackView = requireActivity().findViewById(android.R.id.content);

        // Set the adapter
        if (root instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) root;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            ItemNoModel itemNoModel = new ItemNoModel(context, itemNo);
            if (itemNoModel.ITEMS.size() == 0) {
                snackbar = Snackbar
                        .make(snackView,
                                R.string.itemno_not_found, Snackbar.LENGTH_LONG);
                snackbar.show();
                requireActivity().onBackPressed();
                return null;
            } else if (itemNoModel.ITEMS.size() == 1) {
                Bundle result = new Bundle();
                result.putString(ITEM_NO, itemNoModel.ITEMS.get(0).itemNo);
                getActivity().getSupportFragmentManager().setFragmentResult("itemNoRequestKey", result);
                requireActivity().onBackPressed();
                return null;
            }
            recyclerView.setAdapter(new ItemNoRecyclerViewAdapter(itemNoModel.ITEMS, fiListener));
        }
        hideSoftKeyboard(requireActivity());

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            itemNo = getArguments().getString(HomeFragment.ITEM_NO);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            fiListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fiListener = null;
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
        void onListFragmentInteraction(ItemNoItem item);
    }
}
