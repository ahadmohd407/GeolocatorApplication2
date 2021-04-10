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
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;


public class ThirdFragment extends Fragment {

    TextView user_text;
    Button signin,signout;
    FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      View  view = inflater.inflate(R.layout.fragment_third, container, false);
      signin=view.findViewById(R.id.sign_in);
      signout=view.findViewById(R.id.sign_out);
      user_text=view.findViewById(R.id.user_text);
      auth=FirebaseAuth.getInstance();
      if(auth.getCurrentUser()==null){
          user_text.setVisibility(View.GONE);
          signin.setVisibility(View.VISIBLE);
          signout.setVisibility(View.GONE);
      }
      else{
          user_text.setText("Welcome "+auth.getCurrentUser().getEmail());
          user_text.setVisibility(View.VISIBLE);
          signin.setVisibility(View.GONE);
          signout.setVisibility(View.VISIBLE);
      }
      signout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              auth.signOut();
              signin.setVisibility(View.VISIBLE);
              signout.setVisibility(View.GONE);
              user_text.setVisibility(View.GONE);
          }
      });
      signin.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Signup_Main main=new Signup_Main();
              getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,main).commit();
          }
      });
        return view;
    }


}