package com.paularagones.moode.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.paularagones.moode.Models.SpinnerResult;
import com.paularagones.moode.R;

import java.util.ArrayList;
import java.util.List;

public class RowSpinnerStyleAdapter extends BaseAdapter {

    private static final String LOG_TAG = RowSpinnerStyleAdapter.class.getSimpleName();
    private List<SpinnerResult> spinnerResults = new ArrayList<>();

    private LayoutInflater layoutInflater;

    public RowSpinnerStyleAdapter(Context context, List<SpinnerResult> spinnerResults) {
        this.spinnerResults = spinnerResults;
        Context context1 = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return spinnerResults.size();
    }

    @Override
    public SpinnerResult getItem(int position) {
        return spinnerResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_spinner_style, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(SpinnerResult spinnerResult, ViewHolder holder) {
//        Log.e(LOG_TAG, spinnerResult.toString() );
        holder.tvSpinnerDescription.setText(spinnerResult.getDescription());
        holder.tvId.setText(String.valueOf(spinnerResult.getID()));
    }

    protected class ViewHolder {
        private TextView tvSpinnerDescription;
    private TextView tvId;

        public ViewHolder(View view) {
            tvSpinnerDescription = (TextView) view.findViewById(R.id.tv_spinner_description);
            tvId = (TextView) view.findViewById(R.id.tv_id);
        }
    }

    // It gets a View that displays in the drop down popup the data at the specified position
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // It gets a View that displays the data at the specified position
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
}

