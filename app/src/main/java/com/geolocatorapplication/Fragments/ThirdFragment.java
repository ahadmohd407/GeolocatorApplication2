package com.geolocatorapplication.Fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.geolocatorapplication.R;


public class ThirdFragment extends DialogFragment implements View.OnClickListener {
    Button btnEmbedDialogFragment, btnDialogFragment, btnDialogFragmentFullScreen, btnAlertDialogFragment;
    TextView textView;

    View view;
    Button firstButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_third, container, false);
// get the reference of Button
        textView = view.findViewById(R.id.textView);
        btnEmbedDialogFragment = view.findViewById(R.id.btnEmbedDialogFragment);
        btnDialogFragment = view.findViewById(R.id.btnDialogFragment);
        btnDialogFragmentFullScreen = view.findViewById(R.id.btnDialogFragmentFullScreen);
        btnAlertDialogFragment = view.findViewById(R.id.btnAlertDialogFragment);

        btnEmbedDialogFragment.setOnClickListener(this);
        btnDialogFragment.setOnClickListener(this);
        btnDialogFragmentFullScreen.setOnClickListener(this);
        btnAlertDialogFragment.setOnClickListener(this);

        firstButton = (Button) view.findViewById(R.id.secondButton);
// perform setOnClickListener on first Button



        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnEmbedDialogFragment:
                MyDialogFragment dialogFragment = new MyDialogFragment();

                FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.replace(R.id.frameLayout, dialogFragment);
                ft.commit();
                break;

            case R.id.btnDialogFragment:
                dialogFragment = new MyDialogFragment();

                Bundle bundle = new Bundle();
                bundle.putBoolean("notAlertDialog", true);

                dialogFragment.setArguments(bundle);

                ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);


                dialogFragment.show(ft, "dialog");
                break;

            case R.id.btnDialogFragmentFullScreen:
                dialogFragment = new MyDialogFragment();

                bundle = new Bundle();
                bundle.putString("email", "xyz@gmail.com");
                bundle.putBoolean("fullScreen", true);
                bundle.putBoolean("notAlertDialog", true);

                dialogFragment.setArguments(bundle);


                ft = getFragmentManager().beginTransaction();
                prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);


                dialogFragment.show(ft, "dialog");
                break;

            case R.id.btnAlertDialogFragment:
                dialogFragment = new MyDialogFragment();


                ft = getFragmentManager().beginTransaction();
                prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);


                dialogFragment.show(ft, "dialog");
                break;
        }
    }
}