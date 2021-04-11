package com.geolocatorapplication.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.gson.Gson;

import java.util.List;

import static android.content.ContentValues.TAG;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    Context context;
    List<String>names;
    public RestaurantAdapter(Context context,List<String> names){
        this.context=context;
        this.names=names;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_item,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(names.get(position));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                names.remove(position);
                notifyDataSetChanged();
                SharedPreferences preferences=context.getSharedPreferences("recent",Context.MODE_PRIVATE);
                preferences.edit().clear().apply();

                SharedPreferences.Editor editor=preferences.edit();
                Gson gson=new Gson();
                String json=gson.toJson(names);
                editor.putString("task",json);
                editor.apply();
            }
        });
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                FirebaseFirestore db=FirebaseFirestore.getInstance();
                db.collection("restaurants")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    int flag=0;
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if(document.getId().equals(names.get(position)))
                                        {
                                            flag=1;
                                            AppCompatActivity activity=(AppCompatActivity)view.getContext();
                                            FragmentManager fragmentManager = activity.getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            DetailsFragment NAME = new DetailsFragment();
                                            fragmentTransaction.replace(R.id.frameLayout,NAME);

                                            Bundle args = new Bundle();
                                            args.putString("Location",names.get(position));
                                            NAME.setArguments(args);
                                            fragmentTransaction.commit();
                                        }
                                    }
                                    if(flag==0){
                                        Toast.makeText(context,"Sorry, this restaurant doesn't exist in our database",Toast.LENGTH_SHORT).show();

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView delete;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            delete=itemView.findViewById(R.id.delete);
            name=itemView.findViewById(R.id.name);
        }
    }
}