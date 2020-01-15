package com.example.ns_to_go.Adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ns_to_go.Data.Departure;
import com.example.ns_to_go.R;

import org.w3c.dom.Text;

import java.time.format.DateTimeFormatter;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position)
    {
        Departure departure = dataset.get(position);
        holder.track.setText(departure.getPlannedTrack());
        holder.destination.setText(departure.getDirection());
        holder.typeOfTrain.setText(departure.getTrainType());

        String stations = "";
        if (departure.getRouteStations().size() == 1){
            stations += departure.getRouteStations().get(0);
        } else if (departure.getRouteStations().size() > 1){
            stations += departure.getRouteStations().get(0);
            for (String s : departure.getRouteStations()){
                stations += ", " + s;
            }
        }
        holder.stations.setText(stations);

        if (departure.getPlannedTime().getMinute() == departure.getActualTime().getMinute() && departure.getPlannedTime().getHour() == departure.getActualTime().getHour()){
            holder.time.setText(departure.getPlannedTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            holder.changedTimeView.setText("");
        } else {
            holder.time.setText(departure.getPlannedTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            holder.time.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            holder.changedTimeView.setText(" -> " + departure.getActualTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        }

    }

    @Override
    public int getItemCount()
    {
        return dataset.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder
    {

        TextView destination;
        TextView time;
        TextView stations;
        TextView typeOfTrain;
        TextView track;
        TextView changedTimeView;
        CardView background;

        public ImageViewHolder(@NonNull View itemView)
        {
            super(itemView);

            destination = itemView.findViewById(R.id.destinationView);
            typeOfTrain = itemView.findViewById(R.id.typeOfTrainView);
            time = itemView.findViewById(R.id.timeView);
            track = itemView.findViewById(R.id.trackView);
            stations = itemView.findViewById(R.id.stationsView);
            changedTimeView = itemView.findViewById(R.id.changedTimeView);
            background = itemView.findViewById(R.id.backgroundCardView);
        }
    }
}
