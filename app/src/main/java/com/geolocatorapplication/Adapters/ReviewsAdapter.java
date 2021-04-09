package com.geolocatorapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.geolocatorapplication.R;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

        private List<Reviews> mData;
      private Context context;


        // data is passed into the constructor
        public ReviewsAdapter(Context context, List<Reviews> data) {
                      this.mData = data;
                      this.context=context;
        }

        // inflates the row layout from xml when needed
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_reviews,null,false);
            return new ViewHolder(view);
        }

//    @Override
//    public void onBindViewHolder(@NonNull ReviewsAdapter.ViewHolder holder, int position) {
//
//    }

    // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String name = mData.get(position).getRes_name();
            String review=mData.get(position).getreviews();
            holder.reviewersname.setText(name);
            holder.reviewersreview.setText(review);
        }

        // total number of rows
        @Override
        public int getItemCount() {
            return mData.size();
        }


        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder  {
            TextView reviewersname;
            TextView reviewersreview;

           public ViewHolder(View itemView) {
                super(itemView);
                reviewersname = itemView.findViewById(R.id.reviewers_name);
                reviewersreview=itemView.findViewById(R.id.reviewers_review);

            }


        }


    }
