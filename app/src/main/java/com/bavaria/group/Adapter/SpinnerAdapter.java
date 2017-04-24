package com.bavaria.group.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bavaria.group.R;

import java.util.ArrayList;

/**
 * Created by archirayan1 on 5/31/2016.
 */
public class SpinnerAdapter extends ArrayAdapter<String> {
    Context mContext;
    ArrayList<String> itemList;
    private LayoutInflater inflater = null;

    public SpinnerAdapter(Context context, int textViewResourceId,
                          ArrayList<String> list) {
        super(context, textViewResourceId, list);
        this.itemList = list;
        this.mContext = context;
        inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        // TODO Auto-generated method stub
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //return super.getView(position, convertView, parent);
        View row = inflater.inflate(R.layout.spinner_row, parent, false);
        TextView label = (TextView) row.findViewById(R.id.tvItemName);
        label.setText(itemList.get(position));

        return row;
    }
}