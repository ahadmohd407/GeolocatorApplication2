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

import com.geolocatorapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Signup_Login
 * create an instance of this fragment.
 */
public class Signup_Login extends Fragment {
    EditText emailId;
    EditText password;
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;

    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_signup__login, container, false);


        mFirebaseAuth = FirebaseAuth.getInstance();
            emailId = view.findViewById(R.id.editText);
            password = view.findViewById(R.id.editText2);
            btnSignIn = view.findViewById(R.id.button2);
            tvSignUp = view.findViewById(R.id.textView);

            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                    if( mFirebaseUser != null ){
                        Toast.makeText(getActivity(),"You are logged in",Toast.LENGTH_SHORT).show();
//                        FragmentManager fragmentManager = getFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        Signup_Home NAME = new Signup_Home();
//                        fragmentTransaction.replace(R.id.frameLayout,NAME);

//                        Signup_Home nextFrag= new Signup_Home();
//                        getActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.frameLayout, nextFrag, "findThisFragment")
//                                .addToBackStack(null)
//                                .commit();
                        getActivity(). getFragmentManager(). popBackStack();

                    }
                    else{
                        Toast.makeText(getActivity(),"Please Login",Toast.LENGTH_SHORT).show();
                    }
                }
            };

            btnSignIn.setOnClickListener(new View.OnClickListener() {
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
                        mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(getActivity(),"Login Error, Please Login Again",Toast.LENGTH_SHORT).show();
                                }
                                else{
//                                    FragmentManager fragmentManager = getFragmentManager();
//                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                    Signup_Home NAME = new Signup_Home();
//                                    fragmentTransaction.replace(R.id.frameLayout,NAME);
//                                    Signup_Home nextFrag= new Signup_Home();
//                                    getActivity().getSupportFragmentManager().beginTransaction()
//                                            .replace(R.id.frameLayout, nextFrag, "findThisFragment")
//                                            .commit();
                                    getActivity(). getFragmentManager(). popBackStack();


                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(getActivity(),"Error Occurred!",Toast.LENGTH_SHORT).show();

                    }

                }
            });

            tvSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    FragmentManager fragmentManager = getFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    Signup_Main NAME = new Signup_Main();
//                    fragmentTransaction.replace(R.id.frameLayout,NAME);

                    Signup_Main nextFrag= new Signup_Main();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();

                }
            });
        return view;}

        @Override
        public void onStart() {
            super.onStart();
            mFirebaseAuth.addAuthStateListener(mAuthStateListener);
        }
    }
