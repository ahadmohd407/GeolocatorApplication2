package com.geolocatorapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.geolocatorapplication.R;
import com.google.firebase.auth.FirebaseAuth;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Signup_Home#} factory method to
 * create an instance of this fragment.
 */
public class Signup_Home extends Fragment implements View.OnClickListener
{
    Button btnLogout;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 final Bundle savedInstanceState) {
            // Inflate the layout for this fragment
                    View view=inflater.inflate(R.layout.fragment_signup__home, container, false);
                    btnLogout = (Button)view.findViewById(R.id.logout);
                    FirebaseAuth mFirebaseAuth;
                    FirebaseAuth.AuthStateListener mAuthStateListener;
                    btnLogout.setOnClickListener((View.OnClickListener) this);
                    return view;
                }

    @Override
        public void onClick(View view) {
        FirebaseAuth.getInstance().signOut();
//                    FragmentManager fragmentManager = getFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    Signup_Login NAME = new Signup_Login();
//                    fragmentTransaction.replace(R.id.frameLayout,NAME);
        Signup_Login nextFrag= new Signup_Login();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, nextFrag,"Find Next Fragment")
                .addToBackStack(null)
                .commit();

    }


}