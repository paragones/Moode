package com.paularagones.moode.Adapters;

import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paularagones.moode.Activities.MainActivity;
import com.paularagones.moode.Listeners.ItemClickListener;
import com.paularagones.moode.Models.Status;
import com.paularagones.moode.R;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Mae Anjanette Docena on 3/8/2016.
 */
public class StatusRecyclerAdapter extends RecyclerView.Adapter<StatusRecyclerAdapter.StatusViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = StatusRecyclerAdapter.class.getSimpleName();
    List<Status> statuses;
    Resources res;
    ItemClickListener listener;

    final static String TAG = "StatusRecyclerAdapter";

    public StatusRecyclerAdapter(List<Status> statuses, Resources res, ItemClickListener listener) {
        this.statuses = statuses;
        this.res = res;
        this.listener = listener;
    }

    public static class StatusViewHolder extends RecyclerView.ViewHolder {

        public CardView crdStatus;
        public ImageView imgMood;
        public TextView txtMood;
        public ImageView imgLocation;
        public TextView txtLocation;
        public ImageView imgPerson;
        public TextView txtPerson;
        public ImageView imgActivity;
        public TextView txtActivity;
        public TextView txtNote;

        public StatusViewHolder(View statusView) {
            super(statusView);
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
        final StatusViewHolder statusViewHolder = new StatusViewHolder(v);
        v.findViewById(R.id.status_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, statusViewHolder.getLayoutPosition());
            }
        });
        v.findViewById(R.id.status_card).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClick(v, statusViewHolder.getLayoutPosition());
                return true;
            }
        });
        return statusViewHolder;
    }

    @Override
    public void onBindViewHolder(StatusViewHolder statusViewHolder, int position) {
        int id = res.getIdentifier("drawable/" + statuses.get(position).getFeelings(), null, MainActivity.PACKAGE_NAME);

        statusViewHolder.imgMood.setImageResource(id);
        statusViewHolder.txtMood.setText((statuses.get(position).getFeelings()).toUpperCase());
        statusViewHolder.txtLocation.setText(statuses.get(position).getLocation());
        statusViewHolder.txtPerson.setText(statuses.get(position).getPerson());
        statusViewHolder.txtActivity.setText((statuses.get(position).getActivity()).toLowerCase());
        statusViewHolder.txtNote.setText(statuses.get(position).getNotes());
    }

    @Override
    public long getHeaderId(int position) {
        if (position == 0) {
            return 0;
        } else {
            String date =  formatDate(statuses.get(position).getDateStamp());
            String prevDate =  formatDate(statuses.get(position - 1).getDateStamp());

            if (date.equals(prevDate)) {
                return -1;
            } else {
                return position;
            }
        }
    }

    @Override
    public ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.header, viewGroup, false);
        return new ViewHolder(v) {};
    }

    @Override
    public void onBindHeaderViewHolder(ViewHolder holder, int position) {
        String date = formatDate(statuses.get(position).getDateStamp());

        TextView txtDate = (TextView)holder.itemView;
        txtDate.setText(date.toUpperCase());
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
