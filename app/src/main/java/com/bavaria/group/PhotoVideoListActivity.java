package com.bavaria.group;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bavaria.group.Adapter.CatelogueAdapter;
import com.bavaria.group.Adapter.PhotoVideoListAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.Util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by archirayan1 on 3/11/2016.
 */
public class PhotoVideoListActivity extends Activity {
    private ListView lvPhotoVideo;
    private ImageView ivBack;

    private PhotoVideoListAdapter adapter;
    private CatelogueAdapter adapterCatelogue;
    private ArrayList<HashMap<String,String>> dataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_photo_list);
        lvPhotoVideo = (ListView) findViewById(R.id.lvPAndV_photovideoListActivity);
        ivBack = (ImageView) findViewById(R.id.ivBack_PhotoVideoListActivity);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PhotoVideoActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
                finish();
            }
        });

//        adapter = new PhotoVideoListAdapter(getApplicationContext(),dataList);
        if(Utils.isOnline(getApplicationContext())){
            if(Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_PHOTO_OR_VIDEO).equalsIgnoreCase("0")) {
                new getPhotoAndVideoData().execute();
            }else{
                new getCatelogueData().execute();

            }
        }else{
            Toast.makeText(PhotoVideoListActivity.this, "No internet connection found, Please check your internet connectivity.", Toast.LENGTH_SHORT).show();
        }

//        lvPhotoVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(PhotoVideoListActivity.this, ProjectPicsActivity.class);
//                intent.putExtra("id",dataList.get(position).get("id"));
//                startActivity(intent);
//                overridePendingTransition(R.anim.zoom_in, R.anim.nothing);
//                            }
//        });

    }

    public class getPhotoAndVideoData extends AsyncTask<String, String, String> {
        Dialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(PhotoVideoListActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader_layout);
            dialog.setCancelable(false);
            ImageView loader = (ImageView) dialog.findViewById(R.id.loader_layout_image);
            ((Animatable) loader.getDrawable()).start();
            dialog.show();
            dataList = new ArrayList<HashMap<String, String>>();
            dataList.clear();

        }


        @Override
        protected String doInBackground(String... params) {
              StringBuilder response = new StringBuilder();
            URL url = null;
            try {
                url = new URL(Constant.BASE_URL+"n1_get_category.php");
                HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
                if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                    Log.d("StatusCode", "Done");
                    BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()), 8192);
                    String strLine = null;
                    while ((strLine = input.readLine()) != null) {
                        response.append(strLine);
                    }
                    input.close();
                } else {
//                    Log.d("StatusCode", "NotDone");
                }
//                Log.d("ResCode", String.valueOf(httpconn.getResponseCode()));

            } catch (Exception e) {
//                Log.d("Exception", e.toString());
            }
            return response.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
//            Log.e("Response", "" + s);
            try {
                JSONArray jArray = new JSONArray(s.toString());
//                Log.e("SIZE", "" + jArray.length());
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObj = jArray.getJSONObject(i);
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("id", "" + jObj.getString("id"));
                    hashMap.put("parent_id", "" + jObj.getString("parent_id"));
                    hashMap.put("category_name", "" + jObj.getString("category_name"));
                    hashMap.put("category_image",""+jObj.getString("category_image"));
                    hashMap.put("category_alias",""+jObj.getString("category_alias"));
                    hashMap.put("category_description", "" + jObj.getString("category_description"));
                    hashMap.put("pid", "" + jObj.getString("pid"));

                    dataList.add(hashMap);
                }

            } catch (JSONException e) {
                e.printStackTrace();
//                Log.e(">>>", "" + e.toString());
            }
//            Log.e("SIZE", "******* " + dataList.size());
            adapter = new PhotoVideoListAdapter(PhotoVideoListActivity.this, dataList);
            lvPhotoVideo.setAdapter(adapter);

        }
    }


    public class getCatelogueData extends AsyncTask<String, String, String> {
        Dialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(PhotoVideoListActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader_layout);
            dialog.setCancelable(false);
            ImageView loader = (ImageView) dialog.findViewById(R.id.loader_layout_image);
            ((Animatable) loader.getDrawable()).start();
            dialog.show();
            dataList = new ArrayList<HashMap<String, String>>();
            dataList.clear();

        }


        @Override
        protected String doInBackground(String... params) {
            StringBuilder response = new StringBuilder();
            URL url = null;
            try {
                url = new URL(Constant.BASE_URL+"catelogue_new.php");
                HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
                if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                    Log.d("StatusCode", "Done");
                    BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()), 8192);
                    String strLine = null;
                    while ((strLine = input.readLine()) != null) {
                        response.append(strLine);
                    }
                    input.close();
                } else {
//                    Log.d("StatusCode", "NotDone");
                }
//                Log.d("ResCode", String.valueOf(httpconn.getResponseCode()));

            } catch (Exception e) {
//                Log.d("Exception", e.toString());
            }
            return response.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
//            Log.e("Response", "" + s);
            try {
                JSONObject obj = new JSONObject(s.toString());
//                Log.e("SIZE", "" + jArray.length());
                JSONArray jArray = obj.getJSONArray("data");
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObj = jArray.getJSONObject(i);
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("pid", "" + jObj.getString("pid"));
                    hashMap.put("pname", "" + jObj.getString("pname"));
                    hashMap.put("image",""+jObj.getJSONArray("image"));
                    for(int j=0; j < 1; j++){
                        hashMap.put("imgOne", "" + jObj.getJSONArray("image").get(j));
                    }
                    dataList.add(hashMap);
                }

            } catch (JSONException e) {
                e.printStackTrace();
//                Log.e(">>>", "" + e.toString());
            }
//            Log.e("SIZE", "******* " + dataList.size());
            adapterCatelogue = new CatelogueAdapter(PhotoVideoListActivity.this, dataList);
            lvPhotoVideo.setAdapter(adapterCatelogue);

        }
    }


}
