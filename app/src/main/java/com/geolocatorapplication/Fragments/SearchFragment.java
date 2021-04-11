package com.geolocatorapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geolocatorapplication.Adapters.RestaurantAdapter;
import com.geolocatorapplication.Adapters.Restaurants;
import com.geolocatorapplication.Adapters.SearchAdapter;
import com.geolocatorapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class SearchFragment extends Fragment {

    SearchView searchView;
    List<String> names;
    RecyclerView recyclerView,all_restaurants;
    RestaurantAdapter adapter;
    SearchAdapter searchAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<Restaurants>res_names;
    FirebaseFirestore db;
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_search, container, false);
        searchView=view.findViewById(R.id.searchView);
        all_restaurants=view.findViewById(R.id.all_restaurants);
        loadData();
        res_names=new ArrayList<>();
        searchAdapter=new SearchAdapter(getContext(),res_names);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        all_restaurants.setLayoutManager(linearLayoutManager);
        all_restaurants.setAdapter(searchAdapter);
        db= FirebaseFirestore.getInstance();
        db.collection("restaurants").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot snapshots:queryDocumentSnapshots){
                    res_names.add(new Restaurants(snapshots.getId(),"4/5"));
                    searchAdapter.notifyDataSetChanged();
                }
            }
        });
//        res_names.add(new Restaurants("Doon Darbar","3/5"));
//        res_names.add(new Restaurants("Cafe coffee day","4/5"));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                    db.collection("restaurants")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        int flag=0;
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if(document.getId().equals(query))
                                            {
                                                flag=1;
                                                FragmentManager fragmentManager = getFragmentManager();
                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                DetailsFragment NAME = new DetailsFragment();
                                                fragmentTransaction.replace(R.id.frameLayout,NAME);

                                                Bundle args = new Bundle();
                                                args.putString("Location",query);
                                                NAME.setArguments(args);
                                                fragmentTransaction.commit();
                                            }
                                        }
                                        if(flag==0){
                                            Toast.makeText(getContext(),"Sorry, this restaurant doesn't exist in our database",Toast.LENGTH_SHORT).show();

                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                if(!names.contains(query)) {
                    names.add(query);
                    adapter.notifyDataSetChanged();
                    saveData();
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
    private void saveData(){
        sharedPreferences=getActivity().getSharedPreferences("recent", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(names);
        editor.putString("task",json);
        editor.apply();

    }
    private  void loadData(){
        sharedPreferences=getActivity().getSharedPreferences("recent", Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("task",null);
        Type type=new TypeToken<ArrayList<String>>(){}.getType();
        names=gson.fromJson(json,type);
        if(names==null){
            names=new ArrayList<>();
        }
    }
}