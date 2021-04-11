package com.geolocatorapplication.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.geolocatorapplication.Fragments.DetailsFragment;
import com.geolocatorapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static android.content.ContentValues.TAG;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;
    private List<Restaurants>name;
    public SearchAdapter(Context context, List<Restaurants> name){
        this.context=context;
        this.name=name;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.restaurant_item,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(name.get(position).getRes_name());
        holder.ratings.setText(name.get(position).getRatings());
        holder.res_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AppCompatActivity activity=(AppCompatActivity)view.getContext();
                FragmentManager fragmentManager =activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DetailsFragment NAME = new DetailsFragment();
                fragmentTransaction.replace(R.id.frameLayout,NAME);
                Bundle args = new Bundle();
                args.putString("Location",name.get(position).getRes_name());
                NAME.setArguments(args);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView ratings;
        CardView res_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.res_name);
            ratings=itemView.findViewById(R.id.ratings);
            res_item=itemView.findViewById(R.id.restaurant_layout);
        }
    }
}
