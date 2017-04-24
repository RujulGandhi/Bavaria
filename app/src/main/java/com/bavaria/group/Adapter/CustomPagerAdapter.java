package com.bavaria.group.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bavaria.group.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomPagerAdapter extends PagerAdapter {

    private final ArrayList<String> picList;
    Context mContext;
    private LayoutInflater mLayoutInflater;

    public CustomPagerAdapter(Context context, ArrayList<String> list) {
        mContext = context;
        this.picList = list;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return picList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_fullscreen_image, container, false);
        itemView.setTag(position);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.ivPagerImage);
        imageView.setAdjustViewBounds(true);
        imageView.setDrawingCacheEnabled(true);
        imageView.setTag(position);
        Picasso.with(mContext).load(picList.get(position)).placeholder(R.drawable.default_img).into(imageView);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

//    public ImageView getCurrentImageView(int position) {
//        //  View view = pager.g(position).getRootView();
//        View view = pager.getChildAt(position).getRootView();
//        return (ImageView) view.findViewWithTag(position);
//    }

}