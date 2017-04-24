package com.bavaria.group;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bavaria.group.Adapter.ProjectImageAdapter;
import com.bavaria.group.Constant.Constant;
import com.bavaria.group.Util.Utils;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by archirayan1 on 3/8/2016.
 */
public class ProjectDetailsActivity extends FragmentActivity implements View.OnClickListener, OnMapReadyCallback {
    private ImageView ivBack, ivImagePro;
    private Button btCategory;
    private TextView tvCategoryDesc,tvState, tvProName;
    public static String categoryName, categoryDesc, imagePath, catId, imgId,lat,lng,city,state,country;
    private RatingBar rating;
    private ProjectImageAdapter adapter;
    private GridView gvImage;
    private LinearLayout llProjectDetails, llProjectPics;
    public static ArrayList<String> imgList ;
    private GoogleMap mMap;
    private Double latitude, longitude;
    public String latlong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        inIt();
        getDataFromIntent();
        addListenerOnRatingBar();
//        new getProjectDetailsData().execute();
        if (Utils.isOnline(getApplicationContext())) {
            if(!imgId.equalsIgnoreCase("")){
                new getProjectPics().execute();
            }else{
                Toast.makeText(ProjectDetailsActivity.this, "No images found.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ProjectDetailsActivity.this, "No internet connectivity found, Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void addListenerOnRatingBar() {
        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
//                btCategoryDesc.setText(String.valueOf(rating));
            }
        });
    }

    private void inIt() {
//        tvCity = (TextView) findViewById(R.id.tvCity_PropertyDetailsActivity);
//        tvProName = (TextView) findViewById(R.id.tvProjectname_PropertyDetailsActivity);
        tvState = (TextView) findViewById(R.id.tvState_PropertyDetailsActivity);
        Button btnCheckAvability = (Button) findViewById(R.id.activity_project_details_avability);
        Button btnShareLocation = (Button) findViewById(R.id.activity_project_details_share_location);
        ivBack = (ImageView) findViewById(R.id.ivBack_ProjectDetailsActivity);
        btCategory = (Button) findViewById(R.id.btCategory_ProjectDetailsActivity);
        tvCategoryDesc = (TextView) findViewById(R.id.tvDescription_PropertyDetailsActivity);
        Button btPics = (Button) findViewById(R.id.btPics_ProjectDetailsActivity);
        rating = (RatingBar) findViewById(R.id.ratingBar_ProjectDetailsActivity);
        llProjectDetails = (LinearLayout) findViewById(R.id.llProjectDetails_ProjectDetailsActivity);
        llProjectPics = (LinearLayout) findViewById(R.id.llProjectPics_ProjectDetailsActivity);
        ivImagePro = (ImageView) findViewById(R.id.ivImage_ProjectDetailActivity);
        gvImage = (GridView) findViewById(R.id.gvImageList);
        ivBack.setOnClickListener(this);
        btPics.setOnClickListener(this);
        btCategory.setOnClickListener(this);
        btnCheckAvability.setOnClickListener(this);
        btnShareLocation.setOnClickListener(this);
    }

    private void getDataFromIntent() {

        catId =  Utils.ReadSharePrefrence(getApplicationContext(),Constant.SHRED_PR.KEY_CATID);
        Log.e("catId", "" + catId);
        imagePath = Utils.ReadSharePrefrence(getApplicationContext(),Constant.SHRED_PR.KEY_IMAGE_PATH);
        categoryName = Utils.ReadSharePrefrence(getApplicationContext(),Constant.SHRED_PR.KEY_CAT_NAME);
        imgId = Utils.ReadSharePrefrence(getApplicationContext(),Constant.SHRED_PR.KEY_IMAGE_ID);
        Log.e("IMGPath", "" + imagePath);
        categoryDesc = Utils.ReadSharePrefrence(getApplicationContext(),Constant.SHRED_PR.KEY_CAT_DESC);
        lat = Utils.ReadSharePrefrence(getApplicationContext(),Constant.SHRED_PR.KEY_LAT);
        Log.e("Lat", "" + lat);
        lng = Utils.ReadSharePrefrence(getApplicationContext(),Constant.SHRED_PR.KEY_LNG);
        Log.e("Long", "" + lng);
        latlong= lat + lng;
        city = Utils.ReadSharePrefrence(getApplicationContext(),Constant.SHRED_PR.KEY_CITY);
        state = Utils.ReadSharePrefrence(getApplicationContext(),Constant.SHRED_PR.KEY_STATE);
        country = Utils.ReadSharePrefrence(getApplicationContext(),Constant.SHRED_PR.KEY_COUNTRY);
        setData();
    }

