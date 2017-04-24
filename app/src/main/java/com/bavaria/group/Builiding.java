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

import com.bavaria.group.Adapter.BuilidingAdapter;
import com.bavaria.group.Constant.ConnectionDetector;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.Util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by archirayan on 4/18/2016.
 */
public class Builiding extends Activity {
    private ListView lvProjectList;
    private ImageView ivBack;
    String projectname;
    ArrayList<HashMap<String, String>> projectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);

        ivBack = (ImageView) findViewById(R.id.activity_building_list_back);
        lvProjectList = (ListView) findViewById(R.id.activity_building_list_list);
        getDataFromIntent();
        ConnectionDetector cd = new ConnectionDetector(Builiding.this);
        if (cd.isConnectingToInternet()) {

            new getBuildingList().execute();

        } else {
            Toast.makeText(Builiding.this, "Please connect Internet.", Toast.LENGTH_SHORT).show();
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Builiding.this, ProjectDetailsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getDataFromIntent() {
//        Intent intent = getIntent();
//        projectname = intent.getStringExtra(Constant.ProjectName);
        projectname = Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_BLOCK_PROJECTNAME);
    }

    private class getBuildingList extends AsyncTask<String, String, String> {
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new Dialog(Builiding.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader_layout);
            dialog.setCancelable(false);
            projectList = new ArrayList<HashMap<String, String>>();
            ImageView loader = (ImageView) dialog.findViewById(R.id.loader_layout_image);
            ((Animatable) loader.getDrawable()).start();
            dialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            String url = Constant.BASE_URL + "get_building_project.php?project_name=" + projectname;
            url = url.replaceAll(" ", "%20");
            Log.e("URL", "" + url);
            return new MakeServiceCall().MakeServiceCall(url);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
//            Log.d("Buildingresult", s.toString());

            HashMap<String, String> hashMap = null;
            try {
                JSONArray array = new JSONArray(s.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    hashMap = new HashMap<String, String>();
                    hashMap.put("building_name", "" + object.getString("building_name"));
//                    hashMap.put("image", "" + object.getString("image"));
                    projectList.add(hashMap);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            BuilidingAdapter adapter = new BuilidingAdapter(Builiding.this, projectList, projectname);
            lvProjectList.setAdapter(adapter);
        }

    }
}
