package com.bavaria.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bavaria.group.Adapter.PhotoVideoImageAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.Util.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by archirayan1 on 6/29/2016.
 */
public class CateloguePicsActivity extends Activity {
    private ImageView ivBack;
    private GridView gvPicsList;
    private PhotoVideoImageAdapter adapter;
    private String imgId = "";
    private String category_id = "";
    public static ArrayList<String> imgList;
    private String[] catelogueImagelist;
    private String status;
    String ArrayString;
    List<String> items;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_video_pics);
        ivBack = (ImageView) findViewById(R.id.ivBack_PhotoVideoPicsActivity);
        gvPicsList = (GridView) findViewById(R.id.gvImage_PhotoVideoPicsActivity);
        ArrayString = "";
        Bundle b = getIntent().getExtras();
        ArrayString = b.getString("image");
        try {
            jsonArray = new JSONArray(ArrayString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //        if (getIntent().getExtras() != null) {
//            ArrayString = getIntent().getExtras().getString("image");
//            ArrayString = ArrayString.substring(1, ArrayString.length());
//            ArrayString = ArrayString.replace("\"","");
//            catelogueImagelist = ArrayString.split(",");
//
//            Log.d("hiRUjul", catelogueImagelist.toString());

//        try {
//            for (int i = 0; i < jsonArray.length(); i++) {
//                Log.e("MEHUL" + i, " >>>> " + jsonArray.getString(i));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

//        }


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PhotoVideoListActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
                finish();
            }
        });

        if (Utils.isOnline(getApplicationContext())) {
            status = Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_PHOTO_OR_VIDEO);
            setCatalogueImageData();
        } else {
            Toast.makeText(CateloguePicsActivity.this, "No internet connectivity found, Please check your internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    private void setCatalogueImageData() {
        imgList = new ArrayList<String>();
//        Toast.makeText(CateloguePicsActivity.this, ""+catelogueImagelist.length , Toast.LENGTH_SHORT).show();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                imgList.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
//        Log.e("TOTAL IMAGES", "" + imgList.size());
        if (imgList.size() > 0) {
            adapter = new PhotoVideoImageAdapter(CateloguePicsActivity.this, imgList);
            gvPicsList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
