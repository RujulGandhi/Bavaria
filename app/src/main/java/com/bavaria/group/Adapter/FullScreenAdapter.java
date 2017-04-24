package com.bavaria.group.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bavaria.group.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.bavaria.group.FullScreenImageActivity.pager;

/**
 * Created by archirayan1 on 5/28/2016.
 */
public class FullScreenAdapter extends PagerAdapter {

    private Context context;
    ArrayList<String> picList;
    LayoutInflater inflater;

    public FullScreenAdapter(Context mContext, ArrayList<String> list) {
        this.context = mContext;
        this.picList = list;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public int getCount() {
        return picList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, final int position) {
        picList.get(position);
        View view = inflater.inflate(R.layout.item_fullscreen_image, container, false);
        ImageView ivImage = (ImageView) view.findViewById(R.id.ivPagerImage);
        ivImage.setDrawingCacheEnabled(true);
        Glide.with(context).load(picList.get(position)).placeholder(R.drawable.default_img).into(ivImage);
        container.addView(view);

        return view;
    }

    public ImageView getCurrentImageView(int pos) {
        View view = pager.getChildAt(pos).getRootView();
        return (ImageView) view.findViewById(R.id.ivPagerImage);
    }

}
