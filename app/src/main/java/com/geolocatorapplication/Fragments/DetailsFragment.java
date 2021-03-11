package com.geolocatorapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.geolocatorapplication.R;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {
    String restName;
    String restAdd;
    View view;
    Button SecondButton;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_details, container, false);
        final TextView restaurant = view.findViewById(R.id.restaurant);
        final TextView restaurantsName =  view.findViewById(R.id.restaurantName);
        final TextView restaurantsAddress = view.findViewById(R.id.restaurantAddress);
        TextView restaurantsTimings = view.findViewById(R.id.restaurantTimings);

        final FirebaseFirestore db= FirebaseFirestore.getInstance();
        // get value from search  fragment like doon darbar
        //restaurantsTimings.setText("My Awesome Text");
        final String value = getArguments().getString("Location");

//
//        db.collection("restaurants").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                   // List<String> list = new ArrayList<>();
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        if(document.getId()==value)
//                        {
                            Object a=  db.collection("restaurants").document(value).get();
      //  Log.d(TAG, "HELLO ", a);

       //             }


//                    for (int i = 0; i < list.size(); i++) {
//                        if (value.equals(list.get(i))) {
//
//                            db.collection("restaurants").addSnapshotListener(new EventListener<QuerySnapshot>() {
//                                @Override
//                                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//
//                                    if (e != null) {
//
//                                    }

//                                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
//                                        restName = documentChange.getDocument().getData().get("Name").toString();
//                                        restAdd = documentChange.getDocument().getData().get("Address").toString();


//                                    }
//                                }
//                            });
//
//
//                        }
//                    }
                    restaurant.setText(restName);
                    restaurantsName.setText(restName);
                    restaurantsAddress.setText(restAdd);


//                }
//            }
  //      });
                    return view;
    }
}
//}