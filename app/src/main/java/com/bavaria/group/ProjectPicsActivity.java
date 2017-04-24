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

import com.bavaria.group.Adapter.ProjectImageAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.Util.Utils;

import java.util.HashMap;

/**
 * Created by archirayan1 on 3/10/2016.
 */
public class ProjectPicsActivity extends Activity {
    private ImageView ivBack;
    private GridView gvPicsList;
    private ProjectImageAdapter adapter;
    private String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_pics);
        ivBack = (ImageView) findViewById(R.id.ivBack_ProjectPicsActivity);
        gvPicsList = (GridView) findViewById(R.id.gvImage_ProjectPicsActivity);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = extras.getString("id");
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
            new getProjectImageData().execute();
        }else{
            Toast.makeText(ProjectPicsActivity.this, "No internet connectivity found, Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }

    }
    public class getProjectImageData extends AsyncTask<String, String, String> {
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(ProjectPicsActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader_layout);
            dialog.setCancelable(false);
            ImageView loader = (ImageView) dialog.findViewById(R.id.loader_layout_image);
            ((Animatable) loader.getDrawable()).start();
            dialog.show();

        }


        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("id=",id);
            return new MakeServiceCall().MakeServiceCall(Constant.BASE_URL + "propertyid_pics.php", data);
//            https://bavariagroup.net/en/api/propertyid_pics.php?id=9
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Log.d("Response", "" + s);
            dialog.dismiss();
        }
    }
}
