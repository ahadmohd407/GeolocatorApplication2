package com.geolocatorapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.geolocatorapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class Signup_Main extends Fragment {

        EditText emailId, password;
        Button btnSignUp;
        TextView tvSignIn;
        FirebaseAuth mFirebaseAuth;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_signup__main, container, false);


        mFirebaseAuth = FirebaseAuth.getInstance();
            emailId = view.findViewById(R.id.editText);
            password = view.findViewById(R.id.editText2);
            btnSignUp = view.findViewById(R.id.button2);
            tvSignIn = view.findViewById(R.id.textView);
            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = emailId.getText().toString();
                    String pwd = password.getText().toString();
                    if(email.isEmpty()){
                        emailId.setError("Please enter email id");
                        emailId.requestFocus();
                    }
                    else  if(pwd.isEmpty()){
                        password.setError("Please enter your password");
                        password.requestFocus();
                    }
                    else  if(email.isEmpty() && pwd.isEmpty()){
                        Toast.makeText(getActivity(),"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                    }
                    else  if(!(email.isEmpty() && pwd.isEmpty())){
                        mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(getActivity(),"SignUp Unsuccessful, Please Try Again",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    Signup_Home NAME = new Signup_Home();
                                    fragmentTransaction.replace(R.id.frameLayout,NAME);

                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(getActivity(),"Error Occurred!",Toast.LENGTH_SHORT).show();

                    }
                }
            });

            tvSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Signup_Home NAME = new Signup_Home();
                    fragmentTransaction.replace(R.id.frameLayout,NAME);
                }
            });
        return  view;}
    }
