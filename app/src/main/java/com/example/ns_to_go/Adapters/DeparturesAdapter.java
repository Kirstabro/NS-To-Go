package com.example.ns_to_go.Adapters;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ns_to_go.Data.Departure;
import com.example.ns_to_go.R;

import java.util.ArrayList;

public class DeparturesAdapter extends RecyclerView.Adapter<DeparturesAdapter.ImageViewHolder>
{
    private ArrayList<Departure> dataset;

    public DeparturesAdapter(ArrayList<Departure> departures)
    {
        this.dataset = departures;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout, parent, false);
        ImageViewHolder viewHolder = new ImageViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return dataset.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder
    {
        public ImageViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }
    }
}
