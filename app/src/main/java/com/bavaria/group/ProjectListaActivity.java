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
import android.widget.ListView;
import android.widget.Toast;

import com.bavaria.group.Adapter.ProjectListAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.Util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by archirayan1 on 3/7/2016.
 */
public class ProjectListaActivity extends Activity {
    private ListView lvProjectList;
    private ImageView ivBack;
    private ProjectListAdapter adapter;
    ArrayList<HashMap<String, String>> projectList;
    HashMap<String, String> hashMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectlist);
        lvProjectList = (ListView) findViewById(R.id.lvProjectList_ProjectListActivity);
        ivBack = (ImageView) findViewById(R.id.ivBack_ProjectListActivity);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectListaActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
                finish();
            }
        });

        if (Utils.isOnline(getApplicationContext())) {
            new getProjectCategory().execute();
        } else {
            Toast.makeText(ProjectListaActivity.this, "No internet connectivity found, Please check your internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    private class getProjectCategory extends AsyncTask<String, String, String> {
        Dialog dialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog = new Dialog(ProjectListaActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader_layout);
            dialog.setCancelable(false);
            projectList = new ArrayList<HashMap<String, String>>();
            projectList.clear();
            ImageView loader = (ImageView) dialog.findViewById(R.id.loader_layout_image);
            ((Animatable) loader.getDrawable()).start();
            dialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            return new MakeServiceCall().MakeServiceCall(Constant.BASE_URL+"n1_get_category.php");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            Log.e("Response", "" + s);
            try {
                JSONArray jArray = new JSONArray(s.toString());
//                Log.e("SIZE", "" + jArray.length());
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObj = jArray.getJSONObject(i);
                    hashMap = new HashMap<String, String>();
                    hashMap.put("id", "" + jObj.getString("id"));
                    hashMap.put("parent_id", "" + jObj.getString("parent_id"));
                    hashMap.put("category_name", "" + jObj.getString("category_name"));
                    hashMap.put("category_image",""+jObj.getString("category_image"));
                    hashMap.put("category_alias",""+jObj.getString("category_alias"));
                    hashMap.put("category_description", "" + jObj.getString("category_description"));
                    hashMap.put("lat_add",""+jObj.getString("lat_add"));
                    hashMap.put("long_add",""+jObj.getString("long_add"));

                    hashMap.put("city",""+jObj.getString("city"));
                    hashMap.put("state",""+jObj.getString("state"));
                    hashMap.put("country",""+jObj.getString("country"));

                    hashMap.put("access", "" + jObj.getString("access"));
                    hashMap.put("ordering", "" + jObj.getString("ordering"));
                    hashMap.put("price_type", "" + jObj.getString("price_type"));
                    hashMap.put("published", "" + jObj.getString("published"));
                    hashMap.put("pid", "" + jObj.getString("pid"));

                    projectList.add(hashMap);
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                Log.e(">>>", "" + e.toString());
            }
            adapter = new ProjectListAdapter(ProjectListaActivity.this, projectList);
            lvProjectList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
