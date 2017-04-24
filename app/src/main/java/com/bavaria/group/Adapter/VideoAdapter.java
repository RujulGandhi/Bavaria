package com.bavaria.group.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bavaria.group.R;
import com.bavaria.group.VideoWeb;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by archirayan4 on 5/17/2016.
 */
public class VideoAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, String>> dataList;
    LayoutInflater inflater = null;
    public String vid, pName, pDesc;

    public VideoAdapter(Context context, ArrayList<HashMap<String, String>> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_photo_video_list, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.proNameTxt = (TextView) view.findViewById(R.id.tvTitle_photoVideoListActivity);
            viewHolder.photoImg = (ImageView) view.findViewById(R.id.ivPhoto_photoVideoListActivity);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) view.getTag();
        }
        HashMap<String, String> hm = dataList.get(position);
        viewHolder.proNameTxt.setText(hm.get("pro_name"));
        Glide.with(context).load(dataList.get(position).get("video_image")).into(viewHolder.photoImg);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vid = dataList.get(position).get("video");
                pName = dataList.get(position).get("pro_name");
                pDesc = dataList.get(position).get("pro_small_desc");

                Intent intent = new Intent(context, VideoWeb.class);
                intent.putExtra("video",""+vid);
                intent.putExtra("pro_name",""+pName);
                intent.putExtra("pro_small_desc",""+pDesc);
                context.startActivity(intent);
                Activity activity = (Activity) context;
                activity.overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
            }
        });

        return view;
    }

    private class ViewHolderItem {
        TextView proNameTxt;
        ImageView photoImg;
    }
}
