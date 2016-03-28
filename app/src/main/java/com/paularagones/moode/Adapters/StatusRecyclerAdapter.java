package com.paularagones.moode.Adapters;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.paularagones.moode.Activities.MainActivity;
import com.paularagones.moode.Animations.Animator;
import com.paularagones.moode.Database.DBAdapter;
import com.paularagones.moode.Fragments.StatusFragment;
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

    Context mContext;
    List<Status> mStatuses;
    Resources mRes;
    Animator anim;
    DBAdapter mDBAdapter;

    final static String TAG = "StatusRecyclerAdapter";

    public StatusRecyclerAdapter(Context context, List<Status> statuses, Resources res) {
        this.mContext = context;
        this.mStatuses = statuses;
        this.mRes = res;
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
        public RelativeLayout layoutMisc;
        public TextView txtEdit;
        public TextView txtDelete;

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
            layoutMisc = (RelativeLayout) statusView.findViewById(R.id.misc);
            txtEdit = (TextView) statusView.findViewById(R.id.edit);
            txtDelete = (TextView) statusView.findViewById(R.id.delete);
        }
    }

    @Override
    public int getItemCount() {
        return mStatuses.size();
    }

    @Override
    public StatusViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_status, viewGroup, false);
        final StatusViewHolder statusViewHolder = new StatusViewHolder(v);

        mDBAdapter = DBAdapter.newInstance(mContext);

        return statusViewHolder;
    }

    @Override
    public void onBindViewHolder(final StatusViewHolder statusViewHolder, final int position) {
        int id = mRes.getIdentifier("drawable/" + mStatuses.get(position).getFeelings(), null, MainActivity.PACKAGE_NAME);

        statusViewHolder.imgMood.setImageResource(id);
        statusViewHolder.txtMood.setText((mStatuses.get(position).getFeelings()).toUpperCase());
        statusViewHolder.txtLocation.setText(mStatuses.get(position).getLocation());
        statusViewHolder.txtPerson.setText(mStatuses.get(position).getPerson());
        statusViewHolder.txtActivity.setText((mStatuses.get(position).getActivity()).toLowerCase());
        statusViewHolder.txtNote.setText(mStatuses.get(position).getNotes());

        statusViewHolder.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Edit clicked");
            }
        });
        statusViewHolder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Delete clicked");
                new MaterialDialog.Builder(mContext)
                        .title("Delete status?") // TODO save to strings.xml
                        .content("Status will be deleted permanently.")
                        .positiveText("DELETE")
                        .negativeText("CANCEL")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                mDBAdapter.deleteStatus(mStatuses.get(position));
                                StatusFragment.mAdapter.notifyDataSetChanged();
                            }
                        })
                        .show();
            }
        });

        statusViewHolder.crdStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim = new Animator();
                anim.toggleStatusCardView(statusViewHolder, mRes, statusViewHolder.crdStatus.getTag().toString());
            }
        });


    }

    @Override
    public long getHeaderId(int position) {
        if (position == 0) {
            return 0;
        } else {
            String date =  formatDate(mStatuses.get(position).getDateStamp());
            String prevDate =  formatDate(mStatuses.get(position - 1).getDateStamp());

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
        String date = formatDate(mStatuses.get(position).getDateStamp());

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
