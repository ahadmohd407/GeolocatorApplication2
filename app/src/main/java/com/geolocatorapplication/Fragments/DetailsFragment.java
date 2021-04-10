package com.geolocatorapplication.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geolocatorapplication.Adapters.Restaurants;
import com.geolocatorapplication.Adapters.Reviews;
import com.geolocatorapplication.Adapters.ReviewsAdapter;
import com.geolocatorapplication.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {
    String restName;
    String restAdd;
    View view;
    Button secondButton;
    List<Reviews> res_names;
    ReviewsAdapter reviewsAdapter;
    RecyclerView listofreviews;
    Button revButton;
    Button direction;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
     String value;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details, container, false);
//        final TextView restaurant = view.findViewById(R.id.restaurant);
        final TextView restaurantsName =  view.findViewById(R.id.restaurantName);
        final TextView restaurantsAddress = view.findViewById(R.id.restaurantAddress);
        TextView restaurantsTimings = view.findViewById(R.id.restaurantTimings);
        direction=view.findViewById(R.id.directions);
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        final FirebaseFirestore db= FirebaseFirestore.getInstance();
        value = getArguments().getString("Location");
        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DirectionFragment df = new DirectionFragment();
                fragmentTransaction.replace(R.id.frameLayout,df);

                Bundle args = new Bundle();
                args.putString("Location",value);
                df.setArguments(args);
                fragmentTransaction.commit();
            }
        });
        DocumentReference reference=db.collection("restaurants").document(value);
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                restaurantsAddress.setText(documentSnapshot.getString("Address"));
                restaurantsName.setText(documentSnapshot.getString("Name"));

            }
        });

        revButton = (Button) view.findViewById(R.id.thirdButton);

        revButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser()==null) {
                    Signup_Login nextFrag = new Signup_Login();
                    Bundle args = new Bundle();
                    args.putString("Location",value);
                    nextFrag.setArguments(args);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, nextFrag, "findThisFragment")
                            .commit();
                }
                else{
//                    Signup_Home main = new Signup_Home();
//                    getActivity().getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.frameLayout, main, "findThisFragment")
//                            .commit();
                    showDialog();
                }
            }
        });


        //************************************************************//
        listofreviews=view.findViewById(R.id.listofreviews);
        res_names=new ArrayList<>();
        db.collection(value).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot snapshots:queryDocumentSnapshots){
                    res_names.add(new Reviews(snapshots.getId(),snapshots.getString("review")));
                    reviewsAdapter.notifyDataSetChanged();
                }
            }
        });
        reviewsAdapter=new ReviewsAdapter(getContext(),res_names);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        listofreviews.setLayoutManager(linearLayoutManager);
        listofreviews.setAdapter(reviewsAdapter);




        //****************************************************//
        return view;
    }
    public void showDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View view1=getLayoutInflater().inflate(R.layout.layout_dialog,null);
        final EditText name=view1.findViewById(R.id.edit_Name);
        final EditText review=view1.findViewById(R.id.edit_Review);
        Button submit=view1.findViewById(R.id.submitReview);
        Button cancel=view1.findViewById(R.id.cancel);
        builder.setView(view1);
        final AlertDialog dialog=builder.create();
        dialog.setCanceledOnTouchOutside(false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(name.getText().toString().isEmpty()&&review.getText().toString().isEmpty())){
                    String rev=review.getText().toString();
                    String username=name.getText().toString();
                    HashMap<String,String>data=new HashMap<>();
                    data.put("review",rev);
                    db.collection(value).document(username).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isComplete()){
                                Toast.makeText(getContext(),"Review added",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                            Toast.makeText(getContext(),"Some error ocuured. Please try again later",Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
}





