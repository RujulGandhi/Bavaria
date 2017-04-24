package com.bavaria.group.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bavaria.group.Constant.Constant;
import com.bavaria.group.PhotoVideoPicsActivity;
import com.bavaria.group.R;
import com.bavaria.group.Util.Utils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by archirayan1 on 3/11/2016.
 */
public class PhotoVideoListAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<HashMap<String, String>> projectList;
    private LayoutInflater inflater = null;

    public PhotoVideoListAdapter(Context mCtx, ArrayList<HashMap<String, String>> list) {
        this.mContext = mCtx;
        this.projectList = list;
        inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return projectList.size();
    }

    @Override
    public Object getItem(int position) {
        return projectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolderItem viewHolder = null;
        if (view == null) {
            // inflate the layout
            view = inflater.inflate(R.layout.item_photo_video_list, parent, false);

            // well set up the ViewHolder
            viewHolder = new ViewHolderItem();
            viewHolder.tvProjectName = (TextView) view.findViewById(R.id.tvTitle_photoVideoListActivity);
            viewHolder.ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto_photoVideoListActivity);
            // store the holder with the view.
            view.setTag(viewHolder);
        } else {
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolderItem) view.getTag();
        }

        final HashMap<String, String> hashMap = projectList.get(position);
        viewHolder.tvProjectName.setText(hashMap.get("category_name"));
        if (!hashMap.get("category_image").equalsIgnoreCase("")) {
            Glide.with(mContext).load(hashMap.get("category_image")).placeholder(R.drawable.default_img).into(viewHolder.ivPhoto);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PhotoVideoPicsActivity.class);
                String status = Utils.ReadSharePrefrence(mContext, Constant.SHRED_PR.KEY_IS_PHOTO_OR_VIDEO);
                intent.putExtra("pid", hashMap.get("pid"));
                Log.e("STATUS", "@@@ " + status);
                mContext.startActivity(intent);
                Activity activity = (Activity) mContext;
                activity.overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
            }
        });

//        Log.e("ADAPTER", "" + hashMap.get("category_name"));
//        viewHolder.tvProjectName.setText("" + hashMap.get("category_name"));
        return view;
    }

    static class ViewHolderItem {
        TextView tvProjectName;
        ImageView ivPhoto;
    }
}
