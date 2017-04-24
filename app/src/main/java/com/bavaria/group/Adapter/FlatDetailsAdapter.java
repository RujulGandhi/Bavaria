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

import com.bavaria.group.MultiTouchActivity;
import com.bavaria.group.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by archirayan1 on 5/23/2016.
 */
public class FlatDetailsAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<HashMap<String, String>> flatImgList;
    private LayoutInflater inflater = null;


    public FlatDetailsAdapter(Context mCtx, ArrayList<HashMap<String, String>> list) {
        this.mContext = mCtx;
        this.flatImgList = list;
        inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return flatImgList.size();
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
        ViewHolderItem viewHolder;
        if (view == null) {
            // inflate the layout
            view = inflater.inflate(R.layout.item_flat_details, parent, false);
            // well set up the ViewHolder
            viewHolder = new ViewHolderItem();
            viewHolder.ivFlat = (ImageView) view.findViewById(R.id.ivFlatPic_FlatDetailsActivity);
            // store the holder with the view.
            view.setTag(viewHolder);
        } else {
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolderItem) view.getTag();
        }


        final HashMap<String, String> hashMap = flatImgList.get(position);
        int number = position + 1;
        final String img = "img" + number;
        Log.e("IMG", "" + img);
        Log.e("FLAT ADAPTER", "" + hashMap.get(img));

        Glide.with(mContext).load(hashMap.get(img)).into(viewHolder.ivFlat);
        view.findViewById(R.id.item_flat_list_ripple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MultiTouchActivity.class);
                intent.putExtra("img", hashMap.get(img));
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
            }
        });
        return view;
    }

    static class ViewHolderItem {
        ImageView ivFlat;

    }
}
