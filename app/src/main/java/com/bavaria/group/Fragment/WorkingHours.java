package com.bavaria.group.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bavaria.group.Adapter.WorkingHoursAdapter;
import com.bavaria.group.R;

import java.util.ArrayList;

/**
 * Created by archirayan on 2/27/2016.
 */
public class WorkingHours extends Fragment {
    ArrayList<String> Day, Time;
    WorkingHoursAdapter adapter;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.working_hours_list, container, false);

        Day = new ArrayList<String>();
        Time = new ArrayList<String>();

        listView = (ListView) view.findViewById(R.id.activity_contactus_working_hours_list);

        Day.add("Sunday");
        Day.add("Moday");
        Day.add("Tuesday");
        Day.add("Wensday");

        Time.add("8.00 AM - 5.00 PM");
        Time.add("8.00 AM - 5.00 PM");
        Time.add("8.00 AM - 5.00 PM");
        Time.add("8.00 AM - 5.00 PM");

        adapter = new WorkingHoursAdapter(getActivity(), Day, Time);
        listView.setAdapter(adapter);

        return view;
    }
}