    private void setData() {
        btCategory.setText(categoryName);
//        tvProName.setText(categoryName);
        tvCategoryDesc.setText(city);
        tvState.setText(state);
        if(!imagePath.equalsIgnoreCase("")) {
            Glide.with(getApplicationContext()).load(imagePath).into(ivImagePro);
        }
        latitude = Double.parseDouble(lat);
        longitude = Double.parseDouble(lng);
//        tvCity.setText(city+",");

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ivBack_ProjectDetailsActivity:
                intent = new Intent(getApplicationContext(), ProjectListaActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);

                break;
            case R.id.btCategory_ProjectDetailsActivity:
                llProjectPics.setVisibility(View.GONE);
                llProjectDetails.setVisibility(View.VISIBLE);
                break;
            case R.id.btPics_ProjectDetailsActivity:
                if(llProjectPics.getVisibility() == View.GONE) {
                    llProjectDetails.setVisibility(View.GONE);
                    llProjectPics.setVisibility(View.VISIBLE);
//                    if (new ConnectionDetector(ProjectDetailsActivity.this).isConnectingToInternet()) {
//                        if(!imgId.equalsIgnoreCase("")){
//                            new getProjectPics().execute();
//                        }else{
//                            Toast.makeText(ProjectDetailsActivity.this, "No images found.", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        new ConnectionDetector(ProjectDetailsActivity.this).connectiondetect();
//                    }
                }
                break;
            case R.id.activity_project_details_avability:
                intent = new Intent(getApplicationContext(), Builiding.class);
//                intent.putExtra(Constant.ProjectName, categoryName);
                Utils.WriteSharePrefrence(getApplicationContext(),Constant.SHRED_PR.KEY_BLOCK_PROJECTNAME,categoryName);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_out, R.anim.nothing);
                break;

            case R.id.activity_project_details_share_location:
                intent=new Intent();
               // intent = new Intent(Intent.ACTION_VIEW, Uri.parse("www.google.co.in/maps?q=loc:"+latlong));
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,
                        "Bavaria location: https://www.google.co.in/maps?q=loc:"+ lat +"," +lng);
                intent.setType("text/plain");
                startActivity(intent);

                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title(categoryName));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
    }


    public class getProjectDetailsData extends AsyncTask<String, String, String> {
        private Dialog dialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog = new Dialog(ProjectDetailsActivity.this);
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
            data.put("id", catId);
            return new MakeServiceCall().MakeServiceCall(Constant.BASE_URL + "property_pic.php", data);
        }

        @Override
        protected void onPostExecute(String s) {
//            Log.d("Response", "" + s);
            dialog.dismiss();
            Toast.makeText(ProjectDetailsActivity.this, s.toString(), Toast.LENGTH_SHORT).show();
            try {
                JSONObject object = new JSONObject(s.toString());
                if (object.getString("successful").equalsIgnoreCase("true")) {
                    Glide.with(ProjectDetailsActivity.this).load(object.getString("image")).into(ivImagePro);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class getProjectPics extends AsyncTask<String, String, String> {
        private Dialog dialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog = new Dialog(ProjectDetailsActivity.this);
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
//            Log.e("@@@@@@@@@@@@",""+Constant.BASE_URL + "propertyid_pics.php?id="+imgId);
            return new MakeServiceCall().MakeServiceCall(Constant.BASE_URL + "propertyid_pics.php?id="+imgId);
        }

        @Override
        protected void onPostExecute(String s) {
//            Log.d("Response", "" + s);
            dialog.dismiss();
            try {
                JSONObject object = new JSONObject(s.toString());
                if (object.getString("successful").equalsIgnoreCase("true")) {
//                    Toast.makeText(ProjectDetailsActivity.this, ""+object.getString("error_msg"), Toast.LENGTH_SHORT).show();
                    JSONArray array = object.getJSONArray("image");
                    for (int i = 0; i < array.length(); i++) {
                        imgList.add(array.getString(i));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new ProjectImageAdapter(ProjectDetailsActivity.this, imgList);
            gvImage.setAdapter(adapter);
        }
    }
}
