package com.bavaria.group.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.Constant.Constant;
import com.bavaria.group.FlatDetailsActivity;
import com.bavaria.group.R;
import com.bavaria.group.Util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by archirayan1 on 5/17/2016.
 */
public class BlockAdapter extends BaseAdapter {
    Context mContext;
    Map<String, List<String>> floorList;
    ArrayList<Integer> keyList;
    private LayoutInflater inflater = null;
    String buildingName;
    Map<String, HashMap<String, String>> imgList;
    ArrayList<String> keyImgList;
    String img1 = "";
    String img2 = "";

    public BlockAdapter(Context mCtx, Map<String, List<String>> list, ArrayList<Integer> keyList, Map<String, HashMap<String, String>> imageList, ArrayList<String> keyImgLst) {
        this.mContext = mCtx;
        this.floorList = list;
        this.keyList = keyList;
        this.imgList = imageList;
        this.keyImgList = keyImgLst;
        inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return floorList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        // inflate the layout
        view = inflater.inflate(R.layout.item_floor_details, parent, false);
        // well set up the ViewHolder
        TextView tvFloorName = (TextView) view.findViewById(R.id.tvFloorname);
        ImageView ivBuilding = (ImageView) view.findViewById(R.id.item_project_list_Img);
        View vwDivider = (View) view.findViewById(R.id.vwDivider_FloorAdapter);
        LinearLayout llfloorBox = (LinearLayout) view.findViewById(R.id.llFloorBox_FloorAdapter);

        tvFloorName.setText("Floor" + keyList.get(position));

//        Log.e("FLOOR SIZE", "" + floorList.size());
        for (Map.Entry<String, List<String>> entry : floorList.entrySet()) {

            if (keyList.get(position).toString().equalsIgnoreCase(entry.getKey())) {
                List<String> values = entry.getValue();
//                Log.e("KEY", "" + keyList.get(position) + " ### " + values);
                final List<String> al = new ArrayList<>();
// add elements to al, including duplicates
                Set<String> hs = new HashSet<>();
                hs.addAll(values);
                al.clear();
                al.addAll(hs);

                Collections.sort(al, new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return s1.compareToIgnoreCase(s2);
                    }
                });

//                String weight = al.size();
                llfloorBox.setWeightSum(al.size());
                for (int i = 0; i < al.size(); i++) {
//                    Log.e("@@ " + i, "" + al.get(i));

                    final TextView tvFloorBox = new TextView(mContext);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
//                    tvFloorBox.setLayoutParams(new LinearLayout.LayoutParams(0, 30, 1.0f));
//                    tvFloorBox.setPadding(10, 15, 10, 15);
                    params.setMargins(10, 0, 10, 0);

                    tvFloorBox.setLayoutParams(params);
//                    tvFloorBox.setLayoutParams(new LinearLayout.LayoutParams(0,
//                            LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                    tvFloorBox.setGravity(Gravity.CENTER);
                    tvFloorBox.setTextColor(mContext.getResources().getColor(R.color.white));
//                    tvFloorBox.setText(al.get(i));
                    final String floorName = al.get(i);

//                    for (int j = 0; j < keyImgList.size(); j++) {
//                        Log.e("", "" + keyImgList.get(j));
                    String combine = keyList.get(position).toString() + floorName;
//                        Log.e("MMMM", "" + combine);


                    for (Map.Entry<String, HashMap<String, String>> entry1 : imgList.entrySet()) {
                        if (entry1.getKey().equalsIgnoreCase(combine)) {
                            HashMap<String, String> data = entry1.getValue();
//                                Log.e("******", "*****  START  ******");
//                                Log.e("K >> ", "" + entry1.getKey());
//                                Log.e("V >AV> ", "" + data.get("Available"));
                            tvFloorBox.setText(data.get("flat_name"));
                            if (data.get("Available").equalsIgnoreCase("yes")) {
                                tvFloorBox.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.floor_box_bg_green));
                            } else if(data.get("Available").equalsIgnoreCase("no")) {
                                tvFloorBox.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.floor_box_bg_red));
                            }else{
                                tvFloorBox.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.floor_box_bg_green));
                            }
                            Log.e("**********", "*************************");
                            if (!data.get("img1").equalsIgnoreCase("")) {
                                Log.e("img1 ", "" + data.get("img1"));
                                img1 = data.get("img1");
                            }
                            if (!data.get("img2").equalsIgnoreCase("")) {
                                Log.e("img2 ", "" + data.get("img2"));
                                img2 = data.get("img2");
                            }
//                                Log.e("******", "*****  END  *****");
                        } else {

                        }
                    }

//                    }

//                    if(imgList.get(i).get(key).equalsIgnoreCase(key)){
//                        Log.e("IMg"+keyList.get(position).toString()+floorName,""+);
//
//                        tvFloorBox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.floor_box_bg_green));
//                    }

                    llfloorBox.addView(tvFloorBox);

                    tvFloorBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!img1.equalsIgnoreCase("")) {
                                Toast.makeText(mContext, "" + floorName, Toast.LENGTH_SHORT).show();
                                Utils.WriteSharePrefrence(mContext, Constant.SHRED_PR.KEY_FLOOR_NAME, floorName);
                                Utils.WriteSharePrefrence(mContext,Constant.SHRED_PR.KEY_IMG1,img1);
                                Utils.WriteSharePrefrence(mContext,Constant.SHRED_PR.KEY_IMG2,img2);
                                Intent intent = new Intent(mContext, FlatDetailsActivity.class);
                                mContext.startActivity(intent);
                            } else {
                                Toast.makeText(mContext, "No Image Found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }
        if (Utils.ReadSharePrefrence(mContext, Constant.SHRED_PR.KEY_IS_YES_OR_NO).equalsIgnoreCase("1")) {
            int pos = position + 1;
            if (pos % 2 == 0) {
                vwDivider.setVisibility(View.VISIBLE);
            } else {
                vwDivider.setVisibility(View.GONE);
            }
        }


        return view;
    }
}