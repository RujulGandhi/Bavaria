package com.bavaria.group.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bavaria.group.MultiTouchActivity;
import com.bavaria.group.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by archirayan1 on 6/2/2016.
 */
public class PhotoVideoImageAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<String> imgList;
    private static LayoutInflater inflater = null;
    PhotoVideoImageAdapter adapter = this;

    public PhotoVideoImageAdapter(Context mContext, ArrayList<String> imgList) {
        this.mContext = mContext;
        this.imgList = imgList;
        inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public Object getItem(int position) {
        return imgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.add_image_item, null);
            holder = new ViewHolder();
            holder.ivProperty = (ImageView) view.findViewById(R.id.ivProject_ProjectDetailImage_Activity);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Log.e("IMG"+position, "" + imgList.get(position));
        Glide.with(mContext)
                .load(imgList.get(position))
                .placeholder(R.drawable.default_img)
                .error(R.drawable.default_img)
                .into(holder.ivProperty);
        holder.ivProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MultiTouchActivity.class);
                intent.putExtra("img", imgList.get(position));
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    static class ViewHolder {
        ImageView ivProperty;
    }
}
