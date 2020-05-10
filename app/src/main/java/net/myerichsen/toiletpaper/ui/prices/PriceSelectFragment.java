package net.myerichsen.toiletpaper.ui.prices;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import net.myerichsen.toiletpaper.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PriceSelectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * TODO Select all product rows for the same item no and brand.
 */
public class PriceSelectFragment extends Fragment {
    private View root;
    private EditText pItemNoEditText;
    private EditText pBrandEditText;

    public PriceSelectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static PriceSelectFragment newInstance() {
        PriceSelectFragment fragment = new PriceSelectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_price_select, container, false);
        Context context = getContext();

        pItemNoEditText = root.findViewById(R.id.pItemNoEditText);
        pBrandEditText = root.findViewById(R.id.pBrandEditText);
        Button priceSelectionBtn = root.findViewById(R.id.priceSelectionBtn);


        priceSelectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PriceSelectFragmentDirections.ActionNavPriceSelectToNavPricesList action =
                            PriceSelectFragmentDirections.actionNavPriceSelectToNavPricesList(pItemNoEditText.getText().toString(),
                                    pBrandEditText.getText().toString());
                    Navigation.findNavController(v).navigate(action);
                } catch (Exception e) {
                    Snackbar snackbar = Snackbar
                            .make(requireActivity().findViewById(android.R.id.content), Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }


            }
        });
        return root;
    }
}
