package com.bavaria.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.bavaria.group.Adapter.FlatDetailsAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.Util.Utils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by archirayan1 on 5/23/2016.
 */
public class FlatDetailsActivity extends Activity {
    private FlatDetailsAdapter adapter;
    ArrayList<HashMap<String, String>> flatImgList;
    HashMap<String, String> hashMap;
    private ListView lvflat;
    private ImageView ivBack;
    private String floorname;
    private ImageView ivFirstImg, ivSecondImg;
    private String img1 = "", img2 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_details);
        floorname = Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_FLOOR_NAME);

        init();
//        flatImgList = new ArrayList<HashMap<String, String>>();
//        flatImgList.clear();
        getDataFromIntent();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlatDetailsActivity.this, BlockActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
                finish();
            }
        });


        ivFirstImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MultiTouchActivity.class);
                intent.putExtra("img", img1);
                startActivity(intent);
            }
        });

        ivSecondImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MultiTouchActivity.class);
                intent.putExtra("img", img2);
                startActivity(intent);
            }
        });


//        if (Utils.isOnline(getApplicationContext())) {
//            adapter = new FlatDetailsAdapter(FlatDetailsActivity.this, flatImgList);
//            lvflat.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//        } else {
//            Toast.makeText(FlatDetailsActivity.this, "No internet connection found, Please check your internet connectivity.", Toast.LENGTH_SHORT).show();
//        }
    }

    private void init() {
//        lvflat = (ListView) findViewById(R.id.lvFlatImage_FlateDetailsAdapter);
        ivBack = (ImageView) findViewById(R.id.ivBack_FlatDetailsActivity);
        ivFirstImg = (ImageView) findViewById(R.id.ivFirstImg);
        ivSecondImg = (ImageView) findViewById(R.id.ivSecondImg);

    }

    private void getDataFromIntent() {
        img1 = Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IMG1);
        img2 = Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IMG2);

//        hashMap = new HashMap<String, String>();
//        Bundle extra = getIntent().getExtras();
//        img1 = extra.getString("img1");
//        hashMap.put("img1", img1);
        Glide.with(getApplicationContext()).load(img1).into(ivFirstImg);
        Log.e("img1", "" + img1);

        if (!img2.equalsIgnoreCase("")) {
            Log.e("1111111", "******* " + img2);
            ivSecondImg.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(img2).into(ivSecondImg);
//            hashMap.put("img2", img2);
        } else {
            ivSecondImg.setVisibility(View.GONE);
        }
//        flatImgList.add(hashMap);
    }


//    private class getFlatImageList extends AsyncTask<String, String, String> {
//        Dialog dialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog = new Dialog(FlatDetailsActivity.this);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setContentView(R.layout.loader_layout);
//            dialog.setCancelable(false);
//
//            ImageView loader = (ImageView) dialog.findViewById(R.id.loader_layout_image);
//            ((Animatable) loader.getDrawable()).start();
//            dialog.show();
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            Log.e("*****", "111111111111111" + floorname);
//            return new MakeServiceCall().MakeServiceCall("https://www.bavariagroup.net/api/block_pics.php?name=" + floorname);
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            dialog.dismiss();
////            Log.e("Response", "" + s);
//            try {
//                JSONObject jObj = new JSONObject(s.toString());
////                Log.e("SIZE", "" + jObj.length());
//                if (jObj.getString("successful").equalsIgnoreCase("true")) {
////                    Toast.makeText(getApplicationContext(), "" + jObj.getString("error_msg"), Toast.LENGTH_SHORT).show();
//                    JSONArray jArray = jObj.getJSONArray("image");
//                    for (int i = 0; i < jArray.length(); i++) {
//                        String img = "img" + i;
//                        hashMap = new HashMap<String, String>();
//                        hashMap.put(img, "" + jArray.get(i));
////                        Log.e(">>>> ",""+img);
//                        flatImgList.add(hashMap);
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), "" + jObj.getString("error_msg"), Toast.LENGTH_SHORT).show();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
////                Log.e(">>>", "" + e.toString());
//            }
//            adapter = new FlatDetailsAdapter(FlatDetailsActivity.this, flatImgList);
//            lvflat.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//        }
//    }
}
