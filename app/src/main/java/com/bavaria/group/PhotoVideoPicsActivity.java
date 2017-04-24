package com.bavaria.group;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bavaria.group.Adapter.PhotoVideoImageAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.Util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by archirayan1 on 6/2/2016.
 */
public class PhotoVideoPicsActivity extends Activity {
    private ImageView ivBack;
    private GridView gvPicsList;
    private PhotoVideoImageAdapter adapter;
    private String imgId = "";
    private String category_id = "";
    public static ArrayList<String> imgList;
    private String[] catelogueImagelist;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_video_pics);
        ivBack = (ImageView) findViewById(R.id.ivBack_PhotoVideoPicsActivity);
        gvPicsList = (GridView) findViewById(R.id.gvImage_PhotoVideoPicsActivity);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            imgId = extras.getString("pid");
        }

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
            new getPhotoVideoImageData().execute();
        } else {
            Toast.makeText(PhotoVideoPicsActivity.this, "No internet connectivity found, Please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private class getPhotoVideoImageData extends AsyncTask<String, String, String> {
        private Dialog dialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog = new Dialog(PhotoVideoPicsActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader_layout);
            dialog.setCancelable(false);
            imgList = new ArrayList<String>();
            imgList.clear();
            ImageView loader = (ImageView) dialog.findViewById(R.id.loader_layout_image);
            ((Animatable) loader.getDrawable()).start();
            dialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
//            HashMap<String, String> data = new HashMap<String, String>();
//            data.put("id", imgId);
//            Log.e("@@@@@@@@@@@@", "" + Constant.BASE_URL + "propertyid_pics.php?id=" + imgId);
            return new MakeServiceCall().MakeServiceCall(Constant.BASE_URL + "propertyid_pics.php?id=" + imgId);
        }

        @Override
        protected void onPostExecute(String s) {
//            Log.d("Response", "" + s);
            dialog.dismiss();
            try {
                JSONObject object = new JSONObject(s.toString());
                if (object.getString("successful").equalsIgnoreCase("true")) {
//                    Toast.makeText(PhotoVideoPicsActivity.this, "" + object.getString("error_msg"), Toast.LENGTH_SHORT).show();
                    JSONArray array = object.getJSONArray("image");
                    for (int i = 0; i < array.length(); i++) {
                        imgList.add(array.getString(i));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new PhotoVideoImageAdapter(PhotoVideoPicsActivity.this, imgList);
            gvPicsList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

}
