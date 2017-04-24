package com.bavaria.group;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.Adapter.BlockAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.Util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by archirayan1 on 5/17/2016.
 */
public class BlockActivity extends Activity {
    Map<String, HashMap<String, String>> ImageList;
    Map<String, List<String>> floorList;
    ArrayList<Integer> keyList;
    ArrayList<String> keyImgList;
    HashMap<String, String> hashmap;

    List<String> values;
    public static String key;
    String img_key;

    TextView tvFloorTitle;
    LinearLayout llMain;
    String buildingName, projectname;
    ListView lvMain;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block);
        getDataFromIntent();

        lvMain = (ListView) findViewById(R.id.lvMain_FloorActivity);
        tvFloorTitle = (TextView) findViewById(R.id.tvFloorTitle_FloorActivity);
        ivBack = (ImageView) findViewById(R.id.ivBack_BlockActivity);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BlockActivity.this, Builiding.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
                finish();
            }
        });
        tvFloorTitle.setText(buildingName);

        if (Utils.isOnline(getApplicationContext())) {
            new getFloorData().execute();
        } else {
            Toast.makeText(BlockActivity.this, "No internet connectivity found, please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDataFromIntent() {
        buildingName = Utils.ReadSharePrefrence(getApplicationContext(),Constant.SHRED_PR.KEY_BLOCK_BUILDINGNAME);
        projectname = Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_BLOCK_PROJECTNAME);
//        Intent intent = getIntent();
//        buildingName = intent.getStringExtra(Constant.BuildingName);
//        projectname = intent.getStringExtra(Constant.ProjectName);
    }


    private class getFloorData extends AsyncTask<String, String, String> {
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new Dialog(BlockActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader_layout);
            dialog.setCancelable(false);
//            floorList = new ArrayList<HashMap<String, String>>();
            floorList = new HashMap<String, List<String>>();
            floorList.clear();

            ImageList = new HashMap<String, HashMap<String, String>>();
            ImageList.clear();

            keyList = new ArrayList<Integer>();
            keyList.clear();

            keyImgList = new ArrayList<String>();
            keyImgList.clear();

            ImageView loader = (ImageView) dialog.findViewById(R.id.loader_layout_image);
            ((Animatable) loader.getDrawable()).start();
            dialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
//            Log.e("projectname", "" + projectname);
//            Log.e("buildingName", "" + buildingName);
            String url = Constant.BASE_URL + "all_data123.php?project_name=" + projectname + "&building_name=" + buildingName;
            url = url.replaceAll(" ", "%20");
            Log.e("url", "" + url);
            return new MakeServiceCall().MakeServiceCall(url);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            Log.e("RESPONSE", s.toString());
//            Toast.makeText(BlockActivity.this, "RESULT : " + s, Toast.LENGTH_SHORT).show();


            try {

                JSONObject obj = new JSONObject(s.toString());
//                Log.e("SIZE", "" + obj.length());
                JSONArray array = null;
                if (obj.has("no")) {
                    array = obj.getJSONArray("no");
                    Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_YES_OR_NO, "0");
                }

                if (obj.has("yes")) {
                    array = obj.getJSONArray("yes");
                    Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_IS_YES_OR_NO, "1");
                }

                values = new ArrayList<String>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    hashmap = new HashMap<String, String>();
                    key = object.getString("floor_name");

                    img_key = object.getString("floor_name") + object.getString("flat_name");
//                    Log.e("KEYS", "" +img_key);
                    keyImgList.add(img_key);
                    hashmap.put("Available", object.getString("Available"));
//                    hashmap.put("flat_image",object.getString("flat_image"));
                    hashmap.put("flat_name", object.getString("flat_name"));
                    JSONArray arry = object.getJSONArray("flat_image");
                    Log.e("SIZE", "" + arry);
                    for (int m = 0; m < arry.length(); m++) {
                        int num = m + 1;
                        hashmap.put("img" + num, "" + arry.get(m));
                        Log.e("img" + num, "" + arry.get(m));
                    }
                    values.add(object.getString("flat_name"));
                    floorList.put(key, values);
                    ImageList.put(img_key, hashmap);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(BlockActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
            }
//            Log.e("FLOOR SIZE", "" + floorList.size());
            for (Map.Entry<String, List<String>> entry : floorList.entrySet()) {

                String key = entry.getKey();

                List<String> values = entry.getValue();
//                Log.e("KEY",""+key+" --- "+values);
                System.out.println("Key = " + key);
                keyList.add(Integer.parseInt(key));
                System.out.println("Values = " + values + "n");
//                Log.e("BREAK "+key,"--------------------------------");
            }


            Collections.sort(keyList, new Comparator<Integer>() {
                @Override
                public int compare(Integer s1, Integer s2) {
                    return s2.compareTo(s1);
                }
            });

            BlockAdapter adapter = new BlockAdapter(BlockActivity.this, floorList, keyList, ImageList, keyImgList);
            lvMain.setAdapter(adapter);
        }

    }
}
