package com.paularagones.moode.Adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.paularagones.moode.Models.Result;
import com.paularagones.moode.R;

public class RowResultsWithBarAdapter extends BaseAdapter {

    private List<Result> results = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;

    public RowResultsWithBarAdapter(Context context, List<Result> results) {
        this.results = results;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Result getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_results_with_bar, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(Result result, ViewHolder holder) {
        holder.tvDescription.setText(result.getDescription());
        holder.tvCount.setText(String.valueOf(result.getNumberOfTimes()));
    }

    protected class ViewHolder {
    private TextView tvDescription;
    private TextView tvCount;

        public ViewHolder(View view) {
            tvDescription = (TextView) view.findViewById(R.id.tv_description);
            tvCount = (TextView) view.findViewById(R.id.tv_count);
        }
    }
}
