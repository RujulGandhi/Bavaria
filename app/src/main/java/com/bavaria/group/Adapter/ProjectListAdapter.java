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

import com.bavaria.group.Constant.Constant;
import com.bavaria.group.ProjectDetailsActivity;
import com.bavaria.group.R;
import com.bavaria.group.Util.Utils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by archirayan1 on 3/7/2016.
 */
public class ProjectListAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<HashMap<String, String>> projectList;
    private LayoutInflater inflater = null;

    public ProjectListAdapter(Context mCtx, ArrayList<HashMap<String, String>> list) {
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
            view = inflater.inflate(R.layout.item_project_list, parent, false);
            // well set up the ViewHolder
            viewHolder = new ViewHolderItem();
            viewHolder.tvProjectName = (TextView) view.findViewById(R.id.tvProjectTitle_ProjectList);
            viewHolder.ivCategory = (ImageView) view.findViewById(R.id.item_project_list_Img);
            // store the holder with the view.
            view.setTag(viewHolder);
        } else {
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolderItem) view.getTag();
        }

        final HashMap<String, String> hashMap = projectList.get(position);
//        Log.e("ADAPTER", "" + hashMap.get("category_name"));
        viewHolder.tvProjectName.setText(hashMap.get("category_name"));
        //    Log.d("URL IMAGE", hashMap.get("image"));
        //   Picasso.with(mContext).load(hashMap.get("image")).placeholder(R.drawable.default_img).into(viewHolder.imageViewCalagory);

        if (hashMap.get("category_image").equalsIgnoreCase("")) {
            Glide.with(mContext).load(R.drawable.default_img).into(viewHolder.ivCategory);
//            Log.e("IMG", "NOIMG");
        } else {
//            Log.e("IMG", "IMG" + hashMap.get("category_image"));
            Glide.with(mContext).load(hashMap.get("category_image")).placeholder(R.drawable.default_img).into(viewHolder.ivCategory);
//            Glide.with(mContext).load("https://www.bavariagroup.net/images/osproperty/category/1431843449_AlSedraFinal3.jpg").into(viewHolder.ivCategory);
        }

        view.findViewById(R.id.item_project_list_ripple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ProjectDetailsActivity.class);
                Utils.WriteSharePrefrence(mContext, Constant.SHRED_PR.KEY_CATID, projectList.get(position).get("id"));
                Utils.WriteSharePrefrence(mContext, Constant.SHRED_PR.KEY_IMAGE_PATH, projectList.get(position).get("category_image"));
                Utils.WriteSharePrefrence(mContext, Constant.SHRED_PR.KEY_CAT_NAME, projectList.get(position).get("category_name"));
                Utils.WriteSharePrefrence(mContext,Constant.SHRED_PR.KEY_IMAGE_ID,projectList.get(position).get("pid"));
                Utils.WriteSharePrefrence(mContext,Constant.SHRED_PR.KEY_CAT_DESC,projectList.get(position).get("category_description"));
                Utils.WriteSharePrefrence(mContext,Constant.SHRED_PR.KEY_LAT,projectList.get(position).get("lat_add"));
                Utils.WriteSharePrefrence(mContext,Constant.SHRED_PR.KEY_LNG,projectList.get(position).get("long_add"));
                Utils.WriteSharePrefrence(mContext,Constant.SHRED_PR.KEY_CITY,projectList.get(position).get("city"));
                Utils.WriteSharePrefrence(mContext,Constant.SHRED_PR.KEY_STATE,projectList.get(position).get("state"));
                Utils.WriteSharePrefrence(mContext,Constant.SHRED_PR.KEY_COUNTRY,projectList.get(position).get("country"));
                
                mContext.startActivity(intent);
                Activity activity = (Activity) mContext;
                activity.overridePendingTransition(R.anim.zoom_in, R.anim.nothing);

            }
        });
        return view;
    }

    static class ViewHolderItem {
        TextView tvProjectName;
        ImageView ivCategory;
    }
}
