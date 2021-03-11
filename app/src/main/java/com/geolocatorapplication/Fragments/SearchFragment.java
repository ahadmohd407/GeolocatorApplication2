package com.geolocatorapplication.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geolocatorapplication.Adapters.RestaurantAdapter;
import com.geolocatorapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class SearchFragment extends Fragment {

    SearchView searchView;
    List<String> names;
    RecyclerView recyclerView;
    RestaurantAdapter adapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_search, container, false);
        searchView=view.findViewById(R.id.searchView);
        names=new ArrayList<>();
        names.add("Restaurant-1");
        names.add("Restaurant-2");
        names.add("Restaurant-3");
        names.add("Restaurant-4");
        
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                if(!names.contains(query)) {
                    //1.check in database
                    FirebaseFirestore db= FirebaseFirestore.getInstance();

                    db.collection("restaurants")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if(document.getId().equals(query))
                                            {

                                                FragmentManager fragmentManager = getFragmentManager();
                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                DetailsFragment NAME = new DetailsFragment();
                                                fragmentTransaction.replace(R.id.frameLayout,NAME);

                                                Bundle args = new Bundle();
                                                args.putString("Location",query);
                                                NAME.setArguments(args);
                                                fragmentTransaction.commit();
//Inflate the fragment
                                                //getFragmentManager().beginTransaction().add(R.id.frameLayout, NAME).commit();


                                                //2.if found put marker on the map
                                                //Toast.makeText(getActivity(), "Found", Toast.LENGTH_LONG).show();
                                            //2-a. fetch latitute and longitude and put it in map

                                            }
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });

                    //3.Not foun-->Toast Show
                    names.add(query);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        recyclerView=view.findViewById(R.id.recyclerView);
        adapter=new RestaurantAdapter(getContext(),names);
        GridLayoutManager manager=new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        return view;
    }
}