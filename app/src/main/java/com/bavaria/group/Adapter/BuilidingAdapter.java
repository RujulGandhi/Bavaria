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

import com.bavaria.group.BlockActivity;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.R;
import com.bavaria.group.Util.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by archirayan1 on 3/7/2016.
 */
public class BuilidingAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<HashMap<String, String>> projectList;
    private LayoutInflater inflater = null;
    String buildingName, projectName;


    public BuilidingAdapter(Context mCtx, ArrayList<HashMap<String, String>> list, String projectname) {
        this.mContext = mCtx;
        this.projectList = list;
        this.projectName = projectname;
        inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return projectList.size();
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
            view = inflater.inflate(R.layout.item_building_list, parent, false);
            // well set up the ViewHolder
            viewHolder = new ViewHolderItem();
            viewHolder.tvProjectName = (TextView) view.findViewById(R.id.tvProjectTitle_ProjectList);
            viewHolder.ivBuilding = (ImageView) view.findViewById(R.id.item_project_list_Img);
            // store the holder with the view.
            view.setTag(viewHolder);
        } else {
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolderItem) view.getTag();
        }





        final HashMap<String, String> hashMap = projectList.get(position);
//        Log.e("ADAPTER", "" + hashMap.get("category_name"));
        viewHolder.tvProjectName.setText("" + hashMap.get("building_name"));
//        Picasso.with(mContext).load(hashMap.get("image")).placeholder(R.drawable.default_img).into(viewHolder.ivBuilding);
        view.findViewById(R.id.item_project_list_ripple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, BlockActivity.class);
                Utils.WriteSharePrefrence(mContext,Constant.SHRED_PR.KEY_BLOCK_BUILDINGNAME,hashMap.get("building_name"));
                Utils.WriteSharePrefrence(mContext, Constant.SHRED_PR.KEY_BLOCK_PROJECTNAME, projectName);
                mContext.startActivity(intent);
                ((Activity)mContext).overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
            }
        });
        return view;
    }

    static class ViewHolderItem {
        TextView tvProjectName;
        ImageView ivBuilding;

    }
}
