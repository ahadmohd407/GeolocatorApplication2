package com.geolocatorapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.geolocatorapplication.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

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
    ImageView direction;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details, container, false);
//        final TextView restaurant = view.findViewById(R.id.restaurant);
        final TextView restaurantsName =  view.findViewById(R.id.restaurantName);
        final TextView restaurantsAddress = view.findViewById(R.id.restaurantAddress);
        TextView restaurantsTimings = view.findViewById(R.id.restaurantTimings);
        final String value = getArguments().getString("Location");
        direction=view.findViewById(R.id.directions);
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
        final FirebaseFirestore db= FirebaseFirestore.getInstance();

        DocumentReference reference=db.collection("restaurants").document(value);
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                restaurantsAddress.setText(documentSnapshot.getString("Address"));
                restaurantsName.setText(documentSnapshot.getString("Name"));

            }
        });

                    return view;
    }
}
