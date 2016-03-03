package com.paularagones.moode.Adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.paularagones.moode.Models.Status;
import com.paularagones.moode.R;

public class RowStatusListAdapter extends BaseAdapter {

    private List<Status> statusList = new ArrayList<Status>();

    private Context context;
    private LayoutInflater layoutInflater;

    public RowStatusListAdapter(Context context, List<Status> statusList) {
        this.statusList = statusList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
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

    private void initializeViews(Status status, ViewHolder holder) {
        holder.tvDate.setText(status.getDateStamp());
        holder.tvPerson.setText(status.getPerson());
        holder.tvFeelings.setText(status.getFeelings());
        holder.tvActivity.setText(status.getActivity());
        holder.tvLocation.setText(status.getLocation());
        holder.tvReason.setText(status.getNotes());

    }

    protected class ViewHolder {

    private TextView tvDate;
    private TextView tvFeelings;
    private TextView tvPerson;
    private TextView tvLocation;
    private TextView tvActivity;
    private TextView tvReason;

        public ViewHolder(View view) {
            tvDate = (TextView) view.findViewById(R.id.tv_date);
            tvFeelings = (TextView) view.findViewById(R.id.tv_feelings);
            tvActivity = (TextView) view.findViewById(R.id.tv_activity);
            tvPerson = (TextView) view.findViewById(R.id.tv_person);
            tvLocation = (TextView) view.findViewById(R.id.tv_location);
            tvReason = (TextView) view.findViewById(R.id.tv_reason);
        }
    }
}
