package net.myerichsen.toiletpaper.ui.prices;

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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.TPDbAdapter;
import net.myerichsen.toiletpaper.ui.home.HomeFragment;
import net.myerichsen.toiletpaper.ui.products.ProductModel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

/**
 * Display a graph with a line for each price at viewable scales along a time line
 */
public class PriceGraphFragment extends Fragment {
    private String itemNo;
    private String brand;

    /**
     * No arg constructor
     */
    public PriceGraphFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param itemNo Iten number
     * @param brand  Brand
     * @return A new instance of the class
     */
    public static PriceGraphFragment newInstance(String itemNo, String brand) {
        PriceGraphFragment fragment = new PriceGraphFragment();
        Bundle args = new Bundle();
        args.putString(HomeFragment.ITEM_NO, itemNo);
        args.putString(HomeFragment.BRAND, brand);
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

    /**
     * @param savedInstanceState The state of the saved instance
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemNo = getArguments().getString(HomeFragment.ITEM_NO);
            brand = getArguments().getString(HomeFragment.BRAND);
        }
    }

    /**
     * @param inflater           Inflater
     * @param container          Container
     * @param savedInstanceState The state of the saved instance
     * @return The view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_price_graph, container, false);
    }

    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();
        TPDbAdapter adapter = new TPDbAdapter(context);
        List<ProductModel> lpm;

        if ((itemNo != null) && (!itemNo.equals(""))) {
            lpm = adapter.getProductModels("ITEM_NO=?", itemNo, "TIME_STAMP");
        } else if ((brand != null) && (!brand.equals(""))) {
            lpm = adapter.getProductModels("BRAND=?=?", brand, "TIME_STAMP");
        } else return;

        GraphView graph = view.findViewById(R.id.priceGraph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt;
        Date date;

        for (int i = 0; i < lpm.size(); i++) {
            ldt = LocalDateTime.parse(lpm.get(i).getTimestamp(), f);
            date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

            DataPoint dp = new DataPoint(date, Double.parseDouble(String.valueOf(lpm.get(i).getPackagePrice())));
            series.appendData(dp, true, 10, true);
        }

        graph.addSeries(series);

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(4); // only 4 because of the space
        graph.getGridLabelRenderer().setHumanRounding(false);

        // activate horizontal zooming and scrolling
        graph.getViewport().setScalable(true);

        // activate horizontal scrolling
        graph.getViewport().setScrollable(true);

        // activate horizontal and vertical zooming and scrolling
        graph.getViewport().setScalableY(true);

        // activate vertical scrolling
        graph.getViewport().setScrollableY(true);

        hideSoftKeyboard(requireActivity());
    }
}