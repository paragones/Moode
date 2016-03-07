package com.paularagones.moode.Adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.paularagones.moode.Activities.AddNewStatusActivity;
import com.paularagones.moode.Activities.StatusListActivity;
import com.paularagones.moode.Database.DBAdapter;
import com.paularagones.moode.Models.Status;
import com.paularagones.moode.R;

import org.greenrobot.eventbus.EventBus;

public class RowStatusListAdapter extends BaseAdapter {

    private static final String LOG_TAG = RowStatusListAdapter.class.getSimpleName();
    private List<Status> statusList = new ArrayList<Status>();
    private Context context;
    private LayoutInflater layoutInflater;

    private EventBus eventBus;

    public RowStatusListAdapter(Context context, List<Status> statusList) {
        this.statusList = statusList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        eventBus = EventBus.getDefault();
    }



    @Override
    public int getCount() {
        return statusList.size();
    }

    @Override
    public Status getItem(int position) {
        return statusList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_status_list, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(final Status status, ViewHolder holder) {
        holder.tvDate.setText(status.getDateStamp());
        holder.tvPerson.setText(status.getPerson());
        holder.tvFeelings.setText(status.getFeelings());
        holder.tvActivity.setText(status.getActivity());
        holder.tvLocation.setText(status.getLocation());
        holder.tvReason.setText(status.getNotes());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventBus.postSticky(status);

                Intent mainIntent = new Intent(context, AddNewStatusActivity.class);
                context.startActivity(mainIntent);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBAdapter dbAdapter = DBAdapter.newInstance(context);
                dbAdapter.deleteStatus(status);
            }
        });

    }

    protected class ViewHolder {

    private TextView tvDate;
    private TextView tvFeelings;
    private TextView tvPerson;
    private TextView tvLocation;
    private TextView tvActivity;
    private TextView tvReason;
    private Button btnEdit;
    private Button btnDelete;


        public ViewHolder(View view) {
            tvDate = (TextView) view.findViewById(R.id.tv_date);
            tvFeelings = (TextView) view.findViewById(R.id.tv_feelings);
            tvActivity = (TextView) view.findViewById(R.id.tv_activity);
            tvPerson = (TextView) view.findViewById(R.id.tv_person);
            tvLocation = (TextView) view.findViewById(R.id.tv_location);
            tvReason = (TextView) view.findViewById(R.id.tv_reason);
            btnEdit = (Button) view.findViewById(R.id.button_edit);
            btnDelete = (Button) view.findViewById(R.id.button_delete);
        }
    }
}
