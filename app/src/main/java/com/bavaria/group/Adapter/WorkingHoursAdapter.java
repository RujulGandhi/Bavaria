package com.bavaria.group.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bavaria.group.R;

import java.util.ArrayList;

/**
 * Created by archirayan on 2/26/2016.
 */
public class WorkingHoursAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<String> Day, Time;

    public WorkingHoursAdapter(Context context, ArrayList<String> Day, ArrayList<String> Time) {

        this.Day = Day;
        this.Time = Time;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return Day.size();
    }

    @Override
    public Object getItem(int position) {
        return Day.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.adapter_working_hours,null);

        TextView timeTv = (TextView)view.findViewById(R.id.adapter_working_hours_time);
        TextView DayTv = (TextView)view.findViewById(R.id.adapter_working_hours_days);


        timeTv.setText(Time.get(position));
        DayTv.setText(Day.get(position));

        return view;
    }
}
