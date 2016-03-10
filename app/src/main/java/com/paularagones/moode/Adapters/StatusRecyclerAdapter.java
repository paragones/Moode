package com.paularagones.moode.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paularagones.moode.Models.Status;
import com.paularagones.moode.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Mae Anjanette Docena on 3/8/2016.
 */
public class StatusRecyclerAdapter extends RecyclerView.Adapter<StatusRecyclerAdapter.StatusViewHolder> {

    List<Status> statuses;

    public StatusRecyclerAdapter(List<Status> statuses) {
        this.statuses = statuses;
    }

    public static class StatusViewHolder extends RecyclerView.ViewHolder {

        TextView txtDate;
        CardView crdStatus;
        ImageView imgMood;
        TextView txtMood;
        ImageView imgLocation;
        TextView txtLocation;
        ImageView imgPerson;
        TextView txtPerson;
        ImageView imgActivity;
        TextView txtActivity;
        TextView txtNote;

        StatusViewHolder(View statusView) {
            super(statusView);
            txtDate = (TextView) statusView.findViewById(R.id.date);
            crdStatus = (CardView) statusView.findViewById(R.id.status_card);
            imgMood = (ImageView) statusView.findViewById(R.id.mood_image);
            txtMood = (TextView) statusView.findViewById(R.id.mood);
            imgLocation = (ImageView) statusView.findViewById(R.id.mood_location_img);
            txtLocation = (TextView) statusView.findViewById(R.id.mood_location);
            imgPerson = (ImageView) statusView.findViewById(R.id.mood_person_img);
            txtPerson = (TextView) statusView.findViewById(R.id.mood_person);
            imgActivity = (ImageView) statusView.findViewById(R.id.mood_activity_img);
            txtActivity = (TextView) statusView.findViewById(R.id.mood_activity);
            txtNote = (TextView) statusView.findViewById(R.id.mood_notes);
        }
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }

    @Override
    public StatusViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_status, viewGroup, false);
        StatusViewHolder statusView = new StatusViewHolder(v);
        return statusView;
    }

    @Override
    public void onBindViewHolder(StatusViewHolder statusViewHolder, int position) {
        String date = formatDate(statuses.get(position).getDateStamp());
        statusViewHolder.txtDate.setText(date);
        System.out.println(date);
        statusViewHolder.txtMood.setText((statuses.get(position).getFeelings()).toUpperCase());
        statusViewHolder.txtLocation.setText(statuses.get(position).getLocation());
        statusViewHolder.txtPerson.setText(statuses.get(position).getPerson());
        statusViewHolder.txtActivity.setText((statuses.get(position).getActivity()).toLowerCase());
        statusViewHolder.txtNote.setText(statuses.get(position).getNotes());
    }

    private String formatDate(String stringDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd");
        SimpleDateFormat sdfTemp = new SimpleDateFormat("MM/dd/yyyy");
        String date = null;
        try {
            Date tempDate = sdfTemp.parse(stringDate);
            date = sdf.format(tempDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date;
    }
}
