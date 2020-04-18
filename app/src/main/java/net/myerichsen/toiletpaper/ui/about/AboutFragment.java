package net.myerichsen.toiletpaper.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import net.myerichsen.toiletpaper.R;

public class AboutFragment extends Fragment {
    private View root;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_about, container, false);

        AppCompatImageButton testBtn = root.findViewById(R.id.testBtn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadInitialData();
            }
        });
        return root;
    }

    private void loadInitialData() {
        // TODO Load initial data into both tables
        Snackbar snackbar = Snackbar
                .make(getActivity().findViewById(android.R.id.content), "Her skal initielle data loades", Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
